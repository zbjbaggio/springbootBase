package com.springboot.base.data.entity;

import com.springboot.base.data.base.EntityBase;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色菜单关系表
 * Created by jay on 2017-4-7.
 */
@Data
public class RolePermission extends EntityBase implements Serializable {

    private long roleId;

    private long permissionId;

}
