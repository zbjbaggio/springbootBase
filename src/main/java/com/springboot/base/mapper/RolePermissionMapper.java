package com.springboot.base.mapper;

import com.springboot.base.data.entity.RolePermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：
 * Created by jay on 2018-1-15.
 */
public interface RolePermissionMapper {

    @Delete("delete from t_role_permission where role_id = #{roleId} ")
    int remove(@Param("roleId")long roleId);

    int saves(List<RolePermission> saves);
}
