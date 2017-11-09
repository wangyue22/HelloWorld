package com.cmos.edccommon.utils.enums;


/**
 * 返回客户端信息枚举
 */
public enum   ReturnInfoEnums {

    PROCESS_SUCCESS("0000","处理成功"),
    PROCESS_FAILED("2999","处理失败"),
    PROCESS_ERROR("9999","系统异常");




    private String code;
    private String message;
    private ReturnInfoEnums(String code, String message) {
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
