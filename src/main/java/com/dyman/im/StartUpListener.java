package com.dyman.im;

import com.dyman.im.config.ChatRoomHandlerInitialzer;
import com.dyman.im.disr.*;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ExceptionHandlerWrapper;
import com.lmax.disruptor.dsl.ProducerType;
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
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

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

    public static void main(String[] args) throws InterruptedException {
        mutli();
    }

    public static void mutli() throws InterruptedException {
        int bufferSize = 1024;
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("DisruptorThreadPool").build();
        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, namedThreadFactory, ProducerType.MULTI, new YieldingWaitStrategy());
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        SequenceBarrier barriers = ringBuffer.newBarrier();
        LongEventWorkHandler[] longEventWorkHandlers = new LongEventWorkHandler[10];
        for (int i = 0; i < longEventWorkHandlers.length; i++) {
            longEventWorkHandlers[i] = new LongEventWorkHandler("消费者"+i);
        }
        WorkerPool<LongEvent> workerPool =
                new WorkerPool<>(ringBuffer,
                        barriers,
                        new ExceptionHandler<LongEvent>() {
                            @Override
                            public void handleEventException(Throwable throwable, long l, LongEvent longEvent) {
                                System.out.println(longEvent.getValue() + throwable.getMessage());
                            }

                            @Override
                            public void handleOnStartException(Throwable throwable) {
                                System.out.println(throwable.getMessage());
                            }

                            @Override
                            public void handleOnShutdownException(Throwable throwable) {
                                System.out.println(throwable.getMessage());
                            }
                        },
                        longEventWorkHandlers);
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());
        LongEventProducer producer = new LongEventProducer(ringBuffer);
        workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
        final CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 5; i++) {
            final LongEventProducer p = new LongEventProducer(ringBuffer);
            new Thread(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(int j = 0; j < 100; j ++){
                    ByteBuffer bb = ByteBuffer.allocate(8);
                    bb.putLong(0, j);
                    p.onData(bb);
                }
            }).start();
        }

        Thread.sleep(2000);
        System.out.println("---------------开始生产-----------------");
        latch.countDown();
    }

    public static void singleProducer() {
        int bufferSize = 1024;
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("DisruptorThreadPool").build();
        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, namedThreadFactory, ProducerType.SINGLE, new YieldingWaitStrategy());
        LongEventHandler[] longEventHandlers = new LongEventHandler[10];
        for (int i = 0; i < longEventHandlers.length; i++) {
            longEventHandlers[i] = new LongEventHandler("消费者"+i);
        }

        disruptor.handleEventsWith(longEventHandlers);
        disruptor.start();
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        LongEventProducer longEventProducer = new LongEventProducer(ringBuffer);
        ByteBuffer bb = ByteBuffer.allocate(8);for (long l = 0; l<100L; l++) {
            bb.putLong(0, l);
            longEventProducer.onData(bb);
        }

    }
}
