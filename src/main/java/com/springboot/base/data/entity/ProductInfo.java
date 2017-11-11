package com.springboot.base.data.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ProductInfo {

    @NotEmpty(message = "产品名称不能为空")
    @Max(value = 100, message = "产品名称长度超长")
    private String name;

    @NotEmpty(message = "标题不能为空")
    @Max(value = 100, message = "标题长度超长")
    private String title;

    @Max(value = 200, message = "描述长度超长")
    private String description;

    @NotNull(message = "单价不能为空")
    @Min(value = 0, message = "单价最小为0")
    private BigDecimal price;
}
