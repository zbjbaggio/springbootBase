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
    private Date createTime;

    private String name;

    private String icon;

    private boolean available;

    private Long parentId;

    private String parentIds;

    private String permission;

    private String resourceType;

    private String feUrl;

    private String beUrl;

    private String code;

    private Boolean hasButton;

    List<PermissionVO> children;

    List<PermissionVO> button;
}
