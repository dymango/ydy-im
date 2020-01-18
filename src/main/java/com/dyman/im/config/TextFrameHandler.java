package com.dyman.im.config;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dyman
 * @describe
 * @date 2020/1/11
 */
@ChannelHandler.Sharable
@Slf4j
public class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private final ChannelGroup group;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String remoteAddr  = ctx.channel().remoteAddress().toString();
        String localAddr  = ctx.channel().localAddress().toString();
        String id  = ctx.channel().id().asLongText();
//        SocketConnectionManager.put("123", ctx);
        log.info("【 有客户端连接过来了 remoteAddr={},localAddr={},id={}】", remoteAddr, localAddr, id);
    }

    public TextFrameHandler(ChannelGroup group) {
        this.group = group;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE)
        {
            ctx.pipeline().remove(HttpRequestHandler.class);
            group.write(new TextWebSocketFrame("client" + ctx.channel() + " joined"));
            group.add(ctx.channel());
        }
        else
        {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        group.writeAndFlush(textWebSocketFrame.retain());
    }
}
