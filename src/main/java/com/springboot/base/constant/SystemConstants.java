package com.springboot.base.constant;

/**
 * 系统常量
 * Created by jay on 2016-10-20.
 */
public interface SystemConstants {

    int time = 20;//用户登录后存入redis的时长30分钟

    int verifiedTime = 60;//用户猜密码次数存入redis的时长60分钟，即锁定时间

    int frozenNumber = 3;//n次猜错冻结账号

    int lockedNumber = 1;//冻结n次后锁定账户

}
