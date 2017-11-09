package com.cmos.edccommon.utils.enums;

/**
 * logger.Info提示信息枚举
 */
public enum LoggerInfoEnums {
    /**
     * 命名规范：类型_模块_
     * 类型：
     * PROCESS-系统
     * BUSI-业务
     *
     */
    PROCESS_SUCCESS("0000","处理成功"),
    PROCESS_FAILED("2999","处理失败"),
    PROCESS_ERROR("9999","系统异常");


    private String code;
    private String message;


    private LoggerInfoEnums(String code, String message) {
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
