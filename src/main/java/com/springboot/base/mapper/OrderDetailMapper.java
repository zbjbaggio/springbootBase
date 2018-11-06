package com.springboot.base.mapper;

import com.springboot.base.data.vo.OrderDetailVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 描述：
 * Created by jay on 2017-11-17.
 */
public interface OrderDetailMapper {

    List<OrderDetailVO> listOrderDetail(@Param("orderIds") List<Long> orderIds);

    @Select("select * from t_order_detail where order_id = #{orderId} ")
    List<OrderDetailVO> getByOrderId(@Param("orderId")Long orderId);
}
