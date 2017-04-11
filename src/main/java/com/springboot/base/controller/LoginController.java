package com.springboot.base.controller;

import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.entity.UserInfo;
import com.springboot.base.data.exception.ControllerException;
import com.springboot.base.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录
 * Created by jay on 2017-4-10.
 */
@RestController
@RequestMapping
@Slf4j
public class LoginController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 登录接口
     * @param userInfo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserInfo login(@RequestBody @Validated(UserInfo.LoginGroup.class) UserInfo userInfo, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("添加验证信息{}", bindingResult);
            throw new ControllerException(ErrorInfo.PARAMS_ERROR);
        }
        userInfo = userInfoService.login(userInfo);
        if (userInfo == null) {
            throw new ControllerException(ErrorInfo.LOGIN_ERROR);
        }
        return userInfo;
    }
}
