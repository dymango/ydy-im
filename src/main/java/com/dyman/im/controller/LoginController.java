package com.dyman.im.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author dyman
 * @Date 2020/1/22
 * @Description 登录
 **/
@Controller
@Slf4j
@RequestMapping("/chatRoom")
public class LoginController {

    @GetMapping("/login")
    public String index()
    {
        return "login";
    }

    @PostMapping("/login")
    public void login(String username, String password)
    {

    }
}
