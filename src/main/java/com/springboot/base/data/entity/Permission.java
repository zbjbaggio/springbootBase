package com.springboot.base.data.entity;

import com.springboot.base.data.base.EntityBase;
import lombok.Data;

import java.io.Serializable;

/**
 * 权限表
 * Created by jay on 2017-4-7.
 */
@Data
public class Permission extends EntityBase implements Serializable {

    private boolean available = true;

    private String name;

    private Long parent_id;

    private String parent_ids;

    private String permission;

    private String resource_type;

    private String url;
}
