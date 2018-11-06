package com.springboot.base.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 密码混淆工具类
 * Created by jay on 2017-2-22.
 */
@Slf4j
public class PasswordUtil {

    //密码加密算法
    public static String getPassword(String passwordSrc, String salt) throws Exception {
        try {
            return Md5Util.MD5Encode(passwordSrc, salt);
        } catch (Exception e) {
            log.error("密码加密失败！{}", e);
            throw e;
        }
    }
}
