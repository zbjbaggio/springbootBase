package com.springboot.base.data.entity;

import com.springboot.base.data.base.EntityBase;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 描述：邮费信息
 * Created by jay on 2017-11-24.
 */
@Data
@ToString(callSuper = true)
public class PostageInfo extends EntityBase implements Serializable {

    @NotNull(groups = {ProductInfo.Modify.class}, message = "邮费Id不能为空！")
    private Long id;

    @NotEmpty(groups = {BaseInfo.class, Modify.class}, message = "邮费名称不能为空")
    @Length(groups = {BaseInfo.class, Modify.class}, max = 50, message = "邮费名称长度超长")
    private String name;

    @NotNull(groups = {BaseInfo.class, Modify.class}, message = "单价不能为空")
    @Min(groups = {BaseInfo.class, Modify.class}, value = 0, message = "单价最小为0")
    private BigDecimal price;

    public interface BaseInfo {
    }

    public interface Modify {
    }

}
