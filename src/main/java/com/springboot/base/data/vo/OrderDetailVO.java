package com.springboot.base.data.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.springboot.base.JsonSerializer.CustomDoubleSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderDetailVO {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time;

    private String order_no;

    private String email;

    private String receiver_name;

    private String receiver_address;

    private String receiver_city;

    private String receiver_country;

    private String remark;

    private String description;

    private String postage;

    private byte status;

    @JsonSerialize(using = CustomDoubleSerialize.class)
    private BigDecimal amount;
}
