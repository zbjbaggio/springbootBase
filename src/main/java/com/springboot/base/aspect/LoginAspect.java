package com.springboot.base.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.context.annotation.Configuration;

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

    @Pointcut("execution (* com.springboot.base.controller..*.*(..))")
    private void aspectMethod() {

    }

    @Before(value = "aspectMethod()")
    public void before(JoinPoint point) throws Exception {
        startTime = System.currentTimeMillis();   //获取开始时间
        CodeSignature signature = (CodeSignature) point.getSignature();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getName();
        String argsStr = Arrays.toString(point.getArgs());
        //LOG.info(MarkerFactory.getMarker("USER_MARKER"), "商户id: {} url：{}  参数为：{}", valueHolder.getMerchIdHolder(), className + "." + methodName, argsStr);
        log.info("日志【请求】－－－－－－－－－－－方法为:{}  参数为：{}", className + "." + methodName, argsStr);
    }

    @AfterReturning(value = "aspectMethod()", returning = "returnValue")
    public void after(JoinPoint point, Object returnValue) {
        Signature signature = point.getSignature();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getName();
        //LOG.info(MarkerFactory.getMarker("USER_MARKER"), "商户id: {} url: {} 返回值为： {}", valueHolder.getMerchIdHolder(), className + "." + methodName, returnValue);
        log.info("日志【返回】－－－－－－－－－－－方法为:{}     返回值为：{}  【共耗时-{}-毫秒】 ", className + "." + methodName, returnValue, System.currentTimeMillis() - startTime);
    }
}
