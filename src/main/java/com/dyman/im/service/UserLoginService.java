package com.dyman.im.service;

import com.dyman.im.entity.UserLogin;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author dyman
 * @Date 2020/1/22
 * @Description TODO
 **/
public interface UserLoginService {

    String getLoginPage();

    String login(HttpServletRequest request, Model model, String userName, String password);

    int register(String userName, String password, String ip);

    List<UserLogin> getAllUser();
}
