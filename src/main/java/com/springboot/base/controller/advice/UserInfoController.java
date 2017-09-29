package com.springboot.base.controller.advice;

import com.springboot.base.data.base.Page;
import com.springboot.base.data.vo.UserVO;
import com.springboot.base.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：用户管理
 * Created by jay on 2017-9-29.
 */
@RestController
@RequestMapping("user/user/manager")
@Slf4j
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Page list(@RequestParam(value = "limit", defaultValue = "-1") int limit,
                     @RequestParam(value = "offset", defaultValue = "-1") int offset,
                     @RequestParam(value = "searchStr", defaultValue = "-1") String searchStr,
                     @RequestParam(value = "state", defaultValue = "-1") int state) {
        return userInfoService.listPage(limit, offset, searchStr, state);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public UserVO list(@RequestParam(value = "userId") Long userId) {
        return userInfoService.getDetail(userId);
    }
}
