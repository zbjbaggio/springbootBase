package com.springboot.base.data.dto;

import com.springboot.base.data.vo.PermissionVO;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * 描述：菜单和按钮集合
 * Created by jay on 2017-12-14.
 */
@Data
public class MenuAndButtonDTO {

    private Set<String> permissionSet;

    private List<PermissionVO> menuList;
}
