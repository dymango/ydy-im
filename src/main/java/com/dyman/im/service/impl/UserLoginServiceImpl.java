package com.dyman.im.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dyman.im.entity.UserLogin;
import com.dyman.im.exception.BusinessException;
import com.dyman.im.mapper.UserLoginMapper;
import com.dyman.im.service.UserLoginService;
import com.dyman.im.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.PAData;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author dyman
 * @Date 2020/1/22
 * @Description TODO
 **/
@Service
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    @Value("#{login.key}")
    private String loginKey;

    private final UserLoginMapper userLoginMapper;

    @Override
    public String getLoginPage() {
        return "login";
    }

    @Override
    public String login(HttpServletRequest request, String userName, String password) {
        Map<String, Object> countParams = new HashMap<>();
        countParams.put("user_name", userName);
        QueryWrapper<UserLogin> countWrapper = new QueryWrapper<>();
        countWrapper.allEq(countParams);
        int count = userLoginMapper.selectCount(countWrapper);
        if(count > 0)
        {
            Map<String, Object> params = new HashMap<>();
            params.put("user_name", userName);
            params.put("password", loginKey + password);
            QueryWrapper<UserLogin> queryWrapper = new QueryWrapper<>();
            queryWrapper.allEq(params);
            List<UserLogin> userLoginList = userLoginMapper.selectList(queryWrapper);
            if(userLoginList.size() < 0) throw new BusinessException("用户密码错误");
        }
        else
        {
            register(userName, password, request.getRemoteAddr());
        }

        return "chatroom";
    }

    @Override
    public void register(String userName, String password, String ip) {
        UserLogin userLogin = new UserLogin();
        userLogin.setUserName(userName);
        userLogin.setPassword(EncryptionUtil.md5Encryption(loginKey + password));
        userLogin.setLastLoginIp(ip);
        userLoginMapper.insert(userLogin);
    }
}
