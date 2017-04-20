package com.springboot.base.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页
 * Created by jay on 2017-4-12.
 */
@RestController
@RequestMapping("/user/index")
@Log4j
public class IndexController {

    @RequestMapping(method = RequestMethod.GET)
    public void index() {
        log.info("成功！");
    }
}
