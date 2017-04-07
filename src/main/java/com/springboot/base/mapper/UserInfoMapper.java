package com.springboot.base.mapper;

import com.springboot.base.data.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户dto
 * Created by jay on 2017-4-7.
 */
@Mapper
public interface UserInfoMapper {

    @Select("select * from t_user_info ")
    UserInfo getUserInfo();

    List<UserInfo> getAll();

}
