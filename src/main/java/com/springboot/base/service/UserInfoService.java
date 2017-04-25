package com.springboot.base.service;

import com.springboot.base.data.entity.UserInfo;
import org.apache.shiro.session.Session;

import java.io.Serializable;

/**
 *
 * Created by jay on 2017-4-10.
 */
public interface UserInfoService {

    UserInfo save(UserInfo userInfo) throws Exception;

    UserInfo getUserNoState(String username);

    UserInfo getUser(String username, byte index);

    UserInfo login(UserInfo userInfo);

    Session getUserBySessionId(Serializable sessionId);

}
