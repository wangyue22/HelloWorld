package com.cmos.commons.exception;

public class BaseException extends RuntimeException {
	private static final long serialVersionUID = 3109429998609300853L;
	
	/** 业务执行结果状态码，[注意]值必须>0 */
	private final int code;
	
    public BaseException(int code, String message) {
        super(message);
        this.code = code > 0 ? code : 1;
    }

	public int getCode() {
		return code;
	}
}
