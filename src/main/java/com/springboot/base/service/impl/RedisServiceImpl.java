package com.springboot.base.service.impl;

import com.alibaba.fastjson.JSON;
import com.springboot.base.constant.ManagerLoginConstants;
import com.springboot.base.data.entity.ManagerInfo;
import com.springboot.base.service.RedisService;
import com.springboot.base.util.DateUtil;
import com.springboot.base.util.StringUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 描述：
 * Created by jay on 2017-9-28.
 */
@Service
@Log4j
public class RedisServiceImpl implements RedisService {

    @Inject
    private ManagerLoginConstants managerLoginConstants;

    @Inject
    private StringRedisTemplate template;

    @Override
    public void saveUser(ManagerInfo managerInfo) {
        save(StringUtil.concatStringWithSign("_", USER_TOKEN_KEY, managerInfo.getKey()), managerInfo, managerLoginConstants.getTime(), TimeUnit.MINUTES);
    }

/*    @Override
    public ManagerInfo getUserInfo(ManagerInfo userInfo) throws Exception {
        return get(USER_TOKEN_KEY + TokenUtils.getKey(userInfo), ManagerInfo.class);
    }*/

    /**
     * 保存该用户名猜密码次数
     * @param username 登录名
     * @param number 猜的次数
     */
    @Override
    public void saveUserPasswordNumber(String username, Integer number) {
        save(StringUtil.concatStringWithSign("_", USER_PASSWORD_NUMBER_KEY, username), number, managerLoginConstants.getVerifiedTime(), TimeUnit.MINUTES);
    }

    /**
     * 根据key获取用户欲冻结次数
     * @param username
     * @return
     */
    @Override
    public Integer getUserExpectNumber(String username) {
        String numberString = get(StringUtil.concatStringWithSign("_", RedisService.USER_LOCKED_NUMBER_KEY, username));
        return numberString == null ? 0 : Integer.parseInt(numberString);
    }

    /**
     * 获取该用户名猜密码次数
     * @param username 登录名
     */
    @Override
    public Integer getUserPasswordNumber(String username) {
        String numberString = get(StringUtil.concatStringWithSign("_", USER_PASSWORD_NUMBER_KEY, username));
        return numberString == null ? 0 : Integer.parseInt(numberString);
    }

    @Override
    public ManagerInfo getUserInfoByKey(String key) {
        return get(StringUtil.concatStringWithSign("_", USER_TOKEN_KEY, key), ManagerInfo.class);
    }

    @Override
    public void removeUserTokenByKey(String key) {
        template.delete(StringUtil.concatStringWithSign("_", USER_TOKEN_KEY, key));
    }

    @Override
    public void removeUserPasswordNumberByKey(String username) {
        template.delete(StringUtil.concatStringWithSign("_", USER_PASSWORD_NUMBER_KEY, username));
        template.delete(StringUtil.concatStringWithSign("_", USER_LOCKED_NUMBER_KEY, username));
    }

    @Override
    public void saveUserExpectNumber(String username, int lockedNumber) {
        Date nowDate = new Date(System.currentTimeMillis());
        long expectMin = DateUtil.getMinuteCompare(nowDate, DateUtil.getDayEndDate(nowDate));
        save(StringUtil.concatStringWithSign("_", RedisService.USER_LOCKED_NUMBER_KEY, username), lockedNumber, expectMin, TimeUnit.MINUTES);
    }

    private void save(String key, Object value, long time, TimeUnit timeUnit) {
        ValueOperations<String, String> ops = template.opsForValue();
        ops.set(key, JSON.toJSONString(value), time, timeUnit);
    }

    private void save(String key, Object value) {
        ValueOperations<String, String> ops = template.opsForValue();
        ops.set(key, JSON.toJSONString(value));
    }

    private <T> T get(String key, Class<T> clazz) {
        ValueOperations<String, String> ops = template.opsForValue();
        String value = ops.get(key);
        T t = null;
        if (value != null && value.length() > 0) {
            t = JSON.parseObject(value, clazz);
        }
        return t;
    }

    private String get(String key) {
        ValueOperations<String, String> ops = template.opsForValue();
        return ops.get(key);
    }

}