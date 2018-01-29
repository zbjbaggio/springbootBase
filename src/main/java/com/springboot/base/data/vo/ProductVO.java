package com.springboot.base.data.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.springboot.base.jsonserializer.CustomDoubleSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ProductVO {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String productNo;

    private String name;

    private byte status;

    private String title;

    private String description;

    @JsonSerialize(using = CustomDoubleSerialize.class)
    private BigDecimal price;
}
