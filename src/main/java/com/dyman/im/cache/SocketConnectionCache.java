package com.dyman.im.cache;

import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dyman
 * @describe socket连接管理
 * @date 2019/9/14
 */
public class SocketConnectionCache {

    private static ConcurrentHashMap<String, ChannelHandlerContext> channelContextHashMap = new ConcurrentHashMap<>();

    public static void put(String key, ChannelHandlerContext context)
    {
        channelContextHashMap.put(key, context);
    }

    public static ChannelHandlerContext get(String key)
    {
        return channelContextHashMap.get(key);
    }

    public static void delete(String key)
    {
        channelContextHashMap.remove(key);
    }

    public static ConcurrentHashMap.KeySetView<String, ChannelHandlerContext> keySet()
    {
        return channelContextHashMap.keySet();
    }
}
