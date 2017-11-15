package com.springboot.base.mapper;

import com.springboot.base.data.entity.OrderDetail;
import com.springboot.base.data.entity.OrderInfo;
import com.springboot.base.data.vo.OrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {

    Long count(@Param("searchStr") String searchStr, @Param("status") int status);

    List<OrderVO> listPage(@Param("limit") int limit, @Param("offset") int offset, @Param("searchStr") String searchStr, @Param("status") int status, @Param("orderBy") String orderBy, @Param("descStr") String descStr);

    int save(OrderInfo order);

    int saveDetails(List<OrderDetail> orderDetailList);
}
