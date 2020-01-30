package com.dyman.im.controller;

import com.dyman.im.service.UserLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author dyman
 * @Date 2020/1/22
 * @Description 登录
 **/
@Controller
@Slf4j
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final UserLoginService userLoginService;

    @GetMapping
    public String index()
    {
        return "login";
    }

    @PostMapping
    public String login(HttpServletRequest request, Model model, String username, String password)
    {
        return userLoginService.login(request, model, username, password);
    }
}
