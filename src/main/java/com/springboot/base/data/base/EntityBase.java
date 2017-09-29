package com.springboot.base.data.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 表的基本属性
 * Created by jay on 2017-4-7.
 */
@Data
public class EntityBase implements Serializable {

    private Long id;

    private Date createTime;

    private Date lastModified;
}
