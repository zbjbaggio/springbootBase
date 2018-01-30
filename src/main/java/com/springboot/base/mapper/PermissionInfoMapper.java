package com.springboot.base.mapper;

import com.springboot.base.data.enmus.ResourceType;
import com.springboot.base.data.entity.Permission;
import com.springboot.base.data.vo.PermissionVO;
import com.springboot.base.data.vo.TreeVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 描述：权限
 * Created by jay on 2017-12-13.
 */
public interface PermissionInfoMapper {

    @Select("select distinct a.name,a.parent_id parentId,a.permission,a.resource_type resourceType,a.fe_url feUrl,a.be_url beUrl,a.icon from t_permission_info a join t_role_permission b on a.id = b.permission_id join t_manager_role c on b.role_id = c.role_id where c.manager_id = #{managerId} order by code ")
    List<PermissionVO> listByManagerId(@Param("managerId") Long managerId);

    Long count(@Param("searchStr")String searchStr, @Param("available")Boolean available);

    List listPage(@Param("limit") int limit, @Param("offset") int offset, @Param("searchStr") String searchStr, @Param("available") Boolean available, @Param("orderBy") String orderBy, @Param("descStr") String descStr);

    int remove(@Param("permissionIds")Long[] permissionIds);

    int save(Permission permission);

    @Select("select id,create_time createTime,name,icon,be_url beUrl,fe_url feUrl,parent_id parentId,permission,code,available from t_permission_info where id = #{param1} and resource_type=#{param2} ")
    PermissionVO getDetailById(Long menuId, Enum resource_type);

    @Update("update t_permission_info set name = #{name}, code = #{code}, be_url=#{beUrl}, fe_url=#{feUrl}, icon=#{icon}, available=#{available} where id = #{id}")
    int update(Permission permission);

    @Select("select id,create_time createTime,name,icon,be_url beUrl,fe_url feUrl,parent_id parentId,permission,code,available from t_permission_info where parent_id = #{param1} and resource_type = #{param2} ")
    List<PermissionVO> listByParentId(Long menuId, Enum resourceTpe);

    @Select("select count(id) from t_permission_info where parent_id = #{param1} and resource_type = #{param2} ")
    long countByParentId(Long menuId, Enum resourceTpe);

    @Delete("delete from t_permission_info where id = #{param1} and resource_type = #{param2}")
    int deleteByType(Long permissionId, Enum resourceTpe);

    @Select("select id from t_permission_info where code = #{param1} and resource_type= #{param2}")
    Long getIdByCode(String code, Enum resourceTpe);

    @Select("select count(id) from t_permission_info where code = #{param1} and id <> #{param2} ")
    long countByCodeAndId(String code, Long id);

    void removeByParentIds(@Param("permissionIds")Long[] permissionIds);

    List<TreeVO> listPermissionDetail(@Param("roleId")Long roleId);

    List<PermissionVO> listByIds(@Param("permissionIds")List<Long> permissionIds);

    @Select("select max(code) from t_permission_info where parent_id = #{param1} and resource_type = #{param2} ")
    String getMaxCodeByParentId(Long parentId, Enum resourceTpe);

    @Update("update t_permission_info,(select @rownum:=1) t set code = concat(#{param3}, substr(concat('0000',@rownum), length(concat('0000',@rownum:=@rownum+1))-3, 4)) where parent_id = #{param1} and resource_type = #{param2};")
    int updateButtonCode(Long permissionId, Enum resourceTpe, String code);
}
