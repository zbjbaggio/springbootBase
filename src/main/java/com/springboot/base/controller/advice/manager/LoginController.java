package com.springboot.base.controller.advice.manager;

import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.entity.ManagerInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.ManagerVO;
import com.springboot.base.service.ManagerInfoService;
import com.springboot.base.util.BindingResultUtils;
import com.springboot.base.util.IpVerifyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

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
    public ManagerVO login(@RequestBody @Validated(ManagerInfo.LoginGroup.class) ManagerInfo managerInfo, BindingResult bindingResult, HttpServletRequest request) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("参数错误！{}", BindingResultUtils.getErrorMessage(bindingResult.getAllErrors()));
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        ManagerVO managerVO = managerInfoService.login(managerInfo, IpVerifyUtil.getIp(request));
        if (managerVO == null) {
            throw new PrivateException(ErrorInfo.LOGIN_ERROR);
        }
        return managerVO;
    }
}
