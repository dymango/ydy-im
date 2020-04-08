package com.dyman.im.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.dyman.im.constant.MessageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author dyman
 * @Date 2020/1/22
 * @Description TODO
 **/
@TableName("user_login")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin extends BaseEntity {

    private String userName;
    private String password;
    private String nickName;
    private LocalDateTime lastLoginTime;
    private String lastLoginIp;
}
