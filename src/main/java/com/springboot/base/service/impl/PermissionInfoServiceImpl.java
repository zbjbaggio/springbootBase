package com.springboot.base.service.impl;

import com.springboot.base.data.dto.MenuAndButtonDTO;
import com.springboot.base.data.vo.PermissionVO;
import com.springboot.base.mapper.PermissionInfoMapper;
import com.springboot.base.service.PermissionInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 描述：
 * Created by jay on 2017-12-13.
 */
@Service
public class PermissionInfoServiceImpl implements PermissionInfoService {

    @Value("${junjie.path}")
    private String path;

    @Inject
    private PermissionInfoMapper permissionInfoMapper;

    @Override
    public MenuAndButtonDTO getMenu(long managerId){
        //查询权限
        List<PermissionVO> permissionVOS = permissionInfoMapper.listByManagerId(managerId);
        Set<String> permissionSet = new HashSet<>();
        List<PermissionVO> menuList = new ArrayList<>();
        Set<String> buttonSet = new HashSet<>();
        for (PermissionVO permissionVO : permissionVOS) {
            permissionSet.add("/" + path + permissionVO.getBe_url());
            // 一级菜单没有parentId
            if ("-1".equals(permissionVO.getParent_id() + "")) {
                menuList.add(permissionVO);
            } else if ("menu".equals(permissionVO.getResource_type())) {
                setChild(menuList.get(menuList.size() - 1), permissionVO);
            } else if ("button".equals(permissionVO.getResource_type())) {
                buttonSet.add(permissionVO.getBe_url());
            }
        }
        MenuAndButtonDTO menuAndButtonDTO = new MenuAndButtonDTO();
        menuAndButtonDTO.setMenuList(menuList);
        menuAndButtonDTO.setPermissionSet(permissionSet);
        menuAndButtonDTO.setButtonSet(buttonSet);
        return menuAndButtonDTO;
    }

    private void setChild(PermissionVO node, PermissionVO permissionVO) {
        List<PermissionVO> child = node.getChild();
        if (child == null) {
            child = new ArrayList<>();
            child.add(permissionVO);
        } else if ((child.get(child.size()-1).getParent_id()+"").equals(permissionVO.getParent_id() + "")) {
            child.add(permissionVO);
        } else {
            setChild(child.get(child.size()-1), permissionVO);
        }
        node.setChild(child);
    }
}
