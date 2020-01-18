package com.dyman.im.config;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dyman
 * @describe
 * @date 2019/9/14
 */
@ChannelHandler.Sharable
@Slf4j
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("接收到消息" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("接收到消息" + ctx.pipeline().toString());
        System.out.println("接收到消息" + ctx.name());
//        ctx.write(byteBuf);
        if(byteBuf.refCnt() == 0)
        {
            System.out.println("引用计数为0可以回收了");
            ((ByteBuf) msg).release();
//            ctx.fireChannelRead(byteBuf);
        }
        else
        {
            System.out.println("不可回收" + byteBuf.refCnt());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String remoteAddr  = ctx.channel().remoteAddress().toString();
        String localAddr  = ctx.channel().localAddress().toString();
        String id  = ctx.channel().id().asLongText();
        log.info("【 有客户端连接过来了 remoteAddr={},localAddr={},id={}】", remoteAddr, localAddr, id);
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("传输完成，关闭连接");
//        .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
//            WebSocketServerProtocolHandler.HandshakeComplete complete = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
//            System.out.println("uri: " + complete.requestUri());
//            System.out.println("握手成功");
//        }
//        else
//        {
            super.userEventTriggered(ctx, evt);
//        }
    }


}
