package com.dyman.im.config;

import com.dyman.im.cache.SocketConnectionCache;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dyman
 * @describe
 * @date 2019/9/14
 */
@ChannelHandler.Sharable
@Slf4j
@Data
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private String userId;

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("客户端接收到消息"+byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端ID"+ channelHandlerContext.channel().id().asLongText());
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String remoteAddr  = ctx.channel().remoteAddress().toString();
        String localAddr  = ctx.channel().localAddress().toString();
        String id  = ctx.channel().id().asLongText();
        SocketConnectionCache.put(userId, ctx);
        log.info("【 客户端连接连接服务器 remoteAddr={},localAddr={},id={}】", remoteAddr, localAddr, id);
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty Rock", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
