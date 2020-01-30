package com.dyman.im.service.impl;

import com.dyman.im.cache.SocketConnectionCache;
import com.dyman.im.entity.ChatRoomMessage;
import com.dyman.im.mapper.ChatRoomMessageMapper;
import com.dyman.im.service.ChatRoomService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author dyman
 * @describe
 * @date 2020/1/29
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private static final String DEFAULT_CHAT_ROOM_NAME = "default";
    private final ChatRoomMessageMapper chatRoomMessageMapper;

    @Override
    public void sendMessage(ChatRoomMessage message) {
        int sendUserId = message.getSender();
        message.setRoomName(DEFAULT_CHAT_ROOM_NAME);
        LocalDateTime createTime = LocalDateTime.now();
        message.setCreateTime(createTime);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        chatRoomMessageMapper.insert(message);
        for(Map.Entry<String, ChannelHandlerContext> entry : SocketConnectionCache.entrySet())
        {
            ChannelHandlerContext ctx = entry.getValue();
            if(ctx != null && ctx.channel().isOpen())
            {
                ctx.writeAndFlush(new TextWebSocketFrame(df.format(createTime) + "  用户" + sendUserId + ": " + message.getContent()));
            }
        }
    }
}
