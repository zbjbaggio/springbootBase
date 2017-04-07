package com.springboot.base.util;

/**
 * Created by mentongwu on 16-3-28.
 * 
 */
public class DoubleIntTranslateUtil {

    //整数缩小100倍 转为 两位小数
    public static String addDecimal(String str) {
        String sign = "";
        String result = null;
        //处理负号
        if (str.matches("^-.*")) {
            str = str.substring(1);
            sign = "-";
        }
        if ("0".equals(str)) {//为0
            sign = "";
            result = "0";
        } else if (str.length() >=3) {
            result = str.substring(0, str.length() - 2) + "." + str.substring(str.length() - 2);
        } else if (str.length() == 2){
            result = "0."+str;
        } else if (str.length() == 1){
            result = "0.0"+str;
        }
        return sign + result;
    }

    //小数扩大100倍 转为 整数
    public static String subDecimal(String num) {
        String sign = "";
        String result = null;
        //处理负号
        if (num.matches("^-.*")) {
            num = num.substring(1);
            sign = "-";
        }
        int i = num.indexOf(".");
        if ("0".equals(num)) {//为0
            sign = "";
            result = "0";
        } else if (i == -1) {//非小数
            result = num + "00";
        } else if (num.substring(i + 1).length() == 0) {//有个小数点
            result = num.substring(0, i) + "00";
        } else if (num.substring(i + 1).length() == 1) {//1位小数
            result = num.substring(0, i) +  num.substring(i + 1) + "0";
        } else if (num.substring(i + 1).length() == 2) {//两位小数
            result = num.substring(0, i) +  num.substring(i + 1);
        } else {//两位以上小数
            result = num.substring(0, i) + num.substring(i + 1, i + 3);
        }
        return sign + result;
    }

    //字符串数字扩大10倍
    public static String upDecimal(String num) {
        String sign = "";
        String result = null;
        //处理负号
        if (num.matches("^-.*")) {
            num = num.substring(1);
            sign = "-";
        }
        int i = num.indexOf(".");
        if (i == -1) {
            result = num + "0";
        } else if ((i + 2) == num.length()) {
            result = num.substring(0, i) + num.substring(i + 1, i + 2);
        } else {
            result = num.substring(0, i) + num.substring(i + 1, i + 2) + "." + num.substring(i + 2);
        }
        if ("0".equals((result.charAt(0) + "")) && i == 1) {
            result = result.substring(1);
        }
        return sign + result;
    }

    public static Long StringToLongForMoney(String num) {
        int i = num.indexOf(".");
        if (i == -1) {
            return Long.valueOf(num + "00");
        }
        return Long.valueOf(num.substring(0, i) + num.substring(i + 1));
    }

}
