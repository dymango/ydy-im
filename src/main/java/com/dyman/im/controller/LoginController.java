package com.dyman.im.controller;

import com.dyman.im.constant.MessageTypeEnum;
import com.dyman.im.entity.ChatRoomMessage;
import com.dyman.im.mapper.UserLoginMapper;
import com.dyman.im.service.UserLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
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
    private final UserLoginMapper mapper;
    private final RedisTemplate<String, String> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

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


    @GetMapping("/search/{id}")
    @ResponseBody
    public Boolean search(@PathVariable String id)
    {
        return mapper.exist(id);
    }

    @GetMapping("/getLoginInfo/{key}/{value}")

        @ResponseBody
        public String getLoginInfo(@PathVariable String key, @PathVariable String value)
        {
            redisTemplate.convertAndSend("chatRoom:message", "大家好");
            redisTemplate.convertAndSend("chatRoom:delete", "删删删");
            redisTemplate.opsForStream().add(StreamRecords.string(Collections.singletonMap(key, value)).withStreamKey("key"));
            List<MapRecord<String, Object, Object>> mystream = redisTemplate.opsForStream().range("mystream", Range.unbounded());
            for (MapRecord<String, Object, Object> e: mystream) {
                log.info("消息重发, {}", e.getId().toString());
                redisTemplate.opsForStream().delete(e);
                redisTemplate.opsForStream().add(e.withId(RecordId.autoGenerate()).withStreamKey("mystream"));
            }
        return "缓存成功";
    }


}
