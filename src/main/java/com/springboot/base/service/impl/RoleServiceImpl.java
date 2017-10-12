package com.springboot.base.service.impl;

import com.springboot.base.data.base.Page;
import com.springboot.base.mapper.RoleMapper;
import com.springboot.base.service.RoleService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * 描述：角色服务
 * Created by jay on 2017-10-12.
 */
@Service
@Log4j
public class RoleServiceImpl implements RoleService {

    @Inject
    private RoleMapper roleMapper;

    @Override
    public Page listPage(int limit, int offset, String searchStr, Boolean status) {
        if (!"-1".equals(searchStr)) {
            searchStr = "%" + searchStr + "%";
        }
        Page page = new Page();
        Long count = roleMapper.count(searchStr, status);
        if (count != 0) {
            page.setCount(count);
            page.setList(roleMapper.listPage(limit, offset, searchStr, status));
        }
        return page;
    }

}
