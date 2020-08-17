package com.dyman.im.base;

import com.dyman.im.redis.mq.StreamMessageListener;
import com.dyman.im.redis.mq.StreamMessageListener2;
import com.dyman.im.redis.mq.StreamMessageListener3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.time.Duration;

/**
 * @author dyman
 * @describe
 * @date 2020/7/29
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;
    @Value("${spring.redis.jedis.pool.max-wait}")
    private int maxWait;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    StreamMessageListener streamMessageListener;
    @Autowired
    StreamMessageListener2 streamMessageListener2;
    @Autowired
    StreamMessageListener3 streamMessageListener3;


    private StreamMessageListenerContainer<String, MapRecord<String, String, String>> streamMessageListenerContainer;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory server = new LettuceConnectionFactory(new RedisStandaloneConfiguration(host, port));
        RedisStandaloneConfiguration standaloneConfiguration = server.getStandaloneConfiguration();
        new Thread(() -> redisStream()).start();
        return server;
    }

    private void redisStream() {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> streamMessageListenerContainerOptions = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .builder()
                // 一次性最多拉取多少条消息
                .batchSize(10)
                // 消费消息的线程池
                .executor(this.threadPoolTaskExecutor)
                // 消息消费异常的handler
                .errorHandler(t -> {
                    // throw new RuntimeException(t);
                    t.printStackTrace();
                })
                // 超时时间，设置为0，表示不超时（超时后会抛出异常）
                .pollTimeout(Duration.ZERO)
                // 序列化器
                .serializer(new StringRedisSerializer())
                .build();

        // 根据配置对象创建监听容器对象
        StreamMessageListenerContainer<String, MapRecord<String, String, String>> streamMessageListenerContainer = StreamMessageListenerContainer
                .create(redisConnectionFactory(), streamMessageListenerContainerOptions);

        // 使用监听容器对象开始监听消费（使用的是手动确认方式）
        streamMessageListenerContainer.receiveAutoAck(Consumer.from("group-1", "consumer-1"),
                StreamOffset.create("mystream", ReadOffset.lastConsumed()), streamMessageListener);
        streamMessageListenerContainer.receiveAutoAck(Consumer.from("group-1", "consumer-2"),
                StreamOffset.create("mystream", ReadOffset.lastConsumed()), streamMessageListener2);
        streamMessageListenerContainer.receiveAutoAck(Consumer.from("group-3", "consumer-1"),
                StreamOffset.create("mystream", ReadOffset.lastConsumed()), streamMessageListener3);

        this.streamMessageListenerContainer = streamMessageListenerContainer;
        // 启动监听
        this.streamMessageListenerContainer.start();
    }
}
