package com.springboot.base.data.enmus;


/**
 * 错误信息
 * Created by jay on 2017-2-22.
 */
public enum ErrorInfo {
    SUCCESS(200, "ok"),
    PARAMS_ERROR(50001, "参数错误！"),
    LOGIN_ERROR(50002, "用户名或密码错误！"),
    LOGIN_AGAIN(5003, "重新登录！"),
    NO_LOGIN(5004, "未登录！"),
    NO_AUTHORITY(5005, "未权限！"),
    USER_NAME_SAME(5006, "用户名重复！"),
    REGISTER_ERROR(5007, "用户注册失败！" ),
    USER_FREEZE(5008, "该用户被冻结！" );

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
