package com.springboot.base.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户
 * Created by jay on 2017-4-12.
 */
@RestController
@RequestMapping("/user/")
@Log4j
public class UserController {

    @RequestMapping("info")
    public String info(long userId) {
        return "/user/info";
    }

}
