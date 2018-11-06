package com.springboot.base.aspect;

import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.util.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 日志切面
 * Created by jay on 2016-10-27.
 */
@Aspect
@Configuration
@Slf4j
public class LoginAspect {

    private long startTime = 0; // 开始时间



    @Pointcut("execution (* com.springboot.base.controller..*.*(..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    private void aspectMethod() {

    }

    @Pointcut("execution ( * com.springboot.base.controller..*.list*(..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    private void aspectListMethod() {

    }

    @Pointcut("execution ( * com.springboot.base.controller..*.get*(..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    private void aspectGetMethod() {

    }

    @Before(value = "aspectMethod()")
    public void before(JoinPoint point) {
        startTime = System.currentTimeMillis();   //获取开始时间
        CodeSignature signature = (CodeSignature) point.getSignature();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getName();
        String argsStr = Arrays.toString(point.getArgs());
        //校验参数
        ValidationUtils.checkValidated(signature.getParameterTypes(), point.getArgs());
        //LOG.info(MarkerFactory.getMarker("USER_MARKER"), "商户id: {} url：{}  参数为：{}", valueHolder.getMerchIdHolder(), className + "." + methodName, argsStr);
        log.info("日志【请求】方法为:{}  参数为：{}", className + "." + methodName, argsStr);
    }

    @AfterReturning(value = "aspectMethod()", returning = "returnValue")
    public void after(JoinPoint point, Object returnValue) {
        Signature signature = point.getSignature();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getName();
        //LOG.info(MarkerFactory.getMarker("USER_MARKER"), "商户id: {} url: {} 返回值为： {}", valueHolder.getMerchIdHolder(), className + "." + methodName, returnValue);
        log.info("日志【返回】方法为:{}  返回值为：{}  【共耗时-{}-毫秒】 ", className + "." + methodName, returnValue, System.currentTimeMillis() - startTime);
    }

    @AfterReturning(value = "aspectMethod() &&!aspectListMethod() &&!aspectGetMethod()", returning = "returnValue")
    public void afterWriteLog(JoinPoint point, Object returnValue) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        log.info("CLASS_METHOD : " + point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
    }
}