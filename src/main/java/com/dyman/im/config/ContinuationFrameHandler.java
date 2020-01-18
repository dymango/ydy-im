package com.dyman.im.config;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author dyman
 * @describe
 * @date 2020/1/11
 */
public class ContinuationFrameHandler extends SimpleChannelInboundHandler<ContinuationFrameHandler> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ContinuationFrameHandler binaryFrameHandler) throws Exception {

    }
}
