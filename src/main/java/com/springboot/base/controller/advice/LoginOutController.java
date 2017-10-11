package com.springboot.base.controller.advice;

import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：注销
 * Created by jay on 2017-10-11.
 */
@RestController
@RequestMapping
@Slf4j
public class LoginOutController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 注销
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/loginOut", method = RequestMethod.POST)
    public void loginOut() throws Exception {
        userInfoService.loginOut();
    }
}
