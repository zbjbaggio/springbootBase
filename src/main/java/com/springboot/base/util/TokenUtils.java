package com.springboot.base.util;

import com.springboot.base.data.entity.ManagerInfo;

import java.util.UUID;

/**
 * 描述：token key和token生成方法
 * Created by jay on 2017-9-28.
 */
public class TokenUtils {

    public static String getKey(ManagerInfo managerInfo) throws Exception {
        return Md5Util.MD5Encode(managerInfo.getUsername(), managerInfo.getSalt());
    }

    public static String getToken(ManagerInfo managerInfo) throws Exception {
        UUID uuid = UUID.randomUUID();
        return Md5Util.MD5Encode(uuid.toString(), managerInfo.getSalt());
    }

}
