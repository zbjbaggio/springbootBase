package com.springboot.base.mapper;

import com.springboot.base.data.entity.UserInfo;
import com.springboot.base.data.vo.UserVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用户dto
 * Created by jay on 2017-4-7.
 */
public interface UserInfoMapper {

    @Select("select * from t_user_info where username = #{username}")
    UserInfo getUserInfoNoState(String username);

    @Select("select * from t_user_info where username = #{param1} and id <> #{param2}")
    UserInfo getUserInfoNoStateNoId(String username, Long userId);

    @Select("select * from t_user_info where username = #{param1} and dr = 0 ")
    UserInfo getUserInfo(String username);

    int save(UserInfo userInfo);

    Long count(@Param("searchStr")String searchStr, @Param("status")int status);

    List<UserVO> listPage(@Param("limit") int limit, @Param("offset") int offset, @Param("searchStr") String searchStr, @Param("status") int status, @Param("orderBy")String orderBy, @Param("descStr")String descStr);

    @Select("select * from t_user_info where id = #{param1} and dr = 0")
    UserVO getDetailById(Long userId);

    @Select("select * from t_user_info where id = #{param1}")
    UserInfo getById(Long userId);

    @Update("update t_user_info set username = #{username}, email = #{email}, name = #{name}, phone = #{phone}, status = #{status} where id = #{id}")
    int update(UserInfo userInfo);

    @Update("update t_user_info set status = #{param1} where id = #{param2}")
    int updateStatus(byte index, Long userId);

    int updateDr(@Param("userIds")Long[] userIds);

    @Update("update t_user_info set password = #{param2}, salt = #{param3} where id = #{param1}")
    int updatePassword(Long userIdHolder, String newPassword, String salt);
}
