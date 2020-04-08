package com.dyman.im.base;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.deser.*;
import com.fasterxml.jackson.databind.deser.std.EnumDeserializer;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

import java.util.Iterator;

/**
 * 反序列号工具
 * 因EnumDeserializerByCode中没有找到获取属性类型的方法，所以在拜读源码后重写类进行处理
 * @author males
 */
public class CustomBeanDeserializerFactory extends BeanDeserializerFactory {

    /**
     * Globally shareable thread-safe instance which has no additional custom deserializers
     * registered
     */
    public final static CustomBeanDeserializerFactory instance = new CustomBeanDeserializerFactory(
            new DeserializerFactoryConfig());

    public CustomBeanDeserializerFactory(DeserializerFactoryConfig config) {
        super(config);
    }

    @Override
    public DeserializerFactory withConfig(DeserializerFactoryConfig config) {
        return this;
    }

    @Override
    public JsonDeserializer<?> createEnumDeserializer(DeserializationContext ctx, JavaType type, BeanDescription beanDesc) throws JsonMappingException {
        DeserializationConfig config = ctx.getConfig();
        Class<?> enumClass = type.getRawClass();
        JsonDeserializer<?> deser = this._findCustomEnumDeserializer(enumClass, config, beanDesc);
        if (deser == null) {
            ValueInstantiator valueInstantiator = this._constructDefaultValueInstantiator(ctx, beanDesc);
            SettableBeanProperty[] creatorProps = valueInstantiator == null ? null : valueInstantiator.getFromObjectArguments(ctx.getConfig());
            Iterator i$ = beanDesc.getFactoryMethods().iterator();

            while(i$.hasNext()) {
                AnnotatedMethod factory = (AnnotatedMethod)i$.next();
                if (ctx.getAnnotationIntrospector().hasCreatorAnnotation(factory)) {
                    if (factory.getParameterCount() == 0) {
                        deser = EnumDeserializer.deserializerForNoArgsCreator(config, enumClass, factory);
                        break;
                    }

                    Class<?> returnType = factory.getRawReturnType();
                    if (returnType.isAssignableFrom(enumClass)) {
                        deser = EnumDeserializer.deserializerForCreator(config, enumClass, factory, valueInstantiator, creatorProps);
                        break;
                    }
                }
            }

            if (deser == null) {
                deser = new EnumByCodeDeserializer(this.constructEnumResolver(enumClass, config, beanDesc.findJsonValueMethod()), null);
            }
        }

        BeanDeserializerModifier mod;
        if (this._factoryConfig.hasDeserializerModifiers()) {
            for(Iterator i$ = this._factoryConfig.deserializerModifiers().iterator(); i$.hasNext(); deser = mod.modifyEnumDeserializer(config, type, beanDesc, deser)) {
                mod = (BeanDeserializerModifier)i$.next();
            }
        }

        return deser;
    }
}

