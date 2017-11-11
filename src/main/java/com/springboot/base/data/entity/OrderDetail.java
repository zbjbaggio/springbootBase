package com.springboot.base.data.entity;

import com.springboot.base.data.base.EntityBase;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString(callSuper = true)
public class OrderDetail extends EntityBase implements Serializable {

    @NotNull(message = "订单id不能为空")
    private Long orderId;

    @NotNull(message = "产品id不能为空")
    private Long productId;

    @NotEmpty(message = "产品名称不能为空")
    @Max(value = 100, message = "产品名称长度超长")
    private String productName;

    @NotNull(message = "单价不能为空")
    @Min(value = 0, message = "单价最小为0")
    private BigDecimal price;

    @Min(value = 1, message = "数量最小为1")
    private long number;

    @NotEmpty(message = "size不能为空")
    private String size;

    @NotNull(message = "总额不能为空")
    @Min(value = 0, message = "总额最小为0")
    private BigDecimal sum;

}
