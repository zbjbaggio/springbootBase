package com.springboot.base.data.entity;

import com.springboot.base.data.base.EntityBase;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 描述：管理员角色关系表
 * Created by jay on 2017-12-13.
 */
@Data
@ToString(callSuper = true)
public class ManagerRole extends EntityBase implements Serializable {

    @NotNull(groups = Role.class, message = "角色id不能为空！")
    private long roleId;

    @NotNull(groups = Manager.class, message = "管理员id不能为空！")
    private long managerId;

    private long[] managerIds;

    private long[] roleIds;

    public interface Manager {
    }

    public interface Role {
    }

}
