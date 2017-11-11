package com.springboot.base.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * 描述：错误校验工具
 * Created by jay on 2017-10-9.
 */
public class BindingResutlUtils {

    public static String getMessage(BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if (allErrors != null && allErrors.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (ObjectError error : allErrors) {
                stringBuilder.append(error.getDefaultMessage()).append("\n");
            }
            String s = stringBuilder.toString();
            return s.substring(0, s.length() - 1);
        }
        return null;
    }
}
