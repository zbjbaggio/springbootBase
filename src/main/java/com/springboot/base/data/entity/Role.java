package com.springboot.base.data.entity;

import com.springboot.base.data.base.EntityBase;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * 角色表
 * Created by jay on 2017-4-7.
 */
@Data
public class Role extends EntityBase implements Serializable {

    @NotEmpty
    private boolean available = true;

    @NotEmpty
    private String description;

    @NotEmpty
    private String name;

}
