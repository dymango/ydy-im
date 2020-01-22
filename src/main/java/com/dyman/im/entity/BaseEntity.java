package com.dyman.im.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author dyman
 * @Date 2020/1/22
 * @Description TODO
 **/
@Data
public class BaseEntity {

    private int id;
    private LocalDateTime createTime;
}
