package com.springboot.base.mapper;

import com.springboot.base.data.entity.Permission;

import java.util.List;

/**
 * 描述：
 * Created by jay on 2017-12-13.
 */
public interface PermissionMapper {

    List<Permission> listByUserId(Long id);

}
