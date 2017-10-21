package com.cmos.edccommon.beans.crkey;


import com.cmos.common.bean.GenericBean;
import com.cmos.common.domain.UserResult;

/**
 * 统一入参数据类型  
 * 
 */
public class KeyInfoDTO extends GenericBean implements UserResult {

	private static final long serialVersionUID = 6083560313647235763L;

	/**
	 * 请求源编码
	 */
	private String reqstSrcCode;
	
	/**
	 * 密钥类型 
	 * 例如：DES ，AES ，RSA
	 * 不传或为空，则为默认DES秘钥
	 */	
	private String crkeyTypeCd;
	
	/**
	 * 业务类型代码 
	 * 例如：KSJ 写卡时的卡数据秘钥
	 * 不传或为空，则为默认秘钥
	 */	
	private String bizTypeCd;

	public String getReqstSrcCode() {
		return reqstSrcCode;
	}

	public void setReqstSrcCode(String reqstSrcCode) {
		this.reqstSrcCode = reqstSrcCode;
	}

	public String getCrkeyTypeCd() {
		return crkeyTypeCd;
	}

	public void setCrkeyTypeCd(String crkeyTypeCd) {
		this.crkeyTypeCd = crkeyTypeCd;
	}

	public String getBizTypeCd() {
		return bizTypeCd;
	}

	public void setBizTypeCd(String bizTypeCd) {
		this.bizTypeCd = bizTypeCd;
	}
	
	
}
