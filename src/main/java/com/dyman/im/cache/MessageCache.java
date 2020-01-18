package com.dyman.im.cache;


import com.dyman.im.entity.Message;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dyman
 * @describe 消息缓存
 * @date 2020/1/12
 */
public class MessageCache {

    private static ConcurrentHashMap<String, Message> messageHashMap = new ConcurrentHashMap<>();

    public static void put(String key, Message value)
    {
        messageHashMap.put(key, value);
    }

    public static Message get(String key)
    {
        return messageHashMap.get(key);
    }

    public static ConcurrentHashMap.KeySetView<String, Message> keySet()
    {
        return messageHashMap.keySet();
    }

    public static Set<Map.Entry<String, Message>> enrtySet()
    {
        return messageHashMap.entrySet();
    }
}
