package com.springboot.base.util;

import com.springboot.base.data.entity.UserInfo;

import java.util.UUID;

/**
 * 描述：token key和token生成方法
 * Created by jay on 2017-9-28.
 */
public class TokenUtils {

    public static String getKey(UserInfo userInfo) throws Exception {
        return Md5Util.MD5Encode(userInfo.getUsername(), userInfo.getSalt());
    }

    public static String getToken(UserInfo userInfo) throws Exception {
        UUID uuid = UUID.randomUUID();
        return Md5Util.MD5Encode(uuid.toString(), userInfo.getSalt());
    }

}
