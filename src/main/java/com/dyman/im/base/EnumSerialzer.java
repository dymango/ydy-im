package com.dyman.im.base;

import com.alibaba.fastjson.JSONObject;
import com.dyman.im.constant.MessageTypeEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dyman
 * @describe
 * @date 2020/4/6
 */
public class EnumSerialzer extends JsonSerializer<Enum> {

    private final static EnumSerialzer serialzer;
    static {
        serialzer = new EnumSerialzer();
    }

    public static EnumSerialzer getInstance() {
        return serialzer;
    }

    @Override
    public void serialize(Enum anEnum, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Class enumClass = anEnum.getClass();
        Field[] fields = enumClass.getDeclaredFields();

        List<Field> fieldList = Arrays.stream(fields).filter(f -> Modifier.isStatic(f.getModifiers()) && Modifier.isPublic(f.getModifiers())).collect(Collectors.toList());
        for (Field field : fieldList) {
            if(field.getName().equals(anEnum.name())) {
                jsonGenerator.writeStartObject();
                if(anEnum != null) {
                    jsonGenerator.writeFieldName("name");
                    jsonGenerator.writeObject(MessageTypeEnum.FILE.getName());
                    jsonGenerator.writeFieldName("code");
                    jsonGenerator.writeObject(MessageTypeEnum.FILE.getCode());
                }

                jsonGenerator.writeEndObject();
            }
//            field.setAccessible(true);
//            if(field.getInt("code") == )
//            jsonGenerator.writeFieldName(field.getName());
//            try {
//
//                jsonGenerator.writeObject(field.get(aClass.getName()));
//
//            } catch (IllegalAccessException e) {
//
//                throw new IOException(e);
//
//            }
        }
        jsonGenerator.writeStartObject();
        if(anEnum != null) {
            jsonGenerator.writeFieldName("name");
            jsonGenerator.writeObject(MessageTypeEnum.FILE.getName());
            jsonGenerator.writeFieldName("code");
            jsonGenerator.writeObject(MessageTypeEnum.FILE.getCode());
        }

        jsonGenerator.writeEndObject();
//        Class<? extends Enum> aClass = anEnum.getClass();
//        Field[] fields = aClass.getDeclaredFields();
//        List<Field> fieldList = Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).collect(Collectors.toList());
//        if (CollectionUtils.isEmpty(fieldList)) {
//            jsonGenerator.writeObject(null);
//        } else {
//            jsonGenerator.writeStartObject();
//            jsonGenerator.writeFieldName("content");
//            jsonGenerator.writeObject(MessageTypeEnum.FILE);
////            for (Field field : fieldList) {
////                field.setAccessible(true);
////                jsonGenerator.writeFieldName(field.getName());
////                try {
////                    jsonGenerator.writeObject(field.get(aClass.getName()));
////                } catch (IllegalAccessException e) {
////                    throw new IOException(e);
////                }
////            }
////
//            jsonGenerator.writeEndObject();
//            jsonGenerator.writeNull();
//        }
    }
}
