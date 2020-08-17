package com.dyman.im.redis.mq;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @author dyman
 * @describe
 * @date 2020/7/29
 */
@Configuration
@RequiredArgsConstructor
public class ChatRoomQueue {

    private final RedisConnectionFactory connectionFactory;

    /**
     * 发布/订阅模式
     * @return
     */
    @Bean
    RedisMessageListenerContainer chatRoomListener() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(new ChatRoomListener(), new ChannelTopic("chatRoom:message"));
        container.addMessageListener(new ChatRoomDeleteListener(), new ChannelTopic("chatRoom:delete"));
        return container;
    }
}
