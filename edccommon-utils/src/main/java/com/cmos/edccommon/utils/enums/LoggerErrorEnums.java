package com.cmos.edccommon.utils.enums;

/**
 * logger.error信息枚举
 */
public enum LoggerErrorEnums {
    PROCESS_ERROR("9999","系统异常");


    private String code;
    private String message;


    private LoggerErrorEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String getCode(){
        return code;
    }
    public String getMessage(){
        return message;
    }
}
