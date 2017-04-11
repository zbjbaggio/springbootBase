package com.springboot.base.data.entity;

import com.springboot.base.data.base.EntityBase;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * 权限表
 * Created by jay on 2017-4-7.
 */
@Data
public class Permission extends EntityBase implements Serializable {

    private boolean available = true;

    @NotEmpty
    private String name;

    @NotEmpty
    private Long parent_id;

    @NotEmpty
    private String parent_ids;

    @NotEmpty
    private String permission;

    @NotEmpty
    private String resource_type;

    @NotEmpty
    private String url;
}
