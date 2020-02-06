package com.dyman.im.service;

import com.dyman.im.entity.ChatRoomMessage;

/**
 * @author dyman
 * @describe
 * @date 2020/1/29
 */
public interface ChatRoomService{

    void sendMessage(ChatRoomMessage message);

    long getOnlineNumber();
}
