package com.cmos.edccommon.beans.facelive;

import com.cmos.common.bean.GenericBean;
import com.cmos.common.domain.UserResult;

/**
 * 人像双照比对入参数据类型
 * 
 */
public class FaceLiveInDTO extends GenericBean implements UserResult {

	private static final long serialVersionUID = 4550693735329609512L;

	/**
	 * 请求源
	 */
	private String reqstSrcCode;
	
	/**
	 * 流水号
	 */
	private String swftno;
	
	/**
	 * 业务类型码
	 */
	private String bizTypeCode;
	/**
	 * 人像图片路径
	 */
	private String picRPath;
	/**
	 * 静默活体判定分值
	 */
	private String faceLiveScore;
	
	public String getReqstSrcCode() {
		return reqstSrcCode;
	}
	public void setReqstSrcCode(String reqstSrcCode) {
		this.reqstSrcCode = reqstSrcCode;
	}
	public String getSwftno() {
		return swftno;
	}
	public void setSwftno(String swftno) {
		this.swftno = swftno;
	}
	public String getBizTypeCode() {
		return bizTypeCode;
	}
	public void setBizTypeCode(String bizTypeCode) {
		this.bizTypeCode = bizTypeCode;
	}
	public String getPicRPath() {
		return picRPath;
	}
	public void setPicRPath(String picRPath) {
		this.picRPath = picRPath;
	}
	public String getFaceLiveScore() {
		return faceLiveScore;
	}
	public void setFaceLiveScore(String faceLiveScore) {
		this.faceLiveScore = faceLiveScore;
	}

}
