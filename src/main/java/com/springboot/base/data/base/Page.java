package com.springboot.base.data.base;

import lombok.Data;

import java.util.List;

/**
 * 描述：分页对象
 * Created by jay on 2017-9-29.
 */
@Data
public class Page {

    private Long count = 0L;

    private List list;
}
