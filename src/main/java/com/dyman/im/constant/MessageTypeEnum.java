package com.dyman.im.constant;

import com.dyman.im.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author dyman
 * @describe
 * @date 2020/1/12
 */
@Getter
@AllArgsConstructor
@JsonFormat(shape= JsonFormat.Shape.OBJECT)
public enum MessageTypeEnum implements BaseEnum {
    TEXT("文本", 1),
    FILE("文件", 2),
    IMAGE("图片", 3);

    private String name;
    private int code;

    @Override
    public int getValue() {
        return code;
    }
}
