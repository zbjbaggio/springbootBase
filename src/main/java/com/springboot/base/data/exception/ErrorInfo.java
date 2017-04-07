package com.springboot.base.data.exception;


/**
 * 错误信息
 * Created by jay on 2017-2-22.
 */
public enum ErrorInfo {
    SUCCESS(200, "ok"),
    PARAMS_ERROR(50001, "参数错误错误！"),
    LOGIN_ERROR(50002, "用户名或密码错误！"),
    LOGIN_AGAIN(5003, "重新登录！"),
    NO_LOGIN(5004, "未登录！"),
    NO_AUTHORITY(5005, "未权限！"),
    EXCEL_ERROR(5006, "excel导出失败！"),
    SALE_COUNT_ERROR(5007, "销售数量大于库存数量！" ),
    USER_NAME_SAME(5008, "用户名重复！"),
    CATEGORY_CODE_SAME(5009, "编码重复！"),
    START_DATE_LESS_NOW(5010, "开始日期不能小于今天！");

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
