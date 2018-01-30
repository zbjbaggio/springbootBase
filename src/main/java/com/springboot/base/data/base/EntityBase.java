package com.springboot.base.data.base;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Date lastModified;
}
