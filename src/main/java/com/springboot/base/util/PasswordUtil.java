package com.springboot.base.util;

import lombok.extern.log4j.Log4j;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 密码混淆工具类
 * Created by jay on 2017-2-22.
 */
@Log4j
public class PasswordUtil {

    private static final String ALGORITHMNAME = "md5";

    private static final int HASHITERATIONS = 2;

    //密码加密算法
    public static String getPassword(String passwordSrc, String salt) {
        SimpleHash hash = new SimpleHash(ALGORITHMNAME, passwordSrc, salt, HASHITERATIONS);
        return hash.toHex();
    }

    public static void main(String[] args) {
        System.out.println(PasswordUtil.getPassword("1234567", "cb0d1259-bd35-4d8f-998d-abe2d0417657zhangbaojun"));
    }
}
