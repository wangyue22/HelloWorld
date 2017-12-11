package com.cmos.edccommon.utils.enums;


/**
 * 返回客户端信息枚举
 */
public enum   ReturnInfoEnums {

	PROCESS_SUCCESS("0000","处理成功"),
    PROCESS_FAILED("2999","处理失败"),
    PROCESS_ERROR("9999","系统异常"),
    PROCESS_INPARAM_ERROR("2999","入参校验不通过"),
    
    PICCOMPARE_GZT_DOWN_FAILED("2998","国政通图片下载异常，请重新下载国政通图片"), //国政通特殊返回码（*重要*）
	PICCOMPARE_PICR_DOWN_FAILED("2999","人像照片下载异常"),
	PICCOMPARE_PICR_DEC_FAILED("2999","人像照片解密异常"),
	PICCOMPARE_PICT_DOWN_FAILED("2999","标准照片下载异常"),
	PICCOMPARE_PICT_DEC_FAILED("2999","标准照片解密异常"),
	PICCOMPARE_FAILED("0000","处理成功，但人像比分不通过"),
    FLOW_PROCESS_FAILED("2997", "系统繁忙，请稍后重试"),
	FACELIVE_PICR_DOWN_FAILED("2999","人像照片下载异常"),;

	




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
