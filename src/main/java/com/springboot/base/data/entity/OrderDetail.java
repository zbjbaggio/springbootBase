package com.springboot.base.data.entity;

import com.springboot.base.data.base.EntityBase;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString(callSuper = true)
public class OrderDetail extends EntityBase implements Serializable {

    private Long orderId;

    @NotNull(message = "产品id不能为空")
    private Long productId;

    private String productName;

    private BigDecimal price;

    @Min(value = 1, message = "数量最小为1")
    private long number;

    @NotEmpty(message = "size不能为空")
    @Size(max = 10, message = "size字符长度不能超过10")
    private String size;

    private BigDecimal amount;

}
