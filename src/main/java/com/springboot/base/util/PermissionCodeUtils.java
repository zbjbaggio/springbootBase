package com.springboot.base.util;

/**
 * 描述：功能code编号工具类
 * Created by jay on 2018-5-11.
 */
public class PermissionCodeUtils {

    //补位字符串
    private static final String CODE_PLACE = "000";

    //位长度
    private static final int PLACE_LONG = 4;

    //默认第一节点
    public static final String CODE_PLACE_FIRST = "0001";

    /**
     * 得到子节点第一个编号
     * @param code
     * @return
     */
    public static String getChildrenPlace(String code) {
        return code + CODE_PLACE_FIRST;
    }

    /**
     * 得到父类节点
     * @param code
     * @return
     */
    public static String getParentPlace(String code) {
        return code.substring(0, code.length() - PLACE_LONG);
    }

    /**
     * 默认加一的方法
     * @param code
     * @return
     */
    public static String add(String code) {
        return add(code, 1);
    }

    public static String add(String code, int number) {
        String tempCode = CODE_PLACE + (Long.parseLong(code.substring(code.length() - PLACE_LONG)) + number);
        return code.substring(0, code.length() - PLACE_LONG) +  tempCode.substring(tempCode.length() - PLACE_LONG);
    }

    public static void main(String[] args) {
        System.out.println(PermissionCodeUtils.add("00110013", 90));
    }


}
