package com.springboot.base.mapper;

import com.springboot.base.data.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：角色dao
 * Created by jay on 2017-10-12.
 */
public interface RoleMapper {

    Long count(@Param("searchStr")String searchStr, @Param("status")Boolean status);

    List<UserVO> listPage(@Param("limit")int limit, @Param("offset")int offset, @Param("searchStr")String searchStr, @Param("status")Boolean status);

}
