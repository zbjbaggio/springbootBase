package com.springboot.base.data.exception;

/**
 * controller 异常
 * Created by jay on 2016-10-25.
 */
public class ControllerException extends Exception {

    public int errCode;

    public String msg;

    public ControllerException(ErrorInfo errorInfo) {
        this.errCode = errorInfo.getValue();
        this.msg = errorInfo.getName();
    }
}