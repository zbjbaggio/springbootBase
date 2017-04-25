package com.springboot.base.controller;

import com.springboot.base.service.UserInfoService;
import com.springboot.base.util.SerializableUtils;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 * 首页
 * Created by jay on 2017-4-12.
 */
@RestController
@RequestMapping("/user/index")
@Log4j
public class IndexController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(method = RequestMethod.GET)
    public void index() {
        //userInfoService.getUser();
        Serializable sessionId = SecurityUtils.getSubject().getSession().getId();
        Session session = userInfoService.getUserBySessionId(sessionId);
        log.info("成功！");
    }
}
