package com.springboot.base.controller.advice.manager;

import com.springboot.base.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * 描述：注销
 * Created by jay on 2017-10-11.
 */
@RestController
@RequestMapping("user/")
@Slf4j
public class LoginOutController {

    @Inject
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
