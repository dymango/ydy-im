package com.dyman.im.util;

import com.dyman.im.util.orika.converter.*;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author dyman
 * @describe 属性映射工具
 * @date 2020/4/8
 */
public class OrikaMapper {

    public static final MapperFacade MAPPER_FACADE;

    static {
        MAPPER_FACADE = getFactory().getMapperFacade();
    }

    /**
     * 获取工厂
     * @return
     */
    public static MapperFactory getFactory(){
        MapperFactory factory = new DefaultMapperFactory.Builder().build();
        final ConverterFactory converterFactory = factory.getConverterFactory();
        converterFactory.registerConverter(new InstantConverter());
        converterFactory.registerConverter(new LocalDateConverter());
        converterFactory.registerConverter(new LocalDateTimeConverter());
        converterFactory.registerConverter(new LocalTimeConverter());
        converterFactory.registerConverter(new OffsetDateTimeConverter());
        converterFactory.registerConverter(new ZonedDateTimeConverter());
        return factory;
    }

    /**
     * 获取Bean中为null的属性名
     *
     * @param source 数据源
     * @return
     */
    private static void nullProperty(Object source, Consumer<String> action) {
        BeanWrapper src = new BeanWrapperImpl(source);
        Arrays.stream(src.getPropertyDescriptors())
                .filter(propertyDescriptor -> src.getPropertyDescriptor(propertyDescriptor.getName()) == null)
                .forEach(propertyDescriptor -> action.accept(propertyDescriptor.getName()));
    }

    /**
     * 复制对象属性，且排除空属性的值
     * @param source
     * @param target
     * @param <S>
     * @param <D>
     * @return
     */
    public static <S, D> D mapExcludeNull(S source, D target) {
        MapperFactory factory = getFactory();
        ClassMapBuilder builder = factory.classMap(source.getClass(), target.getClass());
        nullProperty(source, name->builder.exclude(name));
        builder.byDefault().register();
        factory.getMapperFacade().map(source,target);
        return target;
    }

    /**
     * 转换对象
     */
    public static <S, D> D map(S source, Class<D> destinationClass) {
        return MAPPER_FACADE.map(source, destinationClass);
    }

    /**
     * 转换对象的列表到另一个对象的列表.
     */
    public static <S, D> List<D> mapList(Iterable<S> sourceList, Class<D> destinationClass) {
        return MAPPER_FACADE.mapAsList(sourceList, destinationClass);
    }
}
