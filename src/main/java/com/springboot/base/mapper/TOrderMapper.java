package com.springboot.base.mapper;

import com.springboot.base.data.entity.OrderDetail;
import com.springboot.base.data.entity.OrderInfo;
import com.springboot.base.data.vo.OrderVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TOrderMapper {

    int save(OrderInfo order);

    List<OrderVO> select();
}
