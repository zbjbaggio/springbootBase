package com.springboot.base.mapper;

import com.springboot.base.data.entity.ProductInfo;
import com.springboot.base.data.vo.ProductVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ProductMapper {

    Long count(@Param("searchStr") String searchStr, @Param("status") int status);

    List<ProductVO> listPage(@Param("limit") int limit, @Param("offset") int offset, @Param("searchStr") String searchStr, @Param("status") int status, @Param("orderBy") String orderBy, @Param("descStr") String descStr);

    int save(ProductInfo productInfo);

    @Select("select id from t_product_info where product_no = #{param1} ")
    ProductInfo getProductNoStateNoId(String productNo);

    @Select("select id from t_product_info where product_no = #{param1} and id <> #{param2}")
    ProductInfo getProductNoState(String productNo, Long id);

    @Update("update t_product_info set name = #{name}, title = #{title}, status = #{status},price = #{price}, operator_id = #{operatorId}, description = #{description}, product_no=#{productNo} where id = #{id}")
    int update(ProductInfo productInfo);

    @Select("select * from t_product_info where id = #{param1} and dr = 0")
    ProductVO getDetailById(Long userId);

    @Update("update t_product_info set status = #{param1} where id = #{param2}")
    int updateStatus(byte index, Long productId);

    int updateDr(@Param("productIds")Long[] productIds);

    List<ProductVO> getByIds(@Param("productIds")List<Long> productIds);

}
