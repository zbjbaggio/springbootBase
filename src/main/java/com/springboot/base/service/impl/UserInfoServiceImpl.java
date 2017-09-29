package com.springboot.base.service.impl;

import com.springboot.base.constant.SystemConstants;
import com.springboot.base.data.base.Page;
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
import org.springframework.util.StringUtils;

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

    @Autowired
    private RedisService redisService;

    @Override
    public UserVO login(UserInfo user) throws Exception {
        Integer number = checkPasswordNumber(user.getUsername());
        UserInfo newUserInfo = userInfoMapper.getUserInfo(user.getUsername());
        if (newUserInfo == null || !checkUser(user.getPassword(), newUserInfo)) {
            redisService.saveUserPasswordNumber(user.getUsername(), number + 1);
            return null;
        }
        newUserInfo.setPasswordNumber(0);
        saveRedis(newUserInfo, true);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(newUserInfo, userVO);
        return userVO;
    }

    //校验猜密码次数
    private Integer checkPasswordNumber(String username) throws Exception {
        Integer number = redisService.getUserPasswordNumber(username);
        if (number >= SystemConstants.FREEZE_NUMBER) {
            log.info("该用户被停止登录！username{}", username);
            throw new PrivateException(ErrorInfo.USER_NO_LOGIN);
        }
        return number;
    }

    //校验密码
    private boolean checkUser(String passwordStr, UserInfo newUserInfo) throws Exception {
        if (newUserInfo.getStatus() == UserStatus.FREEZE.getIndex()) {
            log.info("该用户被冻结！username{}", newUserInfo.getUsername());
            throw new PrivateException(ErrorInfo.USER_FREEZE);
        }
        if (newUserInfo.getStatus() == UserStatus.UNACTIVATED.getIndex()) {
            log.info("该用户还未审核通过！username{}", newUserInfo.getUsername());
            throw new PrivateException(ErrorInfo.USER_UNACTIVATED);
        }
        String password = PasswordUtil.getPassword(passwordStr, newUserInfo.getSalt());
        return password.equals(newUserInfo.getPassword());
    }

    //设置token
    private void saveRedis(UserInfo userInfo, boolean isToken) throws Exception {
        userInfo.setKey(TokenUtils.getKey(userInfo));
        if (isToken) {
            userInfo.setToken(TokenUtils.getToken(userInfo));
        }
        redisService.saveUser(userInfo);
    }

    @Override
    public UserInfo getUserNoState(String username) {
        return userInfoMapper.getUserInfoNoState(username);
    }

    @Override
    public boolean checkToken(String token, String key) {
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(key)) {
            return false;
        }
        UserInfo userInfo = redisService.getUserInfoByKey(key);
        if (userInfo != null && token.equals(userInfo.getToken())) {
            redisService.saveUser(userInfo);
            return true;
        }
        return false;
    }

    @Override
    public Page listPage(int limit, int offset, String searchStr, int state) {
        if (!"-1".equals(searchStr)) {
            searchStr = "%" + searchStr + "%";
        }
        Page page = new Page();
        Long count = userInfoMapper.count(searchStr, state);
        if (count != 0) {
            page.setCount(count);
            page.setList(userInfoMapper.listPage(limit, offset, searchStr, state));
        }
        return page;
    }

    @Override
    public UserVO getDetail(Long userId) {
        return userInfoMapper.getDetailById(userId);
    }

    @Override
    public UserInfo save(UserInfo userInfo) throws Exception {
        UUID uuid = UUID.randomUUID();
        String salt = uuid.toString();
        userInfo.setSalt(salt);
        userInfo.setPassword(PasswordUtil.getPassword(userInfo.getPassword(), salt));
        userInfo.setStatus(UserStatus.DEFAULT.getIndex());
        userInfo.setOperator_id(null);
        int count = userInfoMapper.save(userInfo);
        if (count > 0) {
            return userInfo;
        }
        return null;
    }
}
