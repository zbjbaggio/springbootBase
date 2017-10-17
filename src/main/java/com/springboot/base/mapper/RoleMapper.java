package com.springboot.base.mapper;

import com.springboot.base.data.entity.Role;
import com.springboot.base.data.vo.RoleVO;
import com.springboot.base.data.vo.UserVO;
import org.apache.ibatis.annotations.Insert;
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

    List<UserVO> listPage(@Param("limit")int limit, @Param("offset")int offset, @Param("searchStr")String searchStr, @Param("status")Boolean status, @Param("orderBy")String orderBy, @Param("descStr")String descStr);

    Long save(Role role);

    Long countRoleName(Role role);

    @Update("update t_role_info set name = #{name}, available = #{available}, description = #{description} where id = #{id} ")
    int update(Role role);

    @Select("select * from t_role_info where id = #{param1} ")
    RoleVO getDetailById(Long roleId);
}
