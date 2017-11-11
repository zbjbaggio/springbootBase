package com.springboot.base.controller.advice.manager;

import com.springboot.base.data.base.Page;
import com.springboot.base.data.dto.PasswordDTO;
import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.enmus.UserStatus;
import com.springboot.base.data.entity.UserInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.UserVO;
import com.springboot.base.service.UserInfoService;
import com.springboot.base.util.BindingResutlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * 描述：用户管理
 * Created by jay on 2017-9-29.
 */
@RestController
@RequestMapping("user/userManager")
@Slf4j
public class UserInfoController {

    @Inject
    private UserInfoService userInfoService;

    /**
     * 用户查询
     * @param limit
     * @param offset
     * @param searchStr
     * @param status
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Page list(@RequestParam(value = "limit", defaultValue = "10") int limit,
                     @RequestParam(value = "offset", defaultValue = "0") int offset,
                     @RequestParam(value = "searchStr", defaultValue = "-1") String searchStr,
                     @RequestParam(value = "status", defaultValue = "-1") int status,
                     @RequestParam(value = "orderBy", defaultValue = "") String orderBy,
                     @RequestParam(value = "desc") boolean desc) {
        return userInfoService.listPage(limit, offset, searchStr, status, orderBy, desc);
    }

    /**
     * 添加用户
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void add(@RequestBody @Validated(UserInfo.BaseInfo.class) UserInfo userInfo, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("添加验证信息{}", BindingResutlUtils.getMessage(bindingResult));
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        userInfoService.save(userInfo);
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public void updatePassword(@RequestBody @Validated(PasswordDTO.BaseInfo.class) PasswordDTO passwordDTO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("添加验证信息{}", BindingResutlUtils.getMessage(bindingResult));
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        userInfoService.updatePassword(passwordDTO);
    }

    /**
     * 用户详情
     * @param userId
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public UserVO detail(@RequestParam(value = "userId") Long userId) {
        return userInfoService.getDetail(userId);
    }

    /**
     * 用户修改
     * @param userInfo
     * @param bindingResult
     * @throws Exception
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(@RequestBody @Validated(UserInfo.Modify.class) UserInfo userInfo, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("添加验证信息{}", BindingResutlUtils.getMessage(bindingResult));
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        userInfoService.update(userInfo);
    }

    /**
     * 用户冻结
     * @param userId
     * @throws Exception
     */
    @RequestMapping(value = "/updateFreeze", method = RequestMethod.POST)
    public void updateFreeze(@RequestParam Long userId) throws Exception {
        if (userId == null) {
            log.info("userId为空！");
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        userInfoService.updateStatus(userId, UserStatus.FREEZE);
    }

    /**
     * 用户删除
     * @param userIds
     * @throws Exception
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(@RequestParam Long[] userIds) throws Exception {
        if (userIds == null || userIds.length <= 0) {
            log.info("userIds为空！");
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        userInfoService.delete(userIds);
    }

}
