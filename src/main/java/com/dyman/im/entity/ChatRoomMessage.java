package com.dyman.im.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dyman.im.constant.MessageTypeEnum;
import lombok.Data;

import java.util.Date;

/**
 * @author dyman
 * @describe 消息
 * @date 2020/1/12
 */
@Data
@TableName("chat_room_message")
public class ChatRoomMessage extends BaseEntity{

    /**
     * 房间名
     */
    private String roomName;
    /**
     * 发送人
     */
    private int sender;
    private String nickName;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息类型
     * {@link MessageTypeEnum}
     */
    private int msgType;
}
