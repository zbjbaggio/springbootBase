package com.springboot.base.controlleradvice;

import com.springboot.base.data.exception.ControllerException;
import com.springboot.base.data.base.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 异常处理
 * Created by jay on 2016-10-25.
 */
@ControllerAdvice(basePackages = "com.springboot.base.controller")
public class ExceptionControllerAdvice implements ResponseBodyAdvice<Object> {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return ResponseResult.ok(body);
    }

    @ExceptionHandler()
    @ResponseBody
    public ResponseResult handler(ControllerException e) {
        return ResponseResult.build(e.errCode, e.msg);
    }

    @ExceptionHandler()
    @ResponseBody
    public void handler(Exception e, ServerHttpResponse response) {
        LOG.error("系统异常！", e);
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
