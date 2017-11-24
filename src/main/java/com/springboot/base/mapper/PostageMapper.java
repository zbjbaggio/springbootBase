package com.springboot.base.mapper;

import com.springboot.base.data.entity.PostageInfo;
import com.springboot.base.data.vo.PostageVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 描述：
 * Created by jay on 2017-11-24.
 */
public interface PostageMapper {

    Long count(@Param("searchStr") String searchStr);

    List listPage(@Param("limit") int limit, @Param("offset") int offset, @Param("searchStr") String searchStr, @Param("orderBy") String orderBy, @Param("descStr") String descStr);

    int save(PostageInfo postageInfo);

    @Select("select * from t_postage_info where id = #{param1}")
    PostageVO getDetailById(Long postageId);

    @Update("update t_postage_info set name = #{name},price = #{price} where id = #{id}")
    int update(PostageInfo postageInfo);

    int delete(@Param("postageIds")Long[] postageIds);
}
