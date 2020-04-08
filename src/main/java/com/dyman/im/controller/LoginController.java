package com.dyman.im.controller;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dyman.im.base.EnumDeSerialzer;
import com.dyman.im.base.EnumSerialzer;
import com.dyman.im.constant.MessageTypeEnum;
import com.dyman.im.dto.TestDto;
import com.dyman.im.entity.UserLogin;
import com.dyman.im.service.UserLoginService;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @GetMapping("/getAllUser/{type}")
    @ResponseBody
    public MessageTypeEnum getAllUser(@PathVariable("type") MessageTypeEnum messageTypeEnum)
    {
        return MessageTypeEnum.IMAGE;
    }

}
