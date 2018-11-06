package com.springboot.base.data.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * 描述：
 * Created by jay on 2017-10-12.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RoleVO {

    private Long id;

    private boolean available = true;

    private String description;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Long managerId;
}
