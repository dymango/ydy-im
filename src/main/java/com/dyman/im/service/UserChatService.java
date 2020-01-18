package com.dyman.im.service;

import com.dyman.im.entity.Message;
import org.springframework.ui.Model;

/**
 * @author dyman
 * @describe
 * @date 2020/1/12
 */
public interface UserChatService {

    String index(Model model, Integer sendUserId, Integer toUserId);

    void sendMessage(Message message);
}
