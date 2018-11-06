package com.springboot.base.util;

import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.exception.PrivateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * 功能：hibernate校验工具
 * Created by lilong on 16-7-7.
 */
@Slf4j
public class ValidationUtils {

    public static void checkValidated(Class[] parameterTypes, Object[] args) throws PrivateException {
        for (int i = 0; i < parameterTypes.length; i++) {
            if (BindingResult.class.equals(parameterTypes[i])) {
                List<FieldError> fieldErrors = ((BindingResult) args[i]).getFieldErrors();
                if (fieldErrors != null && fieldErrors.size() > 0) {
                    StringBuffer msg = new StringBuffer();
                    for (FieldError fieldError : fieldErrors) {
                        msg.append(fieldError.getField()).append(fieldError.getDefaultMessage()).append(",");
                    }
                    log.info("参数错误！{}", msg.substring(0, msg.length() -1));
                    throw new PrivateException(ErrorInfo.PARAMS_ERROR);
                }
            }
        }
    }
}
