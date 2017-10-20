package com.springboot.base.data.entity;

import com.springboot.base.data.base.EntityBase;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 描述：订单信息
 * Created by jay on 2017-10-18.
 */
@Data
public class Order extends EntityBase implements Serializable {

    @NotEmpty(message = "收货地址不能为空")
    private String address;

    @NotNull(message = "金额不能为空")
    @Min(value = 0, message = "最小为0")
    private BigDecimal amount;
    
}
