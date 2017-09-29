package com.springboot.base.util;

import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * 描述：
 * Created by jay on 2017-9-28.
 */
public class BindingResultUtils {

    public static String getErrorMessage(List<ObjectError> allErrors) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ObjectError error : allErrors) {
            stringBuilder.append(error.getDefaultMessage()).append(",");
        }
        String message = stringBuilder.toString();
        return message.substring(0, message.length() - 1);
    }
}
