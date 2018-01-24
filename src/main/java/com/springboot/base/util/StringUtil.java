package com.springboot.base.util;

import java.text.NumberFormat;

/**
 * string 工具类
 * Created by jay on 16-3-4.
 */
public class StringUtil {

    public static String firstCharUpperCase(String fieldName) {
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public static boolean isEmpty(String cs) {
        return cs == null || cs.trim().length() == 0;
    }


    /**
     * @param figures 加几位0
     * @param value   给谁加0
     * @return 格式化好的值
     */

    public static String addZero(int figures, Object value) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMinimumIntegerDigits(figures);
        nf.setMinimumIntegerDigits(figures);
        return nf.format(value);
    }

    /**
     * 字符串特殊拼接
     * @param sign
     * @param contents
     * @return
     */
    public static String concatStringWithSign(String sign, String... contents) {
        String result = contents[0];
        for (int i=1; i<contents.length; i++) {
            result = result.concat(sign).concat(contents[i]);
        }
        //int lastIndex = result.lastIndexOf(sign);
        //result = result.substring(0, lastIndex);
        return result;
    }

}
