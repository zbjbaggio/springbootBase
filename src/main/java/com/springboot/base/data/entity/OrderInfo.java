package com.springboot.base.data.entity;

import com.springboot.base.data.base.EntityBase;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 描述：订单信息
 * Created by jay on 2017-10-18.
 */
@Data
@ToString(callSuper = true)
public class OrderInfo extends EntityBase implements Serializable {

    private String orderNo;

    @NotNull(message = "金额不能为空")
    @Min(value = 0, message = "金额最小为0")
    private BigDecimal amount;

    @NotNull(message = "邮费不能为空")
    @Min(value = 0, message = "邮费最小为0")
    private BigDecimal postage;

    @NotEmpty(message = "邮编不能为空")
    @Length(message = "邮编长度不能超过100", max = 100)
    private String postcode;

    @NotEmpty(message = "邮箱不能为空")
    @Length(max = 50, message = "email长度超长")
    private String email;

    @NotEmpty(message = "收货人名称不能为空")
    @Length(max = 50, message = "收货人名称长度超长")
    private String receiverName;

    @NotEmpty(message = "收货地址1不能为空")
    @Length(max = 200, message = "收货地址1长度超长")
    private String receiverAddress1;

    @Length(max = 200, message = "收货地址2长度超长")
    private String receiverAddress2;

    @NotEmpty(message = "收货城市不能为空")
    @Length(max = 100, message = "收货城市长度超长")
    private String receiverCity;

    @NotEmpty(message = "收货地区不能为空")
    @Length(max = 100, message = "收货地区长度超长")
    private String receiverArea;

    @NotEmpty(message = "收货国家不能为空")
    @Length(max = 100, message = "收货国家长度超长")
    private String receiverCountry;

    @Min(value = 0, message = "订单状态最小为0")
    private int status;

    @NotNull(message = "邮费Id不能为空")
    private Long postageId;

    //描述
    private String description;

    //备注
    private String remark;

    private List<OrderDetail> orderDetailList;

}
