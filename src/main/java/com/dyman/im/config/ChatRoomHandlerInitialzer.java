package com.dyman.im.config;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author dyman
 * @describe
 * @date 2020/1/11
 */
public class ChatRoomHandlerInitialzer extends ChannelInitializer<Channel> {
    private final ChannelGroup group;

    public ChatRoomHandlerInitialzer(ChannelGroup group) {
        this.group = group;
    }

    @Override
    protected void initChannel(Channel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new IdleStateHandler(0, 0, 1, TimeUnit.SECONDS));
        socketChannel.pipeline().addLast(new HttpServerCodec());
        socketChannel.pipeline().addLast(new ChunkedWriteHandler());
        socketChannel.pipeline().addLast(new HttpObjectAggregator(64 * 1024));
        socketChannel.pipeline().addLast(new HttpRequestHandler("/ws"));
        socketChannel.pipeline().addLast(new WebSocketServerProtocolHandler("/ws"));
//        socketChannel.pipeline().addLast(new EchoServerHandler());
        socketChannel.pipeline().addLast(new TextFrameHandler(group));
        socketChannel.pipeline().addLast(new HeartBeatHandler());
//        socketChannel.pipeline().addLast(new HttpContentCompressor());
    }
}
