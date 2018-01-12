package com.springboot.base.data.entity;

import com.springboot.base.data.base.EntityBase;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 权限表
 * Created by jay on 2017-4-7.
 */
@Data
@ToString(callSuper = true)
public class Permission extends EntityBase implements Serializable {

    @NotNull(groups = {Update.class, UpdateButton.class})
    private Long id;

    private boolean available = true;

    @NotEmpty(groups = {BaseInfo.class, Update.class, SaveButton.class, UpdateButton.class})
    @Length(max = 30, message = "菜单名称不能超过30个字！", groups = {BaseInfo.class, Update.class, SaveButton.class, UpdateButton.class})
    private String name;

    @NotEmpty(groups = {BaseInfo.class, Update.class})
    @Length(max = 30, message = "菜单编号不能超过255个字！")
    private String code;

    @NotNull(groups = {SaveButton.class, UpdateButton.class})
    private Long parentId;

    private String parentIds;

    private String permission;

    private String resourceType;

    @NotEmpty(groups = {SaveButton.class, UpdateButton.class})
    @Length(max = 100, message = "后端地址不能超过100个字！", groups = {BaseInfo.class, Update.class, SaveButton.class, UpdateButton.class})
    private String beUrl;

    @Length(max = 100, message = "前端地址不能超过100个字！", groups = {BaseInfo.class, Update.class, SaveButton.class, UpdateButton.class})
    private String feUrl;

    @Length(max = 40, message = "图片地址不能超过40个字！", groups = {BaseInfo.class, Update.class, SaveButton.class, UpdateButton.class})
    private String icon;

    public interface BaseInfo {
    }

    public interface Update {
    }

    public interface SaveButton {
    }

    public interface UpdateButton {
    }
}
