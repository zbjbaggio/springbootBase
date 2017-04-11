package com.springboot.base.service;

import com.springboot.base.data.entity.UserInfo;

/**
 *
 * Created by jay on 2017-4-10.
 */
public interface UserInfoService {

    UserInfo login(UserInfo user);

    UserInfo save(UserInfo userInfo) throws Exception;

    UserInfo getUserNoState(String username);

}
