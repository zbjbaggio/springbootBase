package com.springboot.base.service;

import com.springboot.base.data.entity.UserInfo;

/**
 * 描述：redis 服务
 * Created by jay on 2017-9-28.
 */
public interface RedisService {

    void saveUser(UserInfo userInfo);

    //UserInfo getUserInfo(UserInfo userInfo) throws Exception;

    void saveUserPasswordNumber(String username, Integer number);

    Integer getUserPasswordNumber(String username);

    UserInfo getUserInfoByKey(String key);

    void removeUserTokenByKey(String key);
}
