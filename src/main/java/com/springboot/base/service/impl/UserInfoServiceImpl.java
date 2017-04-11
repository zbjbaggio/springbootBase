package com.springboot.base.service.impl;

import com.springboot.base.data.enmus.UserStatus;
import com.springboot.base.data.entity.UserInfo;
import com.springboot.base.mapper.UserInfoMapper;
import com.springboot.base.service.UserInfoService;
import com.springboot.base.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 用户信息
 * Created by jay on 2017-4-10.
 */
@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo login(UserInfo user) {
        UserInfo newUserInfo = userInfoMapper.getUserInfo(user.getUsername(), UserStatus.DEFAULT.getIndex());
        if (newUserInfo == null) {
            return null;
        }
        return newUserInfo;
    }

    @Override
    public UserInfo getUserNoState(String username) {
        return userInfoMapper.getUserInfoNoState(username);
    }

    @Override
    public UserInfo save(UserInfo userInfo) throws Exception {
        UUID uuid = UUID.randomUUID();
        String salt = uuid.toString();
        userInfo.setSalt(salt);
        userInfo.setPassword(PasswordUtil.getPassword(userInfo.getPassword(), salt));
        userInfo.setState(UserStatus.DEFAULT.getIndex());
        userInfo.setOperator_id(null);
        int count =  userInfoMapper.save(userInfo);
        if (count > 0) {
            return  userInfo;
        }
        return  null;
    }
}
