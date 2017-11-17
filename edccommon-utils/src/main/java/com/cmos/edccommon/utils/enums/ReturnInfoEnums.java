package com.cmos.edccommon.utils.enums;


/**
 * 返回客户端信息枚举
 */
public enum   ReturnInfoEnums {

	PROCESS_SUCCESS("0000","处理成功"),
    PROCESS_FAILED("2999","处理失败"),
    PROCESS_ERROR("9999","系统异常"),
	PICCOMPARE_PICR_DOWN_FAILED("2999","人像照片下载异常"),
	PICCOMPARE_PICR_DEC_FAILED("2999","人像照片解密异常"),
	PICCOMPARE_PICT_DOWN_FAILED("2999","标准照片下载异常"),
	PICCOMPARE_PICT_DEC_FAILED("2999","标准照片解密异常"),
	PICCOMPARE_INPARAM_ERROR("2999","入参校验不通过"),
	PICCOMPARE_FAILED("0000","人像比对不通过");
	




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
