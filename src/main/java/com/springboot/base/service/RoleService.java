package com.springboot.base.service;

import com.springboot.base.data.base.Page;

/**
 * 描述：
 * Created by jay on 2017-10-12.
 */
public interface RoleService {

    Page listPage(int limit, int offset, String searchStr, Boolean status);

}
