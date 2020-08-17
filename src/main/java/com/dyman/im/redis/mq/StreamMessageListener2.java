package com.dyman.im.redis.mq;

import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author dyman
 * @describe
 * @date 2020/7/29
 */
@Slf4j
@Component
public class StreamMessageListener2 implements StreamListener<String, MapRecord<String, String, String>> {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void onMessage(MapRecord<String, String, String> entries) {
        log.info("2"+JSONObject.toJSONString(entries));
//        redisTemplate.opsForStream().acknowledge("ss", entries);
    }
}
