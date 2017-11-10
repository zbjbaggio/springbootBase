package com.springboot.base.service.impl;

import com.springboot.base.constant.SystemConstants;
import com.springboot.base.data.base.Page;
import com.springboot.base.data.dto.PasswordDTO;
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
import com.springboot.base.util.ValueHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.util.UUID;

/**
 * 用户信息
 * Created by jay on 2017-4-10.
 */
@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    @Inject
    private UserInfoMapper userInfoMapper;

    @Inject
    private RedisService redisService;

    @Inject
    private ValueHolder valueHolder;

    // TODO: 2017-10-12 未完成：1.登录成功时删除该用户登录错误次数 2.不能登录一个小时后，再猜错同样次数的直接冻结 3.同样ip地址猜错一次密码出验证码
    @Override
    public UserVO login(UserInfo user) throws Exception {
        Integer number = checkPasswordNumber(user.getUsername());
        UserInfo newUserInfo = userInfoMapper.getUserInfo(user.getUsername());
        if (newUserInfo == null || !checkUser(user.getPassword(), newUserInfo)) {
            redisService.saveUserPasswordNumber(user.getUsername(), number + 1);
            return null;
        }
        newUserInfo.setPasswordNumber(0);
        newUserInfo.setPassword("");
//        redisService.removeUserPasswordNumberByKey();
        saveRedis(newUserInfo, true);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(newUserInfo, userVO);
        return userVO;
    }

    @Override
    public boolean checkToken(String token, String key) {
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(key)) {
            log.info("未登录");
            return false;
        }
        UserInfo userInfo = redisService.getUserInfoByKey(key);
        if (userInfo != null && token.equals(userInfo.getToken())) {
            valueHolder.setUserIdHolder(userInfo.getId());
            redisService.saveUser(userInfo);
            return true;
        }
        log.info("重新登录");
        return false;
    }

    @Override
    public Page listPage(int limit, int offset, String searchStr, int status, String orderBy, boolean desc) {
        if (!"-1".equals(searchStr)) {
            searchStr = "%" + searchStr + "%";
        }
        String descStr = "";
        if (!StringUtils.isEmpty(orderBy) && desc) {
            descStr = "desc";
        }
        Page page = new Page();
        Long count = userInfoMapper.count(searchStr, status);
        if (count != 0) {
            page.setCount(count);
            page.setList(userInfoMapper.listPage(limit, offset, searchStr, status, orderBy, descStr));
        }
        return page;
    }

    @Override
    public UserVO getDetail(Long userId) {
        return userInfoMapper.getDetailById(userId);
    }

    @Override
    public void update(UserInfo userInfo) throws Exception {
        //校验用户名称是否重复
        checkUsernameByUserId(userInfo.getUsername(), userInfo.getId());
        int count = userInfoMapper.update(userInfo);
        if (count <= 0) {
            throw new PrivateException(ErrorInfo.SAVE_ERROR);
        }
    }

    @Override
    public void updateStatus(Long userId, UserStatus index) throws Exception {
        int i = userInfoMapper.updateStatus(index.getIndex(), userId);
        if (i <= 0) {
            throw new PrivateException(ErrorInfo.STATUS_ERROR);
        }
    }

    @Override
    public void delete(Long[] userIds) throws Exception {
        int i = userInfoMapper.updateDr(userIds);
        if (i <= 0) {
            throw new PrivateException(ErrorInfo.DELETE_ERROR);
        }
    }

    @Override
    public void loginOut() throws Exception {
        UserInfo user = userInfoMapper.getById(valueHolder.getUserIdHolder());
        redisService.removeUserTokenByKey(TokenUtils.getKey(user));
    }

    @Override
    public void updatePassword(PasswordDTO passwordDTO) throws Exception {
        Long userIdHolder = valueHolder.getUserIdHolder();
        UserInfo user = userInfoMapper.getById(userIdHolder);
        if (user == null) {
            log.error("userId未能查询出用户数据！userId:{}", userIdHolder);
            throw new PrivateException(ErrorInfo.ERROR);
        }
        String password = PasswordUtil.getPassword(passwordDTO.getOldPassword(), user.getSalt());
        if (!user.getPassword().equals(password)) {
            log.info("userId:{}, oldPassword:{}, password:{}", userIdHolder, password, user.getPassword());
            throw new PrivateException(ErrorInfo.PASSWORD_ERROR);
        }
        UUID uuid = UUID.randomUUID();
        String salt = uuid.toString();
        password = PasswordUtil.getPassword(passwordDTO.getNewPassword(), salt);
        int count = userInfoMapper.updatePassword(userIdHolder, password, salt);
        if (count <= 0) {
            throw new PrivateException(ErrorInfo.UPDATE_ERROR);
        }
    }

    @Override
    public UserInfo save(UserInfo userInfo) throws Exception {
        //校验用户名称是否重复
        checkUsername(userInfo.getUsername());
        UUID uuid = UUID.randomUUID();
        String salt = uuid.toString();
        userInfo.setSalt(salt);
        userInfo.setPassword(PasswordUtil.getPassword(userInfo.getPassword(), salt));
        userInfo.setStatus(UserStatus.DEFAULT.getIndex());
        Long userIdHolder = valueHolder.getUserIdHolder();
        userInfo.setOperatorId(userIdHolder);
        int count = userInfoMapper.save(userInfo);
        if (count > 0) {
            return userInfo;
        }
        return null;
    }

    private void checkUsernameByUserId(String username, Long userId) throws Exception {
        UserInfo user = userInfoMapper.getUserInfoNoStateNoId(username, userId);
        if (user != null) {
            throw new PrivateException(ErrorInfo.USER_NAME_SAME);
        }
    }

    //设置token
    private void saveRedis(UserInfo userInfo, boolean isToken) throws Exception {
        userInfo.setKey(TokenUtils.getKey(userInfo));
        if (isToken) {
            userInfo.setToken(TokenUtils.getToken(userInfo));
        }
        redisService.saveUser(userInfo);
    }

    private void checkUsername(String username) throws Exception {
        UserInfo user = userInfoMapper.getUserInfoNoState(username);
        if (user != null) {
            throw new PrivateException(ErrorInfo.USER_NAME_SAME);
        }
    }

    //校验密码
    private boolean checkUser(String passwordStr, UserInfo newUserInfo) throws Exception {
        if (newUserInfo.getStatus() == UserStatus.FREEZE.getIndex()) {
            log.info("该用户被冻结！username：{}", newUserInfo.getUsername());
            throw new PrivateException(ErrorInfo.USER_FREEZE);
        }
        if (newUserInfo.getStatus() == UserStatus.UNACTIVATED.getIndex()) {
            log.info("该用户还未审核通过！username：{}", newUserInfo.getUsername());
            throw new PrivateException(ErrorInfo.USER_UNACTIVATED);
        }
        String password = PasswordUtil.getPassword(passwordStr, newUserInfo.getSalt());
        return password.equals(newUserInfo.getPassword());
    }

    //校验猜密码次数
    private Integer checkPasswordNumber(String username) throws Exception {
        Integer number = redisService.getUserPasswordNumber(username);
        if (number >= SystemConstants.FREEZE_NUMBER) {
            log.info("该用户被停止登录！username：{}", username);
            throw new PrivateException(ErrorInfo.USER_NO_LOGIN);
        }
        return number;
    }
}
