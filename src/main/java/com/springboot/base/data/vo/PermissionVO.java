package com.springboot.base.data.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.springboot.base.data.entity.Permission;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 描述：
 * Created by jay on 2017-12-13.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PermissionVO {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time;

    private String name;

    private String icon;

    private boolean available;

    private Long parent_id;

    private String parent_ids;

    private String permission;

    private String resource_type;

    private String fe_url;

    private String be_url;

    private String code;

    List<PermissionVO> children;

    List<PermissionVO> button;
}
