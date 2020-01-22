package com.dyman.im.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
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
public class UserLogin extends BaseEntity {

    public UserLogin()
    {
        this.setCreateTime(LocalDateTime.now());
        this.setLastLoginTime(LocalDateTime.now());
    }

    private String userName;
    private String password;
    private LocalDateTime lastLoginTime;
    private String lastLoginIp;
}
