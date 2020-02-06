package com.dyman.im.controller;

import com.dyman.im.entity.ChatRoomMessage;
import com.dyman.im.entity.Message;
import com.dyman.im.service.ChatRoomService;
import com.dyman.im.service.UserChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author dyman
 * @describe
 * @date 2020/1/12
 */
@Controller
@Slf4j
@RequestMapping("/chatRoom")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/sendMessage")
    @ResponseBody
    public void sendMessage(ChatRoomMessage message)
    {
        chatRoomService.sendMessage(message);
    }

    @PostMapping("/getOnlineNumber")
    @ResponseBody
    public long getOnlineNumber()
    {
        return chatRoomService.getOnlineNumber();
    }
}
