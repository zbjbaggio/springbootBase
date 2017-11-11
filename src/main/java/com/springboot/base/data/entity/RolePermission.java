package com.springboot.base.data.entity;

import com.springboot.base.data.base.EntityBase;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * 角色菜单关系表
 * Created by jay on 2017-4-7.
 */
@Data
@ToString(callSuper = true)
public class RolePermission extends EntityBase implements Serializable {

    @NotEmpty
    private long roleId;

    @NotEmpty
    private long permissionId;

}
