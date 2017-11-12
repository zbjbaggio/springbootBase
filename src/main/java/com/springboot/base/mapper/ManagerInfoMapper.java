package com.springboot.base.mapper;

import com.springboot.base.data.entity.ManagerInfo;
import com.springboot.base.data.vo.ManagerVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用户dto
 * Created by jay on 2017-4-7.
 */
public interface ManagerInfoMapper {

    @Select("select * from t_manager_info where username = #{username}")
    ManagerInfo getUserInfoNoState(String username);

    @Select("select * from t_manager_info where username = #{param1} and id <> #{param2}")
    ManagerInfo getUserInfoNoStateNoId(String username, Long userId);

    @Select("select * from t_manager_info where username = #{param1} and dr = 0 ")
    ManagerInfo getUserInfo(String username);

    int save(ManagerInfo managerInfo);

    Long count(@Param("searchStr")String searchStr, @Param("status")int status);

    List<ManagerVO> listPage(@Param("limit") int limit, @Param("offset") int offset, @Param("searchStr") String searchStr, @Param("status") int status, @Param("orderBy")String orderBy, @Param("descStr")String descStr);

    @Select("select * from t_manager_info where id = #{param1} and dr = 0")
    ManagerVO getDetailById(Long userId);

    @Select("select * from t_manager_info where id = #{param1}")
    ManagerInfo getById(Long userId);

    @Update("update t_manager_info set username = #{username}, email = #{email}, name = #{name}, phone = #{phone}, status = #{status} where id = #{id}")
    int update(ManagerInfo managerInfo);

    @Update("update t_manager_info set status = #{param1} where id = #{param2}")
    int updateStatus(byte index, Long userId);

    int updateDr(@Param("userIds")Long[] userIds);

    @Update("update t_manager_info set password = #{param2}, salt = #{param3} where id = #{param1}")
    int updatePassword(Long userIdHolder, String newPassword, String salt);
}
