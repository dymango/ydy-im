package com.dyman.im.base;

import com.dyman.im.exception.BusinessException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dyman
 * @describe 字符串转换枚举类
 * @date 2020/4/6
 */
public class StringToEnumConverterFactory implements ConverterFactory<String, BaseEnum> {

    private static final Map<Class, Converter> converterMap = new HashMap<>();

    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        Converter<String, T> converter = converterMap.get(targetType);
        if(converter == null) {
            converter = new StringToEnumConverter<>(targetType);
            converterMap.put(targetType, converter);
        }
        return converter;
    }

    class StringToEnumConverter<T extends BaseEnum> implements Converter<String, T> {
        private Map<String, T> enumMap = new HashMap<>();

        StringToEnumConverter(Class<T> enumType) {
            T[] enums = enumType.getEnumConstants();
            for(T e : enums) {
                enumMap.put(String.valueOf(e.getValue()), e);
            }
        }

        @Override
        public T convert(String source) {

            T t = enumMap.get(source);
            if (t == null) {
                // 异常可以稍后去捕获
                throw new BusinessException("无对应枚举类,转换失败");
//                throw new IllegalArgumentException("No element matches " + source);
            }
            return t;
        }
    }
}
