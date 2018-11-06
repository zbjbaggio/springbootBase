package com.springboot.base.service;

import com.springboot.base.data.base.Page;
import com.springboot.base.data.dto.MenuAndButtonDTO;
import com.springboot.base.data.dto.PermissionDTO;
import com.springboot.base.data.entity.Permission;
import com.springboot.base.data.vo.PermissionVO;
import com.springboot.base.data.vo.PermissionTreeVO;
import com.springboot.base.data.vo.TreeVO;

import java.util.List;

/**
 * 描述：
 * Created by jay on 2017-12-13.
 */
public interface PermissionInfoService {

    MenuAndButtonDTO getMenu(long managerId);

    Page listPage(int limit, int offset, String searchStr, Boolean available, String orderBy, boolean desc);

    void remove(Long[] permissionIds) throws Exception;

    PermissionVO getDetail(Long menuId);

    PermissionVO buttonDetail(Long buttonId);

    Permission saveMenu(Permission permission) throws Exception;

    Permission saveButton(Permission permission) throws Exception;

    void removeButton(Long permissionId) throws Exception;

    PermissionTreeVO listPermissionDetail(Long roleId);

    Long[] checkTreePermission(List<Long> permissionIds) throws Exception;

    List<TreeVO> getMenuTreeDetail();

    void updateCode(List<PermissionDTO> permissionDTOList);
}
