package com.dyman.im.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author dyman
 * @describe 消息
 * @date 2020/1/12
 */
@Data
public class Message {

    private int sender;
    private int receiver;
    private String content;
    private int msgType;
    private Date createTime;
}
