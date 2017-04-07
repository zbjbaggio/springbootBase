package com.springboot.base.data.entity;

import com.springboot.base.data.base.EntityBase;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * Created by jay on 2017-4-7.
 */
@Data
public class UserInfo extends EntityBase implements Serializable {

    private String username;

    private String email;

    private String name;

    private String password;

    private String phone;

    private String salt;

    private String state;

    private String operator_id;

}
