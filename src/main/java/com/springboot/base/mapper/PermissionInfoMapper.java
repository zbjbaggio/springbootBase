package com.springboot.base.mapper;

import com.springboot.base.data.vo.PermissionVO;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 描述：权限
 * Created by jay on 2017-12-13.
 */
public interface PermissionInfoMapper {

    @Select("select a.name,a.parent_id,a.parent_ids,a.permission,a.resource_type,a.fe_url,a.be_url,a.icon from t_permission_info a join t_role_permission b on a.id = b.permission_id join t_manager_role c on b.role_id = c.role_id where c.manager_id = #{managerId} order by code ")
    List<PermissionVO> listByManagerId(@Param("managerId") Long managerId);

}
