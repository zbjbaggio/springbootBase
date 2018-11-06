package com.springboot.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 描述：编号工具
 * Created by jay on 2017-11-13.
 */
public class NOUtils {

    private static final String GENERATOR = "10";

    public static String getGeneratorNO() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmm");
        Date date = new Date();
        long time = date.getTime();
        String dateStr = simpleDateFormat.format(date);
        Random random = new Random();
        int randNum = random.nextInt(10000);
        String number = time + randNum + "";
        return GENERATOR + dateStr + number.substring(number.length()-8);
    }

}
