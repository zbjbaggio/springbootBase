package com.springboot.base.controller.advice.manager;

import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.entity.ManagerInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.service.ManagerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * 注册
 * Created by jay on 2017-4-10.
 */
//@RestController
@RequestMapping
@Slf4j
public class RegisterController {

    @Inject
    private ManagerInfoService managerInfoService;

    /**
     * 注册
     * @param managerInfo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody @Validated(ManagerInfo.BaseInfo.class) ManagerInfo managerInfo, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("添加验证信息{}", bindingResult);
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        managerInfo = managerInfoService.save(managerInfo);
        if (managerInfo == null) {
            throw new PrivateException(ErrorInfo.REGISTER_ERROR);
        }
    }

}
