package com.springboot.base.controller.advice;

import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.entity.UserInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.UserVO;
import com.springboot.base.service.UserInfoService;
import com.springboot.base.util.BindingResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * 登录
 * Created by jay on 2017-4-10.
 */
@RestController
@RequestMapping
@Slf4j
public class LoginController {

    @Inject
    private UserInfoService userInfoService;

    /**
     * 登录接口
     *
     * @param userInfo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserVO login(@RequestBody @Validated(UserInfo.LoginGroup.class) UserInfo userInfo, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("参数错误！{}", BindingResultUtils.getErrorMessage(bindingResult.getAllErrors()));
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        UserVO userVO = userInfoService.login(userInfo);
        if (userVO == null) {
            throw new PrivateException(ErrorInfo.LOGIN_ERROR);
        }
        return userVO;
    }
}
