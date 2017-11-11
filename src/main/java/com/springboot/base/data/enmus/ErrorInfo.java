package com.springboot.base.data.enmus;


/**
 * 错误信息
 * Created by jay on 2017-2-22.
 */
public enum ErrorInfo {
    SUCCESS(200, "ok"),
    ERROR(500, "系统异常"),
    PARAMS_ERROR(50001, "参数错误！"),
    LOGIN_ERROR(50002, "用户名或密码错误！"),
    LOGIN_AGAIN(50003, "重新登录！"),
    NO_LOGIN(50004, "未登录！"),
    NO_AUTHORITY(50005, "未权限！"),
    USER_NAME_SAME(50006, "用户名重复！"),
    REGISTER_ERROR(50007, "用户注册失败！" ),
    USER_FREEZE(50008, "该用户已被冻结，请联系管理员！" ),
    USER_UNACTIVATED(50009, "该用户还未审核通过请耐心等待！"),
    USER_NO_LOGIN(50010, "该用户已冻结请1个小时后再试！"),
    SAVE_ERROR(50011, "保存失败！"),
    STATUS_ERROR(50012, "修改状态失败！"),
    DELETE_ERROR(50013, "用户已冻结！"),
    NAME_SAME(50014, "名称重复！"),
    UPDATE_ERROR(50015, "修改失败！"),
    PASSWORD_ERROR(50016, "密码错误！"),
    NO_SAME(50017, "编号重复！");

    private int value;

    private String name;

    ErrorInfo(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
