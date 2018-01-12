package com.springboot.base.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 描述：
 * Created by jay on 2018-1-12.
 */
@Data
@AllArgsConstructor
public class PermissionTreeVO {

    private List<TreeVO> treeVOS;

    private List<Long> check;

}
