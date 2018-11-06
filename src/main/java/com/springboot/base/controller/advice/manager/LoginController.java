package com.springboot.base.controller.advice.manager;

import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.entity.ManagerInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.ManagerVO;
import com.springboot.base.service.ManagerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * 登录
 * Created by jay on 2017-4-10.
 */
@RestController
@RequestMapping("/manage")
@Slf4j
public class LoginController {

    @Inject
    private ManagerInfoService managerInfoService;

    /**
     * 登录接口
     *
     * @param managerInfo
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/login")
    public ManagerVO login(@RequestBody @Validated(ManagerInfo.LoginGroup.class) ManagerInfo managerInfo, BindingResult bindingResult) throws Exception {
        ManagerVO managerVO = managerInfoService.login(managerInfo);
        if (managerVO == null) {
            throw new PrivateException(ErrorInfo.LOGIN_ERROR);
        }
        return managerVO;
    }
}
