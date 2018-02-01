package com.springboot.base.data.vo;

import lombok.Data;
import lombok.ToString;

/**
 * 描述：
 * Created by jay on 2018-1-12.
 */
@Data
@ToString(callSuper = true)
public class TreeVO extends PermissionVO {

    private String label;

    private Long roleId;

   private Long grandParentId;


}
