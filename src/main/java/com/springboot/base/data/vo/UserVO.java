package com.springboot.base.data.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

/**
 * 描述：
 * Created by jay on 2017-9-28.
 */
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
@Data
public class UserVO {

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

}
