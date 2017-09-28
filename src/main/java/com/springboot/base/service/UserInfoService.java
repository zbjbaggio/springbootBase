package com.springboot.base.service;

import com.springboot.base.data.entity.UserInfo;
import com.springboot.base.data.vo.UserVO;

/**
 *
 * Created by jay on 2017-4-10.
 */
public interface UserInfoService {

    UserVO login(UserInfo user) throws Exception;

    UserInfo save(UserInfo userInfo) throws Exception;

    UserInfo getUserNoState(String username);

}
