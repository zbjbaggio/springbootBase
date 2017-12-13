package com.springboot.base.data.entity;

import com.springboot.base.data.base.EntityBase;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * 描述：管理员角色关系表
 * Created by jay on 2017-12-13.
 */
@Data
@ToString(callSuper = true)
public class ManagerRoler extends EntityBase implements Serializable {

    @NotEmpty
    private long roleId;

    @NotEmpty
    private long managerId;

}
