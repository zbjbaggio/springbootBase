package com.springboot.base.data.dto;

import com.springboot.base.data.entity.OrderDetail;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 描述：
 * Created by jay on 2017-11-14.
 */
@Data
@ToString
public class OrderDTO {

    @NotEmpty(message = "邮箱不能为空")
    @Size(max = 50, message = "email长度超长")
    private String email;

    @NotEmpty(message = "收货人名称不能为空")
    @Size(max = 50, message = "收货人名称长度超长")
    private String receiverName;

    @NotEmpty(message = "收货地址不能为空")
    @Size(max = 100, message = "收货地址长度超长")
    private String receiverAddress;

    @NotEmpty(message = "收货城市不能为空")
    @Size(max = 100, message = "收货城市长度超长")
    private String receiverCity;

    @NotEmpty(message = "收货国家不能为空")
    @Size(max = 100, message = "收货国家长度超长")
    private String receiverCountry;

    //描述
    private String description;

    @Valid
    @NotNull
    private List<OrderDetail> orderDetailList;
}
