package com.dyman.im.service.impl;

import com.dyman.im.cache.MessageCache;
import com.dyman.im.cache.SocketConnectionCache;
import com.dyman.im.entity.Message;
import com.dyman.im.service.UserChatService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

/**
 * @author dyman
 * @describe
 * @date 2020/1/12
 */
@Service
public class UserChatServiceImpl implements UserChatService {

    @Override
    public String index(Model model, Integer sendUserId, Integer toUserId) {
        if(sendUserId == null || toUserId == null) return "index.html";
        Set<Map.Entry<String, Message>> messageSet = MessageCache.enrtySet();
        List<Message> messageList = new ArrayList<>();
        for(Map.Entry<String, Message> m : messageSet)
        {
            Message message = m.getValue();
            int sender = Integer.parseInt(m.getKey());
            int receiver = message.getReceiver();
            if((sender == sendUserId && receiver == toUserId) || (sender == toUserId && receiver == sendUserId))
            {
                messageList.add(message);
            }
        }

        model.addAttribute("messageList", messageList);
        model.addAttribute("sender", sendUserId);
        model.addAttribute("receiver", toUserId);
        return "chatroom";
    }

    @Override
    public void sendMessage(Message message) {
        int toUserId = message.getReceiver();
        int sendUserId = message.getSender();
        ChannelHandlerContext ctx = SocketConnectionCache.get(toUserId+"");
        if(ctx == null || !ctx.channel().isOpen()) throw new RuntimeException();
        ctx.writeAndFlush(new TextWebSocketFrame("用户" + sendUserId + ": " + message.getContent()));
    }
}
