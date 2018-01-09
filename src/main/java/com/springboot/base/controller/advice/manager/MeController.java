package com.springboot.base.controller.advice.manager;

import com.springboot.base.data.dto.PasswordDTO;
import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.service.ManagerInfoService;
import com.springboot.base.util.BindingResutlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * 描述：用户本身具有的权限
 * Created by jay on 2017-10-11.
 */
@RestController
@RequestMapping("/manage/user/me")
@Slf4j
public class MeController {

    @Inject
    private ManagerInfoService managerInfoService;

    /**
     * 注销
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/loginOut")
    public void loginOut() throws Exception {
        managerInfoService.loginOut();
    }

    /**
     * 修改密码
     */
    @PostMapping(value = "/updatePassword")
    public void updatePassword(@RequestBody @Validated(PasswordDTO.BaseInfo.class) PasswordDTO passwordDTO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("添加验证信息{}", BindingResutlUtils.getMessage(bindingResult));
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        managerInfoService.updatePassword(passwordDTO);
    }
}
