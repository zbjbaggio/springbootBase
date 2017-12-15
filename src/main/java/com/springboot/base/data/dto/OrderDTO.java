package com.springboot.base.data.dto;

import com.springboot.base.data.entity.OrderDetail;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 描述：
 * Created by jay on 2017-11-14.
 */
@Data
public class OrderDTO {

    @NotEmpty(message = "邮箱不能为空")
    @Size(max = 50, message = "email长度超长")
    private String email;

    @NotEmpty(message = "手机不能为空")
    @Length(max = 50, message = "手机长度超长")
    private String phone;

    @NotEmpty(message = "收货人名称不能为空")
    @Length(max = 50, message = "收货人名称长度超长")
    private String receiverName;

    @NotEmpty(message = "收货地址1不能为空")
    @Length(max = 100, message = "收货地址1长度超长")
    private String receiverAddress1;

    @Length(max = 100, message = "收货地址2长度超长")
    private String receiverAddress2;

    @NotEmpty(message = "收货城市不能为空")
    @Length(max = 100, message = "收货城市长度超长")
    private String receiverCity;

    @NotEmpty(message = "收货国家不能为空")
    @Length(max = 100, message = "收货国家长度超长")
    private String receiverCountry;

    @NotEmpty(message = "收货地区不能为空")
    @Length(max = 100, message = "收货地区长度超长")
    private String receiverArea;

    @NotNull(message = "邮费Id不能为空")
    private Long postageId;

    @NotEmpty(message = "邮编不能为空")
    @Length(message = "邮编长度不能超过100", max = 100)
    private String postcode;

    //描述
    private String description;

    @Valid
    @NotNull
    private List<OrderDetail> orderDetailList;
}
