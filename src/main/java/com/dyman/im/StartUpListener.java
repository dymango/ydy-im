package com.dyman.im;

import com.dyman.im.config.ChatRoomHandlerInitialzer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @author dyman
 * @describe
 * @date 2019/9/14
 */
@Component
@Slf4j
public class StartUpListener implements ApplicationListener<ApplicationStartedEvent> {

    private final int PORT = 8082;
    private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private final EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    private Channel channel;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        start();
    }

    public void start(){
        try
        {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(PORT))
                    .childHandler(new ChatRoomHandlerInitialzer(channelGroup));
            ChannelFuture channelFuture = serverBootstrap.bind().syncUninterruptibly();
            channel = channelFuture.channel();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                eventLoopGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                log.info("【 netty服务端异常 msg={}】", e.getMessage());
            }
        }
    }
}
