package com.springboot.base.mapper;

import com.springboot.base.data.entity.OrderDetail;
import com.springboot.base.data.entity.OrderInfo;
import com.springboot.base.data.vo.OrderVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface OrderMapper {

    Long count(@Param("searchStr") String searchStr, @Param("status") int status);

    List<OrderVO> listPage(@Param("limit") int limit, @Param("offset") int offset, @Param("searchStr") String searchStr, @Param("status") int status, @Param("orderBy") String orderBy, @Param("descStr") String descStr);

    int save(OrderInfo order);

    int saveDetails(List<OrderDetail> orderDetailList);

    int updateDr(@Param("orderIds")Long[] orderIds);

    @Update("update t_order_info set payment_id = #{paymentId} where id = #{orderId}")
    int updatePaymentId(@Param("orderId")Long orderId, @Param("paymentId")String paymentId);

    @Update("update t_order_info set status = #{newOrderStatus} where payment_id = #{paymentId} and status = #{oldOrderStatus} ")
    int updateStatusByPaymentId(@Param("paymentId")String paymentId, @Param("newOrderStatus")byte newOrderStatus, @Param("oldOrderStatus")byte oldOrderStatus);

    @Update("update t_order_info set status = #{newOrderStatus} where id = #{orderId} and status = #{oldOrderStatus} ")
    int updateStatusByOrderId(@Param("orderId")Long orderId, @Param("newOrderStatus")byte newOrderStatus, @Param("oldOrderStatus")byte oldOrderStatus);

    @Update("update t_order_info set status = #{newOrderStatus} where id = #{orderId} ")
    int updateStatusByOrderIdAndStatus(@Param("orderId")Long orderId, @Param("newOrderStatus")byte newOrderStatus);
}
