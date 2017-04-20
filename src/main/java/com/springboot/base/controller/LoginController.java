package com.springboot.base.controller;

import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.entity.UserInfo;
import com.springboot.base.data.exception.ControllerException;
import com.springboot.base.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录
 * Created by jay on 2017-4-10.
 */
@RestController
@RequestMapping
@Slf4j
public class LoginController {


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, @RequestParam String username,
                        @RequestParam String password) throws Exception {
        String exception = (String) request.getAttribute("shiroLoginFailure");
        System.out.println("exception=" + exception);
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                log.info("UnknownAccountException -- > 账号不存在：");
                throw new ControllerException(ErrorInfo.LOGIN_ERROR);
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                log.info("IncorrectCredentialsException -- > 密码不正确：");
                throw new ControllerException(ErrorInfo.LOGIN_ERROR);
            } else if ("kaptchaValidateFailed".equals(exception)) {
                System.out.println("kaptchaValidateFailed -- > 验证码错误");
                //msg = "kaptchaValidateFailed -- > 验证码错误";
            } else {
                log.error("登录失败！", exception);
                throw new ControllerException(ErrorInfo.LOGIN_EXCEPTION);
            }
        }
        return "success";
    }

    /**
     * 未登录接口
     *
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void login() throws Exception {
        throw new ControllerException(ErrorInfo.NO_LOGIN);
    }

    /**
     * 退出
     *
     * @throws Exception
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout() throws Exception {
        log.info("退出！");
    }
}
