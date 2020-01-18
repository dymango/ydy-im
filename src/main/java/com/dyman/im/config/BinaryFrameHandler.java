package com.dyman.im.config;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author dyman
 * @describe
 * @date 2020/1/11
 */
public class BinaryFrameHandler extends SimpleChannelInboundHandler<BinaryFrameHandler> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BinaryFrameHandler binaryFrameHandler) throws Exception {

    }
}
