package com.springboot.base.service;

import com.springboot.base.data.base.Page;
import com.springboot.base.data.entity.RoleInfo;
import com.springboot.base.data.vo.RoleVO;

/**
 * 描述：
 * Created by jay on 2017-10-12.
 */
public interface RoleService {

    Page listPage(int limit, int offset, String searchStr, Boolean status, String orderBy, Boolean desc);

    Long save(RoleInfo role) throws Exception;

    RoleVO getDetail(Long roleId);
}
