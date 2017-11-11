package com.springboot.base.data.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ProductVO {

    private Long id;

    private Date createTime;

    private String name;

    private String title;

    private String description;

    private BigDecimal price;
}
