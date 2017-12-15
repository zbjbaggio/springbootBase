package com.springboot.base.data.entity;

import com.springboot.base.data.base.EntityBase;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * 权限表
 * Created by jay on 2017-4-7.
 */
@Data
@ToString(callSuper = true)
public class Permission extends EntityBase implements Serializable {

    private boolean available = true;

    @NotEmpty
    private String name;

    @NotEmpty
    private Long parentId;

    @NotEmpty
    private String parentIds;

    @NotEmpty
    private String permission;

    @NotEmpty
    private String resourceType;
}
