package com.springboot.base.controller.advice.manager;

import com.springboot.base.data.base.Page;
import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.entity.Permission;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.PermissionVO;
import com.springboot.base.service.PermissionInfoService;
import com.springboot.base.util.BindingResutlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * 描述：
 * Created by jay on 2018-1-10.
 */
@RestController
@RequestMapping("/manage/user/menu")
@Slf4j
public class MenuController {

    @Inject
    private PermissionInfoService permissionInfoService;

    /**
     * 菜单查询
     *
     * @param limit
     * @param offset
     * @param searchStr
     * @param available
     * @return
     */
    @GetMapping(value = "/list")
    public Page list(@RequestParam(value = "limit", defaultValue = "10") int limit,
                     @RequestParam(value = "offset", defaultValue = "0") int offset,
                     @RequestParam(value = "searchStr", defaultValue = "-1") String searchStr,
                     @RequestParam(value = "status", defaultValue = "") Boolean available,
                     @RequestParam(value = "orderBy", defaultValue = "") String orderBy,
                     @RequestParam(value = "desc") boolean desc) {
        return permissionInfoService.listPage(limit, offset, searchStr, available, orderBy, desc);
    }

    /**
     * 添加菜单
     * @param permission
     * @return
     */
    @PostMapping(value = "/save")
    public Permission save(@RequestBody @Validated(Permission.BaseInfo.class) Permission permission, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("添加验证信息{}", BindingResutlUtils.getMessage(bindingResult));
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        return permissionInfoService.save(permission);
    }

    /**
     * 修改菜单
     * @param permission
     * @return
     */
    @PostMapping(value = "/update")
    public Permission update(@RequestBody @Validated(Permission.Update.class) Permission permission, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("添加验证信息{}", BindingResutlUtils.getMessage(bindingResult));
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        return permissionInfoService.update(permission);
    }

    /**
     * 删除
     * @param permissionIds
     * @throws Exception 异常
     */
    @PostMapping(value = "/remove")
    public void remove(@RequestParam Long[] permissionIds) throws Exception {
        if (permissionIds == null || permissionIds.length <= 0) {
            log.info("productIds为空！");
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        permissionInfoService.remove(permissionIds);
    }

    /**
     * 菜单详情
     * @param menuId
     * @return
     */
    @GetMapping(value = "/detail")
    public PermissionVO detail(@RequestParam(value = "menuId") Long menuId) {
        return permissionInfoService.getDetail(menuId);
    }

    /**
     * 按钮详情
     * @param buttonId
     * @return
     */
    @GetMapping(value = "/buttonDetail")
    public PermissionVO buttonDetail(@RequestParam(value = "buttonId") Long buttonId) {
        return permissionInfoService.buttonDetail(buttonId);
    }

    /**
     * 按钮保存
     * @param permission
     * @param bindingResult
     * @throws Exception
     */

    @PostMapping(value = "/saveButton")
    public Permission saveButton(@RequestBody @Validated(Permission.SaveButton.class) Permission permission, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("添加验证信息{}", BindingResutlUtils.getMessage(bindingResult));
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        return permissionInfoService.saveButton(permission);
    }

    /**
     * 按钮修改
     * @param permission
     * @param bindingResult
     * @throws Exception
     */

    @PostMapping(value = "/updateButton")
    public Permission updateButton(@RequestBody @Validated(Permission.UpdateButton.class) Permission permission, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("添加验证信息{}", BindingResutlUtils.getMessage(bindingResult));
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        return permissionInfoService.updateButton(permission);
    }

    /**
     * 删除按钮
     * @param permissionId
     * @throws Exception 异常
     */
    // TODO: 2018-1-11 未删除角色相对应的按钮 code应该重新排一下
    @PostMapping(value = "/removeButton")
    public void removeButton(@RequestParam Long permissionId) throws Exception {
        permissionInfoService.removeButton(permissionId);
    }

}