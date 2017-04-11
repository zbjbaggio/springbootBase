package com.springboot.base.mapper;

import com.springboot.base.data.entity.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户dto
 * Created by jay on 2017-4-7.
 */
@Mapper
public interface UserInfoMapper {

    @Select("select * from t_user_info where username = #{username} ")
    UserInfo getUserInfoNoState(String username);

    @Select("select * from t_user_info where username = #{param1} and state = #{param2} ")
    UserInfo getUserInfo(String username, byte state);

    @Insert("insert into t_user_info(username,email,name,password,phone,salt,state,operator_id,create_time) values(#{username},#{email},#{name},#{password},#{phone},#{salt},#{state},#{operator_id},now())")
    int save(UserInfo userInfo);

    List<UserInfo> getAll();

}
