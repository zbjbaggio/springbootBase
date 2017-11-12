package com.springboot.base.service;

import com.springboot.base.data.entity.ManagerInfo;

/**
 * 描述：redis 服务
 * Created by jay on 2017-9-28.
 */
public interface RedisService {

    void saveUser(ManagerInfo managerInfo);

    //ManagerInfo getUserInfo(ManagerInfo userInfo) throws Exception;

    void saveUserPasswordNumber(String username, Integer number);

    Integer getUserPasswordNumber(String username);

    ManagerInfo getUserInfoByKey(String key);

    void removeUserTokenByKey(String key);

    void removeUserPasswordNumberByKey(String key);
}
