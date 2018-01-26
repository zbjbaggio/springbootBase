package com.springboot.base.service;

import com.springboot.base.data.base.Page;
import com.springboot.base.data.entity.RoleInfo;
import com.springboot.base.data.entity.RolePermission;
import com.springboot.base.data.vo.PermissionTreeVO;
import com.springboot.base.data.vo.RoleVO;

import java.util.List;

/**
 * 描述：
 * Created by jay on 2017-10-12.
 */
public interface RoleService {

    Page listPage(int limit, int offset, String searchStr, Boolean status, String orderBy, Boolean desc);

    RoleInfo save(RoleInfo role) throws Exception;

    RoleVO getDetail(Long roleId);

    void remove(Long[] roleIds) throws Exception;

    List<RoleVO> listAllByUserId(Long userIdHolder);

}
