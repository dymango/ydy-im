package com.dyman.im.controller;

import com.dyman.im.entity.Message;
import com.dyman.im.service.UserChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class ChatRoomController {

    @Autowired
    private UserChatService userChatService;

    @RequestMapping("/index")
    public String index(Model model, Integer sendUserId, Integer toUserId)
    {
        return userChatService.index(model, sendUserId, toUserId);
    }

    @PostMapping("/sendMessage")
    @ResponseBody
    public void sendMessage(Message message)
    {
        userChatService.sendMessage(message);
    }
}
