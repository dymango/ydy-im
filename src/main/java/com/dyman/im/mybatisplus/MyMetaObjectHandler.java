package com.dyman.im.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author dyman
 * @describe
 * @date 2020/3/6
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("thread __ {}, {}", Thread.currentThread().getId(), Thread.currentThread().getName());
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createdBy", String.class, "admin");
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "lastModifiedBy", String.class, "admin");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("thread __ {}, {}", Thread.currentThread().getId(), Thread.currentThread().getName());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "lastModifiedBy", String.class, "admin");
    }
}
