package com.springboot.base.controller.advice.manager;

import com.springboot.base.data.base.Page;
import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.entity.RoleInfo;
import com.springboot.base.data.entity.RolePermission;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.PermissionTreeVO;
import com.springboot.base.data.vo.RoleVO;
import com.springboot.base.service.RolePermissionService;
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
@RequestMapping("/manage/user/role")
@Slf4j
public class RoleController {

    @Inject
    private RoleService roleService;

    @Inject
    private RolePermissionService rolePermissionService;

    /**
     * 角色查询
     * @param limit
     * @param offset
     * @param searchStr
     * @param status
     * @return
     */
    @GetMapping(value = "/list")
    public Page list(@RequestParam(value = "limit", defaultValue = "10") int limit,
                     @RequestParam(value = "offset", defaultValue = "0") int offset,
                     @RequestParam(value = "searchStr", defaultValue = "-1") String searchStr,
                     @RequestParam(value = "status") Boolean status,
                     @RequestParam(value = "orderBy", defaultValue = "") String orderBy,
                     @RequestParam(value = "desc") boolean desc) {
        return roleService.listPage(limit, offset, searchStr, status, orderBy, desc);
    }

    @PostMapping(value = "/save")
    public RoleInfo save(@RequestBody @Validated(RoleInfo.Modify.class) RoleInfo role, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("添加验证信息{}", BindingResutlUtils.getMessage(bindingResult));
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        return roleService.save(role);
    }

    @GetMapping(value = "/getDetail")
    public RoleVO getDetail(@RequestParam(value = "roleId") Long roleId) {
        return roleService.getDetail(roleId);
    }

    /**
     * 删除按钮
     * @param roleIds
     * @throws Exception 异常
     */
    @PostMapping(value = "/remove")
    public void remove(@RequestParam Long[] roleIds) throws Exception {
        roleService.remove(roleIds);
    }

    /**
     * 权限树
     * @param roleId
     * @return
     */
    @GetMapping(value = "/getPermissionDetail")
    public PermissionTreeVO getPermissionDetail(@RequestParam(value = "roleId") Long roleId) {
        return roleService.listPermissionDetail(roleId);
    }

    /**
     * 权限树保存
     * @param rolePermission
     * @return
     */
    @PostMapping(value = "/savePermission")
    public void savePermission(@RequestBody @Validated(RolePermission.BaseInfo.class) RolePermission rolePermission, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("添加验证信息{}", BindingResutlUtils.getMessage(bindingResult));
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        rolePermissionService.savePermission(rolePermission);
    }

}
