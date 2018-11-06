package com.springboot.base.service.impl;

import com.springboot.base.data.entity.RolePermission;
import com.springboot.base.mapper.RolePermissionMapper;
import com.springboot.base.service.PermissionInfoService;
import com.springboot.base.service.RolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * Created by jay on 2018-1-15.
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    @Inject
    private PermissionInfoService permissionInfoService;

    @Inject
    private RolePermissionMapper rolePermissionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<RolePermission> savePermission(RolePermission rolePermission) throws Exception {
        //检查权限id，并且返回相应权限ids
        Long[] permissionIds = permissionInfoService.checkTreePermission(rolePermission.getPermissionIds());
        RolePermission saveRolePermission;
        List<RolePermission> saves = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            saveRolePermission = new RolePermission();
            saveRolePermission.setPermissionId(permissionId);
            saveRolePermission.setRoleId(rolePermission.getRoleId());
            saves.add(saveRolePermission);
        }
        rolePermissionMapper.remove(rolePermission.getRoleId());
        rolePermissionMapper.saves(saves);
        return saves;
    }
}
