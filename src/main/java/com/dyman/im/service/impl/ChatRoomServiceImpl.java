package com.dyman.im.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dyman.im.cache.SocketConnectionCache;
import com.dyman.im.entity.ChatRoomMessage;
import com.dyman.im.entity.UserLogin;
import com.dyman.im.mapper.ChatRoomMessageMapper;
import com.dyman.im.mapper.UserLoginMapper;
import com.dyman.im.service.ChatRoomService;
import com.dyman.im.util.OnlineNumberCounter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
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
    private final UserLoginMapper userLoginMapper;

    @Override
    public void sendMessage(ChatRoomMessage message) {
        int sendUserId = message.getSender();
        Map<String, Object> params = new HashMap<>();
        params.put("id", sendUserId);
        QueryWrapper<UserLogin> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(params);
        UserLogin user = userLoginMapper.selectOne(queryWrapper);
        user.setNickName(message.getNickName());
        userLoginMapper.updateById(user);
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
                ctx.writeAndFlush(new TextWebSocketFrame(df.format(createTime) + "  " + message.getNickName() + ": " + message.getContent()));
            }
        }
    }

    @Override
    public long getOnlineNumber() {
        return OnlineNumberCounter.getInstance().getOnlineNumber();
    }
}
