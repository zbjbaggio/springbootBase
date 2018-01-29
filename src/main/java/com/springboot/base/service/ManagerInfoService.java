package com.springboot.base.service;

import com.springboot.base.data.base.Page;
import com.springboot.base.data.dto.PasswordDTO;
import com.springboot.base.data.enmus.UserStatus;
import com.springboot.base.data.entity.ManagerInfo;
import com.springboot.base.data.vo.ManagerVO;

import java.util.List;

/**
 * Created by jay on 2017-4-10.
 */
public interface ManagerInfoService {

    ManagerVO login(ManagerInfo user) throws Exception;

    ManagerInfo save(ManagerInfo managerInfo) throws Exception;

    boolean checkToken(String token, String key, String url);

    Page listPage(int limit, int offset, String searchStr, int status, String orderBy, boolean desc);

    ManagerVO getDetail(Long userId);

    void update(ManagerInfo managerInfo) throws Exception;

    void updateStatus(Long userId, UserStatus index) throws Exception;

    void unlockedUserStatus(Long userId) throws Exception;

    void remove(Long[] userIds) throws Exception;

    void loginOut() throws Exception;

    void updatePassword(PasswordDTO passwordDTO) throws Exception;

    List<ManagerVO> listAllByRoleId(Long roleId);
}
