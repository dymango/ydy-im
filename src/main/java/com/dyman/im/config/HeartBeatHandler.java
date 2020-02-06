package com.dyman.im.config;

import com.dyman.im.util.OnlineNumberCounter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author dyman
 * @describe
 * @date 2019/9/14
 */
@ChannelHandler.Sharable
@Slf4j
@Data
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("HEARTBAT", CharsetUtil.ISO_8859_1));

    private String userId;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent) {
            ctx.writeAndFlush(new TextWebSocketFrame("HEARTBAT")).addListener((ChannelFutureListener) channelFuture -> {
                if (channelFuture.isCancelled()) {
                    long n = OnlineNumberCounter.getInstance().decrease();
                    channelFuture.channel().close();
                    log.info("当前在线人数: {}", n);
                }
            });
        }
        else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
