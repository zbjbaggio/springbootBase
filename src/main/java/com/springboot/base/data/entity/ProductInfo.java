package com.springboot.base.data.entity;

import com.springboot.base.data.base.EntityBase;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString(callSuper = true)
public class ProductInfo extends EntityBase implements Serializable{

    @NotNull(groups = {Modify.class}, message = "产品Id不能为空！")
    private Long id;

    @NotEmpty(groups = {BaseInfo.class, Modify.class}, message = "产品编号不能为空")
    @Length(groups = {BaseInfo.class, Modify.class}, max = 10, message = "产品编号长度超长")
    private String productNo;

    @NotEmpty(groups = {BaseInfo.class, Modify.class}, message = "产品名称不能为空")
    @Length(groups = {BaseInfo.class, Modify.class}, max = 100, message = "产品名称长度超长")
    private String name;

    @NotEmpty(groups = {BaseInfo.class, Modify.class}, message = "标题不能为空")
    @Length(groups = {BaseInfo.class, Modify.class}, max = 100, message = "标题长度超长")
    private String title;

    @Length(groups = {BaseInfo.class, Modify.class}, max = 200, message = "描述长度超长")
    private String description;

    @NotNull(groups = {BaseInfo.class, Modify.class}, message = "单价不能为空")
    @Min(groups = {BaseInfo.class, Modify.class}, value = 0, message = "单价最小为0")
    private BigDecimal price;

    @NotNull(groups = {BaseInfo.class, Modify.class}, message = "产品状态不能为空！")
    private byte status;

    private Long operatorId;

    public interface BaseInfo {
    }

    public interface Modify {
    }

}
