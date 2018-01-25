package com.springboot.base.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * Created by lilong on 2018/1/23.
 */
@ConfigurationProperties(prefix = "manager.login")
public interface SystemPropertiesConstants {

    int time = 30;          //用户登录后存入redis的时长30分钟

    int verifiedTime = 60;       //用户猜密码次数存入redis的时长60分钟，即锁定时间

    int frozenNumber = 3;      //n次猜错冻结账号

    int lockedNumber = 1;    //冻结n次后锁定账户
}
