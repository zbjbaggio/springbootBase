package com.springboot.base.constant;

/**
 * 系统常量
 * Created by jay on 2016-10-20.
 */
public interface SystemConstants {

    int USER_TOKEN_TIME_LONG = 30;//用户登录后存入redis的时长30分钟

    int USER_PASSWORD_TIME_LONG = 60;//用户猜密码次数存入redis的时长60分钟

    int FREEZE_NUMBER = 3;//3次猜错冻结

}
