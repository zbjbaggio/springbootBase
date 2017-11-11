package com.springboot.base.data.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ProductVO {

    private String name;

    private String title;

    private String description;

    private BigDecimal price;
}
