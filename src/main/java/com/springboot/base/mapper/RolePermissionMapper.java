package com.springboot.base.mapper;

import com.springboot.base.data.entity.RolePermission;

import java.util.List;

/**
 * 描述：
 * Created by jay on 2018-1-15.
 */
public interface RolePermissionMapper {

    void delete(long roleId);

    void saves(List<RolePermission> saves);
}
