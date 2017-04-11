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
 * 注册
 * Created by jay on 2017-4-10.
 */
@RestController
@RequestMapping
@Slf4j
public class RegisterController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 注册
     * @param userInfo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody @Validated(UserInfo.BaseInfo.class) UserInfo userInfo, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("添加验证信息{}", bindingResult);
            throw new ControllerException(ErrorInfo.PARAMS_ERROR);
        }
        //校验用户名称是否重复
        UserInfo user = userInfoService.getUserNoState(userInfo.getUsername());
        if (user != null) {
            throw new ControllerException(ErrorInfo.USER_NAME_SAME);
        }
        userInfo = userInfoService.save(userInfo);
        if (userInfo == null) {
            throw new ControllerException(ErrorInfo.REGISTER_ERROR);
        }
    }

}
