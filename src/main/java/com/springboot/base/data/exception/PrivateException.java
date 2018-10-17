package com.springboot.base.data.exception;

import com.springboot.base.enmus.ErrorInfo;
import lombok.ToString;

/**
 * controller 异常
 * Created by jay on 2016-10-25.
 */
@ToString
public class PrivateException extends Exception {

    public int errCode;

    public String msg;

    public PrivateException(ErrorInfo errorInfo) {
        this.errCode = errorInfo.getValue();
        this.msg = errorInfo.getName();
    }
}