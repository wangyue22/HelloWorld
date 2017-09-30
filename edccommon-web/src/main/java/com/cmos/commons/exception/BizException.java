package com.cmos.commons.exception;

import org.springframework.http.HttpStatus;

/**
 * 业务异常
 */
public class BizException extends BaseException {
	private static final long serialVersionUID = -5003177555907180358L;

	public BizException(int code, String message) {
        super(code, "{\"status\":"+HttpStatus.INTERNAL_SERVER_ERROR.value()+",\"message\":\""+message+"\",\"code\":"+code+"}");
    }
}
