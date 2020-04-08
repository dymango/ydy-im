package com.dyman.im.base;

import com.dyman.im.constant.MessageTypeEnum;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.EnumDeserializer;
import com.fasterxml.jackson.databind.util.EnumResolver;

import java.io.IOException;

/**
 * @author dyman
 * @describe
 * @date 2020/4/6
 */
public class EnumByCodeDeserializer extends EnumDeserializer {

    public EnumByCodeDeserializer(EnumResolver byNameResolver, Boolean caseInsensitive) {
        super(byNameResolver, caseInsensitive);
    }

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return MessageTypeEnum.IMAGE;
//        return super.deserialize(p, ctxt);
    }
}
