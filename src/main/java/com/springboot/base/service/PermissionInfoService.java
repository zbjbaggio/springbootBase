package com.springboot.base.service;

import com.springboot.base.data.dto.MenuAndButtonDTO;

/**
 * 描述：
 * Created by jay on 2017-12-13.
 */
public interface PermissionInfoService {

    MenuAndButtonDTO getMenu(long managerId);

}
