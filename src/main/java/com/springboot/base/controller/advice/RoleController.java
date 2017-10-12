package com.springboot.base.controller.advice;

import com.springboot.base.data.base.Page;
import com.springboot.base.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
                     @RequestParam(value = "status") Boolean status) {
        return roleService.listPage(limit, offset, searchStr, status);
    }

}
