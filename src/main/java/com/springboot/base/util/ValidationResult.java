package com.springboot.base.util;

import lombok.Data;

import java.util.Map;

/**
 * 功能：hibernate校验工具方法 返回的 结果对象
 */
@Data
public class ValidationResult {

    //校验结果是否有错
    private boolean hasErrors;

    //校验错误信息
    private Map<String,String> errorMsg;

}
