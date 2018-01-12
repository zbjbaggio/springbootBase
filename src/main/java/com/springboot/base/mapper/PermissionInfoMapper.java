package com.springboot.base.mapper;

import com.springboot.base.data.entity.Permission;
import com.springboot.base.data.vo.PermissionVO;
import com.springboot.base.data.vo.TreeVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 描述：权限
 * Created by jay on 2017-12-13.
 */
public interface PermissionInfoMapper {

    @Select("select a.name,a.parent_id,a.parent_ids,a.permission,a.resource_type,a.fe_url,a.be_url,a.icon from t_permission_info a join t_role_permission b on a.id = b.permission_id join t_manager_role c on b.role_id = c.role_id where c.manager_id = #{managerId} order by code ")
    List<PermissionVO> listByManagerId(@Param("managerId") Long managerId);

    Long count(@Param("searchStr")String searchStr, @Param("available")Boolean available);

    List listPage(@Param("limit") int limit, @Param("offset") int offset, @Param("searchStr") String searchStr, @Param("available") Boolean available, @Param("orderBy") String orderBy, @Param("descStr") String descStr);

    int remove(@Param("permissionIds")Long[] permissionIds);

    int save(Permission permission);

    @Select("select * from t_permission_info where id = #{param1} and resource_type=#{param2} ")
    PermissionVO getDetailById(Long menuId, Enum resource_type);

    @Update("update t_permission_info set name = #{name}, code = #{code}, be_url=#{beUrl}, fe_url=#{feUrl}, icon=#{icon}, available=#{available} where id = #{id}")
    int update(Permission permission);

    @Select("select * from t_permission_info where parent_id = #{param1} and resource_type = #{param2} ")
    List<PermissionVO> getListByParentId(Long menuId, Enum resourceTpe);

    @Delete("remove from t_permission_info where id = #{param1} and resource_type = #{param2}")
    int deleteByType(Long permissionId, Enum resourceTpe);

    @Select("select id from t_permission_info where code = #{param1} and resource_type= #{param2}")
    Long getIdByCode(String code, Enum resourceTpe);

    void removeByParentIds(@Param("permissionIds")Long[] permissionIds);

    List<TreeVO> listPermissionDetail(@Param("roleId")Long roleId);
}
