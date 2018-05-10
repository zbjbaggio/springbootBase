package com.springboot.base.interceptor;

import com.alibaba.fastjson.JSON;
import com.springboot.base.data.base.ResponseResult;
import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.service.ManagerInfoService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * session 拦截器
 * Created by jay on 16-3-19.
 */
@Component
@Log4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Value("${userTimeOut}")
    private Long userTimeOut;

    @Inject
    private ManagerInfoService managerInfoService;

    /**
     * 登录认证
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (true || "OPTIONS".equals(request.getMethod())) {
            return true;
        }
        try {
            String token = request.getHeader("token");
            String key = request.getHeader("key");
            boolean isSuccess = managerInfoService.checkToken(token, key, request.getRequestURI());
            if (!isSuccess) {
                getFail(response);
            }
            return isSuccess;
        } catch (Exception e) {
            log.error("登录验证异常！{}", e);
            response.setStatus(500);
            return false;
        }
    }

    // 设置返回的失败信息
    private void getFail(HttpServletResponse response) {
        //将实体对象转换为JSON Object转换
        String json = JSON.toJSONString(ResponseResult.build(ErrorInfo.NO_AUTHORITY));
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

}