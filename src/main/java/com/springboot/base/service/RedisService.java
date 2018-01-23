package com.springboot.base.service;

import com.springboot.base.data.entity.ManagerInfo;

import java.util.concurrent.TimeUnit;

/**
 * 描述：redis 服务
 * Created by jay on 2017-9-28.
 */
public interface RedisService {

    String USER_TOKEN_KEY = "user_token";//用户登录后存入redis的key

    String USER_PASSWORD_NUMBER_KEY = "user_password";//用户猜密码次数存入redis的key

    String USER_EXPECT_NUMBER_KEY = "user_expect_number";//用户欲冻结次数


    /**
     * FUNCTION
     */

    void saveUser(ManagerInfo managerInfo);

    //ManagerInfo getUserInfo(ManagerInfo userInfo) throws Exception;

    void saveUserPasswordNumber(String username, Integer number);

    void saveUserPasswordNumberSameIP(String username, String ip, Integer number);

    Integer getUserExpectNumber(String key);

    Integer getUserPasswordNumber(String username);

    Integer getUserPasswordNumberSameIP(String username, String ip);

    ManagerInfo getUserInfoByKey(String key);

    void removeUserTokenByKey(String key);

    void removeUserPasswordNumberByKey(String key, String ip);

    void save(String key, Object value, long time, TimeUnit timeUnit);

    void save(String key, Object value);
}
