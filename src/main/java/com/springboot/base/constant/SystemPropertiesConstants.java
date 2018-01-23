package com.springboot.base.constant;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * Created by lilong on 2018/1/23.
 */
@Component
@Data
@NoArgsConstructor
public class SystemPropertiesConstants {

    @Value("${manager.login.time}")
    int USER_TOKEN_TIME_LONG = 30;          //用户登录后存入redis的时长30分钟

    @Value("${manager.login.verifiedTime}")
    int USER_PASSWORD_TIME_LONG = 60;       //用户猜密码次数存入redis的时长60分钟，即锁定时间

    @Value("${manager.login.lockNumber}")
    int MANAGER_LOGIN_LOCK_NUMBER = 3;      //n次猜错锁定账号

    @Value("${manager.login.expectNumber}")
    int MANAGER_LOGIN_EXPECT_NUMBER = 1;    //锁定n次后冻结账户
}
