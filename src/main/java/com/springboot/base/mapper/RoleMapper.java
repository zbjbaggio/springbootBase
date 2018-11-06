package com.springboot.base.mapper;

import com.springboot.base.data.entity.RoleInfo;
import com.springboot.base.data.vo.ManagerVO;
import com.springboot.base.data.vo.RoleVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 描述：角色dao
 * Created by jay on 2017-10-12.
 */
public interface RoleMapper {

    Long count(@Param("searchStr")String searchStr, @Param("status")Boolean status);

    List<ManagerVO> listPage(@Param("limit")int limit, @Param("offset")int offset, @Param("searchStr")String searchStr, @Param("status")Boolean status, @Param("orderBy")String orderBy, @Param("descStr")String descStr);

    int save(RoleInfo role);

    Long countRoleName(RoleInfo role);

    @Update("update t_role_info set name = #{name}, available = #{available}, description = #{description} where id = #{id} ")
    int update(RoleInfo role);

    @Select("select * from t_role_info where id = #{param1} ")
    RoleVO getDetailById(Long roleId);

    int remove(@Param("roleIds")Long[] roleIds);

    @Select("select a.id,a.name,b.manager_id managerId from t_role_info a left join t_manager_role b on a.id = b.role_id and b.manager_id = #{param1} ")
    List<RoleVO> listAllByUserId(Long userIdHolder);
}
