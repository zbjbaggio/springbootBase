package com.springboot.base.data.entity;

import com.springboot.base.data.base.EntityBase;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色用户关系表
 * Created by jay on 2016-10-13.
 */
@Data
public class UserRole extends EntityBase implements Serializable {

    private long roleId;

    private long userId;

}
