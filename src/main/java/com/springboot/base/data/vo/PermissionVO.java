package com.springboot.base.data.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * 描述：
 * Created by jay on 2017-12-13.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PermissionVO {

    private Long id;

    private String name;

    private String icon;

    private Long parent_id;

    private String parent_ids;

    private String permission;

    private String resource_type;

    private String fe_url;

    private String be_url;

    List<PermissionVO> child;
}
