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

    Long save(RoleInfo role);

    Long countRoleName(RoleInfo role);

    @Update("update t_role_info set name = #{name}, available = #{available}, description = #{description} where id = #{id} ")
    int update(RoleInfo role);

    @Select("select * from t_role_info where id = #{param1} ")
    RoleVO getDetailById(Long roleId);
}
