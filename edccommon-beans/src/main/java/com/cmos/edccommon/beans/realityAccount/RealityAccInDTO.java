package com.cmos.edccommon.beans.realityAccount;

import java.sql.Timestamp;

import com.cmos.common.bean.GenericBean;

/**
 * 实名账户统一入参
 * @author: xdx
 */
public class RealityAccInDTO extends GenericBean {

    private static final long serialVersionUID = 1L;

	/**
	 * 请求源
	 */
	private String reqstSrcCode;

	/**
	 * 流水号
	 */
	private String swftno;


	public String getSwftno() {
		return swftno;
	}

	public void setSwftno(String swftno) {
		this.swftno = swftno;
	}

	public String getReqstSrcCode() {
		return reqstSrcCode;
	}

	public void setReqstSrcCode(String reqstSrcCode) {
		this.reqstSrcCode = reqstSrcCode;
	}
}