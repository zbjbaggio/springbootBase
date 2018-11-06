package com.springboot.base.data.entity;

import com.springboot.base.data.base.EntityBase;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 角色表
 * Created by jay on 2017-4-7.
 */
@Data
@ToString(callSuper = true)
public class RoleInfo extends EntityBase implements Serializable {

    @NotNull(groups = {RoleInfo.Modify.class}, message = "是否启用不能为空！")
    private boolean available = true;

    @NotNull(groups = {RoleInfo.Modify.class}, message = "描述不能为空！")
    private String description;

    @NotEmpty(groups = {RoleInfo.Modify.class}, message = "名称不能为空！")
    private String name;

    public interface Modify {
    }

}
