package com.dyman.im.controller;

import com.alibaba.fastjson.JSONObject;
import com.dyman.im.core.DHashMap;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    /**
     * 1111 1111 1111 1111 0101 0101 0101 0101
     * >>> ↓
     * 0000 0000 0000 0000 1111 1111 1111 1111
     * ^ ↓
     * 1111 1111 1111 1111 1010 1010 1010 1010
     */
    private HashMap hashMap;

    public static void main(String[] args) {
//        System.out.println(16 >>> 1);
//        DHashMap dHashMap = new DHashMap();
//        dHashMap.put("you", "mash");
//        int i = 1;
//        System.out.println(dHashMap.get("you"));
//        System.out.println(dHashMap.get("hehe"));
//        DHashMap dHashMap = new DHashMap();
//        for(int i = 0; i < 100; i++) {
//            dHashMap.put("you" + i, "mash" + i);
//        }
//
//        System.out.println(dHashMap.size());

        HashMap dHashMap = new HashMap();
        for(int i = 0; i < 100; i++) {
            dHashMap.put("you" + i, "mash" + i);
        }

        System.out.println(dHashMap.size());
    }

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
