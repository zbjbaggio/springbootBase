package com.springboot.base.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by lilong on 2018/1/23.
 */
@Component
@Data
@ConfigurationProperties(prefix = "manager.login")
public class ManagerLoginConstants {

    private int time = 20;//用户登录后存入redis的时长30分钟

    private int verifiedTime = 60;//用户猜密码次数存入redis的时长60分钟，即锁定时间

    private int frozenNumber = 3;//n次猜错冻结账号

    private int lockedNumber = 1;//冻结n次后锁定账户
}
