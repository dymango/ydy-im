package com.dyman.im.constant;

/**
 * @author dyman
 * @describe
 * @date 2020/1/12
 */
public enum MessageType {
    TEXT("文本", 0),
    FILE("文件", 1),
    IMAGE("图片", 2);

    MessageType(String name, int code) {
        this.name = name;
        this.code = code;
    }

    private String name;
    private int code;
}
