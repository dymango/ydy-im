package com.dyman.im.mybatisplus;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author dyman
 * @describe
 * @date 2020/3/6
 */
@Component
public class CustomIdGenerator implements IdentifierGenerator {

    private Logger logger = LoggerFactory.getLogger(CustomIdGenerator.class);

    public static final String NAME = "snowflake";
    public static final String CLASS = "com.dyman.im.mybatisplus";
    private static final IdGenerator GENERATOR = IdGenerator.of();

    @Override
    public Long nextId(Object entity) {
        String bizKey = entity.getClass().getName();
        logger.info("bizKey:{}", bizKey);
        MetaObject metaObject = SystemMetaObject.forObject(entity);
        return GENERATOR.generate();
    }
}
