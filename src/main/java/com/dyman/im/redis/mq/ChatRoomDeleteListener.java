package com.dyman.im.redis.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * @author dyman
 * @describe
 * @date 2020/7/29
 */
@Slf4j
public class ChatRoomDeleteListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] bytes) {
        log.info("删除聊天室: {}", message.toString());
    }
}
