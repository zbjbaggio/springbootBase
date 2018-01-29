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
        managerRoleMapper.remove(managerRole.getManagerId());
        long[] roleIds = managerRole.getRoleIds();
        if (roleIds != null && roleIds.length > 0) {
            List<ManagerRole> saves = new ArrayList<>(roleIds.length);
            ManagerRole save;
            for (long roleId : roleIds) {
                save = new ManagerRole();
                save.setManagerId(managerRole.getManagerId());
                save.setRoleId(roleId);
                saves.add(save);
            }
            managerRoleMapper.saves(saves);
        }
    }

}
