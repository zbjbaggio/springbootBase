package com.springboot.base.data.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 描述：订单信息
 * Created by jay on 2017-11-9.
 */
@Data
public class OrderInfo {

    private BigDecimal total;

    private String description;



}
