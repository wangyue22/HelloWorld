package com.cmos.commons.exception;

import org.springframework.http.HttpStatus;

public class InvalidParamException extends BaseException {

	private static final long serialVersionUID = 2451675038671985497L;
	
//	/** 参数校验失败类型：path或query string中参数异常 */
//	public static final int CODE_PATH_OR_QS_PARAM_ERROR   = 1;
//	
//	/** 参数校验失败类型：请求body内容中参数异常 */
//	public static final int CODE_REQUEST_BODY_PARAM_ERROR = 2;
	
	private final String objName;

	public InvalidParamException(String objName, String message) {
		//super(checkCode(code), message);
        super(1, "{\"status\":"+HttpStatus.BAD_REQUEST.value()+",\"message\":{\""+objName+"\":\""+message+"\"},\"code\":1}");
		this.objName = objName;
    }

	public String getObjName() {
		return objName;
	}
	
//	private static final int checkCode(int code) {
//		return (code == CODE_PATH_OR_QS_PARAM_ERROR || code == CODE_REQUEST_BODY_PARAM_ERROR) 
//				? code : CODE_PATH_OR_QS_PARAM_ERROR;
//	}
}
