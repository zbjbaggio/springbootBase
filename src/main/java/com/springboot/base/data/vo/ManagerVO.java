package com.springboot.base.data.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 描述：
 * Created by jay on 2017-9-28.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ManagerVO {

    private Long id;

    private String username;

    private String email;

    private String name;

    private String key;

    private String token;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time;

    private String phone;

    private byte status;

    private List<PermissionVO> permissionList;

    private Set<String> buttonSet;

}
