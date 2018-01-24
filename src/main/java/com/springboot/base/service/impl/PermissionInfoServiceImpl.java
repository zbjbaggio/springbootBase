package com.springboot.base.service.impl;

import com.springboot.base.data.base.Page;
import com.springboot.base.data.dto.MenuAndButtonDTO;
import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.enmus.ResourceType;
import com.springboot.base.data.entity.Permission;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.PermissionVO;
import com.springboot.base.data.vo.PermissionTreeVO;
import com.springboot.base.data.vo.TreeVO;
import com.springboot.base.mapper.PermissionInfoMapper;
import com.springboot.base.service.PermissionInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
@Slf4j
public class PermissionInfoServiceImpl implements PermissionInfoService {

    @Value("${server.context-path}")
    private String path;

    @Inject
    private PermissionInfoMapper permissionInfoMapper;

    @Override
    public MenuAndButtonDTO getMenu(long managerId) {
        //查询权限
        List<PermissionVO> permissionVOS = permissionInfoMapper.listByManagerId(managerId);
        Set<String> permissionSet = new HashSet<>();
        List<PermissionVO> menuList = new ArrayList<>();
        Set<String> buttonSet = new HashSet<>();
        for (PermissionVO permissionVO : permissionVOS) {
            permissionSet.add(path + permissionVO.getBe_url());
            // 一级菜单没有parentId
            if ("0".equals(permissionVO.getParent_id() + "")) {
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

    @Override
    public Page listPage(int limit, int offset, String searchStr, Boolean available, String orderBy, boolean desc) {
        if (!"-1".equals(searchStr)) {
            searchStr = "%" + searchStr + "%";
        }
        String descStr = "";
        if (!StringUtils.isEmpty(orderBy) && desc) {
            descStr = "desc";
        }
        Page page = new Page();
        Long count = permissionInfoMapper.count(searchStr, available);
        if (count != 0) {
            page.setCount(count);
            page.setList(permissionInfoMapper.listPage(limit, offset, searchStr, available, orderBy, descStr));
        }
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long[] permissionIds) throws Exception {
        int i = permissionInfoMapper.remove(permissionIds);
        if (i != permissionIds.length) {
            throw new PrivateException(ErrorInfo.DELETE_ERROR);
        }
        permissionInfoMapper.removeByParentIds(permissionIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Permission save(Permission permission) throws Exception {
        permission.setResourceType(String.valueOf(ResourceType.menu));
        int count;
        String code = permission.getCode();
        Long id = permission.getId();
        //校验code唯一
        long codeCount = permissionInfoMapper.countByCodeAndId(code, id == null ? -1 : id);
        if (codeCount != 0) {
            log.info("编码重复,菜单对象；{}", permission);
            throw new PrivateException(ErrorInfo.NO_SAME);
        }
        //根据code查找父节点
        Long permissionId = 0L;
        if (code.length() > 4) {
            permissionId = permissionInfoMapper.getIdByCode(code.substring(0, code.length() - 4), ResourceType.menu);
            if (permissionId == null) {
                log.info("编码未找到父类id,菜单对象；{}", permission);
                throw new PrivateException(ErrorInfo.NO_ERROR);
            }
        }
        permission.setParentId(permissionId);
        if (permission.getId() == null) {
            count = permissionInfoMapper.save(permission);
        } else {
            count = permissionInfoMapper.update(permission);
        }
        if (count != 1) {
            throw new PrivateException(ErrorInfo.SAVE_ERROR);
        }
        return permission;
    }

    @Override
    public PermissionVO getDetail(Long menuId) {
        PermissionVO permissionVO = permissionInfoMapper.getDetailById(menuId, ResourceType.menu);
        long count = permissionInfoMapper.countByParentId(permissionVO.getId(), ResourceType.menu);
        if (count > 0) {
            permissionVO.setHasButton(false);
        } else {
            permissionVO.setHasButton(true);
            permissionVO.setButton(permissionInfoMapper.listByParentId(menuId, ResourceType.button));
        }
        return permissionVO;
    }

    @Override
    public PermissionVO buttonDetail(Long buttonId) {
        return permissionInfoMapper.getDetailById(buttonId, ResourceType.button);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Permission saveButton(Permission permission) throws Exception {
        //校验，有子菜单的不允许有按钮
        long countLong = permissionInfoMapper.countByParentId(permission.getId(), ResourceType.menu);
        if (countLong > 0) {
            log.info("有子菜单的不允许有按钮,菜单对象；{}", permission);
            throw new PrivateException(ErrorInfo.BUTTON_ERROR);
        }
        permission.setResourceType(String.valueOf(ResourceType.button));
        //根据parentId计算code
        String code = permissionInfoMapper.getMaxCodeByParentId(permission.getParentId(), ResourceType.button);
        if (code == null) {
            PermissionVO parentPermissionVO = permissionInfoMapper.getDetailById(permission.getParentId(), ResourceType.menu);
            code = parentPermissionVO.getCode() + "0001";
        } else {
            String tempCode = "000" + (Long.parseLong(code.substring(code.length() - 4)) + 1);
            code = code.substring(0, code.length() - 4) +  tempCode.substring(tempCode.length() - 4);
        }
        permission.setCode(code);
        int count;
        if (permission.getId() == null) {
            count = permissionInfoMapper.save(permission);
        } else {
            count = permissionInfoMapper.update(permission);
        }
        if (count != 1) {
            throw new PrivateException(ErrorInfo.SAVE_ERROR);
        }
        return permission;
    }

    public static void main(String[] args) {
        String code = "00020001";
        String tempCode = "000" + (Long.parseLong(code.substring(code.length() - 4)) + 1);
        code = code.substring(0, code.length() - 4) +  tempCode.substring(tempCode.length() - 4);
        System.out.println(code);
    }

    @Override
    public void removeButton(Long permissionId) throws Exception {
        int i = permissionInfoMapper.deleteByType(permissionId, ResourceType.button);
        if (i != 1) {
            throw new PrivateException(ErrorInfo.DELETE_ERROR);
        }
    }

    @Override
    public PermissionTreeVO listPermissionDetail(Long roleId) {
        List<TreeVO> roleTreeVOS = permissionInfoMapper.listPermissionDetail(roleId);
        List<TreeVO> treeVOS = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        for (TreeVO treeVO : roleTreeVOS) {
            //角色没有的并且没有父节点的id
            if (treeVO.getRoleId() != null && treeVO.getParentId() == null) {
                ids.add(treeVO.getId());
            }
            if ("0".equals(treeVO.getParent_id() + "")) {
                treeVOS.add(treeVO);
            } else {
                if (treeVO.getResource_type().equals(String.valueOf(ResourceType.button))) {
                    treeVO.setLabel("按钮-" + treeVO.getLabel());
                }
                setChild(treeVOS.get(treeVOS.size() - 1), treeVO);
            }
        }
        return new PermissionTreeVO(treeVOS, ids);
    }

    @Override
    public Long[] checkTreePermission(List<Long> permissionIds) throws Exception {
        List<PermissionVO> permissions = permissionInfoMapper.listByIds(permissionIds);
        if (permissions == null || permissions.size() != permissionIds.size()) {
            log.info("权限ids查询条数对不上！permissionIds：{}， permissions：{} ", permissionIds, permissions);
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        Set<Long> parentIds;
        //已经查过的父类Id
        Set<Long> hasParentIds = new HashSet<>(permissionIds);
        List<PermissionVO> permissionVOS = permissions;
        //查找父类节点只到找不到为止
        do {
            parentIds = new HashSet<>();
            for (PermissionVO permissionVO : permissionVOS) {
                if (!permissionVO.getParent_id().equals(0L) && !hasParentIds.contains(permissionVO.getParent_id())) {
                    parentIds.add(permissionVO.getParent_id());
                }
            }
            hasParentIds.addAll(parentIds);
            if (parentIds.size() > 0) {
                permissionVOS = permissionInfoMapper.listByIds(new ArrayList<>(parentIds));
            }
        } while (parentIds.size() > 0);
        return hasParentIds.toArray(new Long[hasParentIds.size()]);
    }

    private void setChild(PermissionVO parentPermission, PermissionVO childrenPermissionVO) {
        List<PermissionVO> child = parentPermission.getChildren();
        if (child == null) {
            child = new ArrayList<>();
            child.add(childrenPermissionVO);
        } else if ((child.get(child.size() - 1).getParent_id() + "").equals(childrenPermissionVO.getParent_id() + "")) {
            child.add(childrenPermissionVO);
        } else {
            setChild(child.get(child.size() - 1), childrenPermissionVO);
        }
        parentPermission.setChildren(child);
    }
}
