package com.springboot.base.controller.advice;

import com.springboot.base.data.base.Page;
import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.entity.Role;
import com.springboot.base.data.entity.UserInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.RoleVO;
import com.springboot.base.service.RoleService;
import com.springboot.base.util.BindingResutlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * 描述：角色管理控制类
 * Created by jay on 2017-10-12.
 */
@RestController
@RequestMapping("user/roleManager")
@Slf4j
public class RoleController {

    @Inject
    private RoleService roleService;

    /**
     * 角色查询
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
                     @RequestParam(value = "status") Boolean status,
                     @RequestParam(value = "orderBy", defaultValue = "") String orderBy,
                     @RequestParam(value = "desc") boolean desc) {
        return roleService.listPage(limit, offset, searchStr, status, orderBy, desc);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Long save(@RequestBody @Validated(Role.Modify.class) Role role, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("添加验证信息{}", BindingResutlUtils.getMessage(bindingResult));
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        return roleService.save(role);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public RoleVO detail(@RequestParam(value = "roleId") Long roleId) throws Exception {
        return roleService.getDetail(roleId);
    }

}
