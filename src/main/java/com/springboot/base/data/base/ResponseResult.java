package com.springboot.base.data.base;

import com.springboot.base.data.enmus.ErrorInfo;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

public class ResponseResult implements Serializable {

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public static ResponseResult ok() {
        return ok(null);
    }

    public static ResponseResult ok(Object data) {
        return new ResponseResult(ErrorInfo.SUCCESS.getValue(), ErrorInfo.SUCCESS.getName(), data);
    }

    public static ResponseResult build(ErrorInfo errorInfo) {
        return new ResponseResult(errorInfo.getValue(), errorInfo.getName(), null);
    }
    public static ResponseResult build(int errCode, String msg) {
        return new ResponseResult(errCode, msg, null);
    }

    public static ResponseResult buildForValidated() { return build(ErrorInfo.PARAMS_ERROR); }

    public ResponseResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}