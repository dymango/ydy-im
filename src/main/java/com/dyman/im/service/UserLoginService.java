package com.dyman.im.service;

import com.dyman.im.entity.UserLogin;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author dyman
 * @Date 2020/1/22
 * @Description TODO
 **/
public interface UserLoginService {

    String getLoginPage();

    String login(HttpServletRequest request, String userName, String password);

    void register(String userName, String password, String ip);
}
