package com.springboot.base.util;

/**
 * 错误代码编号
 * Created by jay on 2017-1-10.
 */
public interface ErrorCode {

    int LOGIN_ERROR_CODE = 50001;

    String LOGIN_ERROR_MESSAGE = "用户名或密码错误！";

    int PARAMS_ERROR_CODE = 5002;

    String PARAMS_ERROR_MESSAGE = "参数错误错误！";

    int LOGIN_AGAIN_ERROR_CODE = 5003;

    String LOGIN_AGAIN_ERROR_MESSAGE = "重新登录！";

    int EXCEL_EXPORT_ERROR_CODE = 5004;

    String EXCEL_EXPORT_ERROR_MESSAGE = "excel导出失败！";



}
