package com.springboot.base.controller.advice;

import com.springboot.base.data.base.Page;
import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.enmus.UserStatus;
import com.springboot.base.data.entity.UserInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.UserVO;
import com.springboot.base.service.UserInfoService;
import com.springboot.base.util.BindingResutlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 描述：用户管理
 * Created by jay on 2017-9-29.
 */
@RestController
@RequestMapping("user/userManager")
@Slf4j
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Page list(@RequestParam(value = "limit", defaultValue = "-1") int limit,
                     @RequestParam(value = "offset", defaultValue = "-1") int offset,
                     @RequestParam(value = "searchStr", defaultValue = "-1") String searchStr,
                     @RequestParam(value = "status", defaultValue = "-1") int status) {
        return userInfoService.listPage(limit, offset, searchStr, status);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public UserVO list(@RequestParam(value = "userId") Long userId) {
        return userInfoService.getDetail(userId);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(@RequestBody @Validated(UserInfo.Modify.class) UserInfo userInfo, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("添加验证信息{}", BindingResutlUtils.getMessage(bindingResult));
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        userInfoService.update(userInfo);
    }

    @RequestMapping(value = "/updateFreeze", method = RequestMethod.POST)
    public void updateFreeze(@RequestParam Long userId) throws Exception {
        if (userId == null) {
            log.info("userId为空！");
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        userInfoService.updateStatus(userId, UserStatus.FREEZE);
    }

    @RequestMapping(value = "/updateDefault", method = RequestMethod.POST)
    public void updateDefault(@RequestParam Long userId) throws Exception {
        if (userId == null) {
            log.info("userId为空");
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        userInfoService.updateStatus(userId, UserStatus.DEFAULT);
    }

}
