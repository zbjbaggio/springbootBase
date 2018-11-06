package com.springboot.base.data.entity;

import com.springboot.base.data.base.EntityBase;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 角色菜单关系表
 * Created by jay on 2017-4-7.
 */
@Data
@ToString(callSuper = true)
public class RolePermission extends EntityBase implements Serializable {

    @NotNull(groups = {RolePermission.BaseInfo.class}, message = "角色id不能为空！")
    private long roleId;

    private long permissionId;

    @NotNull(groups = {RolePermission.BaseInfo.class}, message = "权限id不能为空！")
    private List<Long> permissionIds;

    public interface BaseInfo {
    }
}
