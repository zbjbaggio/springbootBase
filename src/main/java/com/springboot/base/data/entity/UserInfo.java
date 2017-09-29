package com.springboot.base.data.entity;

import com.springboot.base.data.base.EntityBase;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
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

    @NotEmpty(groups = {BaseInfo.class, LoginGroup.class}, message = "用户名不能为空！")
    @Length(max = 30, message = "用户名不能超过30个字！")
    private String username;

    @NotEmpty(groups = {BaseInfo.class})
    @Pattern(regexp = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$", message = "邮箱格式不正确！")
    private String email;

    @NotEmpty(groups = {BaseInfo.class})
    private String name;

    @NotEmpty(groups = {BaseInfo.class, LoginGroup.class}, message = "密码不能为空！")
    private String password;

    @NotEmpty(groups = {BaseInfo.class})
    private String phone;

    private String salt;

    private byte status;

    private String operator_id;

    private String key;

    private String token;

    private int passwordNumber;//密码猜测次数

    public interface LoginGroup {
    }

    public interface BaseInfo {
    }
}
