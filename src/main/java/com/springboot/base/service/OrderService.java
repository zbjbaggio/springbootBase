package com.springboot.base.service;

import com.springboot.base.data.base.Page;
import com.springboot.base.data.entity.OrderInfo;
import com.springboot.base.data.vo.OrderDetailVO;
import com.springboot.base.data.vo.OrderVO;

import java.util.List;

public interface OrderService {

    Page listPage(int limit, int offset, String searchStr, int status, String orderBy, boolean desc);

    void delete(Long[] orderIds) throws Exception;

    OrderInfo save(OrderInfo order) throws Exception;

    void updatePaymentId(Long orderId, String paymentId) throws Exception;

    void updateStatusByPaymentId(String paymentId, byte newOrderStatus, byte oldOrderStatus) throws Exception;

    void updateStatusByOrderId(Long orderId, byte newOrderStatus, byte oldOrderStatus) throws Exception;

    void updateStatusByOrderId(Long orderId, byte newOrderStatus) throws Exception;

    OrderVO getByPaymentId(String paymentId);

    List<OrderDetailVO> getOrderDetailById(Long orderId);
}
