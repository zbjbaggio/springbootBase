package com.springboot.base.service;

import com.springboot.base.data.base.Page;
import com.springboot.base.data.entity.OrderDetail;
import com.springboot.base.data.entity.OrderInfo;
import com.springboot.base.data.vo.OrderVO;

import java.util.List;

public interface OrderService {

    Page listPage(int limit, int offset, String searchStr, int status, String orderBy, boolean desc);

    OrderVO getDetail(Long orderId);

    void delete(Long[] orderIds) throws Exception;

    OrderInfo save(OrderInfo order) throws Exception;

    List<OrderDetail> listOrderDetailDetail(Long orderId);

}
