package com.cmos.commons.exception;

import org.springframework.http.HttpStatus;

public class AuthException extends BaseException {
	private static final long serialVersionUID = 319004793532685775L;
	
	public static final int CODE_ERR_OTHER = 1;
	public static final int CODE_NOT_LOGIN = 2;
	public static final int CODE_ERR_OWNERSHIP = 3;
	
	public AuthException(int code, String message) {
		super(checkCode(code), 
				"{\"status\":"+HttpStatus.UNAUTHORIZED.value()+",\"message\":\""+message+"\",\"code\":"+checkCode(code)+"}");
	}
	
	private static final int checkCode(int code) {
		return (CODE_ERR_OTHER == code
				|| CODE_NOT_LOGIN == code
				|| CODE_ERR_OWNERSHIP == code
				) ? code : CODE_ERR_OTHER;
	}
}
