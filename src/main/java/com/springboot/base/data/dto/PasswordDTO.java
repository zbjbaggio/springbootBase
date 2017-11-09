package com.springboot.base.data.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 描述：密码
 * Created by jay on 2017-11-9.
 */
@Data
public class PasswordDTO {

    @NotEmpty(groups = {PasswordDTO.BaseInfo.class}, message = "旧密码不能为空！")
    private String oldPassword;

    @NotEmpty(groups = {PasswordDTO.BaseInfo.class}, message = "新密码不能为空！")
    private String newPassword;

    public interface BaseInfo {
    }
}
