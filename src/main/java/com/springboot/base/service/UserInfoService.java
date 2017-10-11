package com.springboot.base.service;

import com.springboot.base.data.base.Page;
import com.springboot.base.data.enmus.UserStatus;
import com.springboot.base.data.entity.UserInfo;
import com.springboot.base.data.vo.UserVO;

/**
 * Created by jay on 2017-4-10.
 */
public interface UserInfoService {

    UserVO login(UserInfo user) throws Exception;

    UserInfo save(UserInfo userInfo) throws Exception;

    boolean checkToken(String token, String key);

    Page listPage(int limit, int offset, String searchStr, int status);

    UserVO getDetail(Long userId);

    void update(UserInfo userInfo) throws Exception;

    void updateStatus(Long userId, UserStatus index) throws Exception;

    void delete(Long[] userIds) throws Exception;

}
