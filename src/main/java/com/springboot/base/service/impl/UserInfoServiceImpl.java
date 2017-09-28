package com.springboot.base.service.impl;

import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.enmus.UserStatus;
import com.springboot.base.data.entity.UserInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.UserVO;
import com.springboot.base.mapper.UserInfoMapper;
import com.springboot.base.service.RedisService;
import com.springboot.base.service.UserInfoService;
import com.springboot.base.util.PasswordUtil;
import com.springboot.base.util.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

    private final int FREEZE_NUMBER = 3;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public UserVO login(UserInfo user) throws Exception {
        UserInfo newUserInfo = userInfoMapper.getUserInfo(user.getUsername(), UserStatus.DEFAULT.getIndex());
        if (newUserInfo == null) {
            checkPasswordNumber(user.getUsername());
            return null;
        }
        if (!checkPassword(user.getPassword(), newUserInfo.getPassword(), newUserInfo.getSalt())) {
            checkPasswordNumber(user.getUsername());
            return null;
        }
        newUserInfo.setPasswordNumber(0);
        saveRedis(newUserInfo, true);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(newUserInfo, userVO);
        return userVO;
    }

    //校验猜密码次数
    private void checkPasswordNumber(String username) throws Exception {
        Integer number = redisService.getUserPasswordNumber(username);
        if (number >= FREEZE_NUMBER) {
            log.info("该用户被冻结！username{}", username);
            throw new PrivateException(ErrorInfo.USER_FREEZE);
        }
        redisService.saveUserPasswordNumber(username, number + 1);
    }

    //校验密码
    private boolean checkPassword(String passwordStr, String passwordMD5, String salt) throws Exception {
        String password = PasswordUtil.getPassword(passwordStr, salt);
        return password.equals(passwordMD5);
    }

    //设置token
    private void saveRedis(UserInfo userInfo, boolean isToken) throws Exception {
        userInfo.setKey(TokenUtils.getKey(userInfo));
        if (isToken) {
            userInfo.setToke(TokenUtils.getToken(userInfo));
        }
        redisService.saveUser(userInfo);
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
        int count = userInfoMapper.save(userInfo);
        if (count > 0) {
            return userInfo;
        }
        return null;
    }
}
