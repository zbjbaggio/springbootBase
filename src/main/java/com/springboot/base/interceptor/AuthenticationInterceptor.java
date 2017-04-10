package com.springboot.base.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.base.ResponseResult;
import com.springboot.base.util.ValueHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * session 拦截器
 * Created by jay on 16-3-19.
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Inject
    private ValueHolder valueHolder;

    @Value("${userTimeOut}")
    private Long userTimeOut;

    /**
     * 登录认证
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        try {
            String urlPath = request.getRequestURI();
            String token = request.getHeader("token");
            if (!StringUtils.isEmpty(token)) {
                /*Merch merch = loginService.getMerchByToken(token);
                if (merch != null) {
                    loginService.saveToRedis(merch);
                    valueHolder.setMerchIdHolder(merch.getMerchId());
                    valueHolder.setTokenHolder(token);
                    return true;
                } else {
                    LOG.error("token 对象为空！url:{}  token：{}", urlPath, token);
                    response.getWriter().write(JSONObject.toJSONString(ResponseResult.build(500, "登录超时！")));
                }*/
            } else {
                LOG.error("token为空,未登录！url:{}", urlPath);
                response.getWriter().write(JSONObject.toJSONString(ResponseResult.build(ErrorInfo.NO_LOGIN)));
            }
            return false;
        } catch (Exception e) {
            LOG.error("登录验证异常！{}", e);
            response.setStatus(500);
            return false;
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