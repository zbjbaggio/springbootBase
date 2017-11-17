package com.springboot.base.mapper;

import com.springboot.base.data.entity.OrderDetail;

import java.util.List;

/**
 * 描述：
 * Created by jay on 2017-11-17.
 */
public interface OrderDetailMapper {

    List<OrderDetail> listOrderDetail(List<Long> orderIds);

}
