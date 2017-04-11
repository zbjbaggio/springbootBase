package com.springboot.base.data.entity;

import com.springboot.base.data.base.EntityBase;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 用户表
 * Created by jay on 2017-4-7.
 */
@Data
public class UserInfo extends EntityBase implements Serializable {

    @NotEmpty(groups = {BaseInfo.class, LoginGroup.class})
    private String username;

    @NotEmpty(groups = {BaseInfo.class})
    @Pattern(regexp = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$", message = "邮箱格式不正确！")
    private String email;

    @NotEmpty(groups = {BaseInfo.class})
    private String name;

    @NotEmpty(groups = {BaseInfo.class, LoginGroup.class})
    private String password;

    @NotEmpty(groups = {BaseInfo.class})
    private String phone;

    private String salt;

    private byte state;

    private String operator_id;

    public interface LoginGroup {
    }

    public interface BaseInfo {
    }
}
