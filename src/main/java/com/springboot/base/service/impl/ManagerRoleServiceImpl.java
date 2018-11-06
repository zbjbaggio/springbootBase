package com.springboot.base.service.impl;

import com.springboot.base.data.entity.ManagerRole;
import com.springboot.base.mapper.ManagerRoleMapper;
import com.springboot.base.service.ManagerRoleService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * Created by jay on 2018-1-26.
 */
@Service
public class ManagerRoleServiceImpl implements ManagerRoleService {

    @Inject
    private ManagerRoleMapper managerRoleMapper;

    @Override
    public void saves(ManagerRole managerRole) {
        long[] roleIds = managerRole.getRoleIds();
        long[] managerIds = managerRole.getManagerIds();
        if (roleIds != null && roleIds.length > 0) {
            managerRoleMapper.removeByManagerId(managerRole.getManagerId());
            List<ManagerRole> saves = new ArrayList<>(roleIds.length);
            ManagerRole save;
            for (long roleId : roleIds) {
                save = new ManagerRole();
                save.setManagerId(managerRole.getManagerId());
                save.setRoleId(roleId);
                saves.add(save);
            }
            managerRoleMapper.saves(saves);
        } else if (managerIds != null && managerIds.length > 0) {
            managerRoleMapper.removeByRoleId(managerRole.getRoleId());
            List<ManagerRole> saves = new ArrayList<>(managerIds.length);
            ManagerRole save;
            for (long managerId : managerIds) {
                save = new ManagerRole();
                save.setRoleId(managerRole.getRoleId());
                save.setManagerId(managerId);
                saves.add(save);
            }
            managerRoleMapper.saves(saves);
        }
    }

}
