package com.cmos.edccommon.beans.piccompare;

import com.cmos.common.bean.GenericBean;
import com.cmos.common.domain.UserResult;


/**
 * 人像比对判定入参数据类型
 * 
 */
public class PicCompareBase64InDTO extends GenericBean implements UserResult {

	private static final long serialVersionUID = -6516310978206066441L;
	
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
	 * 头像照片or手持证件人像照片  base64字符串
	 */
	private String photoStrBase64;
	
	/**
	 * 标准图片base64字符串
	 */
	private String picTStrBase64;

	/**
	 *  人像照片是头像照片，还是手持证件人像照片  t:头像 r:手持人像 
	 */
	private String photoType;
	
	/**
	 * 图片比对分值区间
	 */
	private String confidenceScore;
	
	/**
	 * 标准图片类型
	 * g是国政通
	 * x是芯片
	 */
	private String picTType;
	

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

	public String getPhotoStrBase64() {
		return photoStrBase64;
	}

	public void setPhotoStrBase64(String photoStrBase64) {
		this.photoStrBase64 = photoStrBase64;
	}

	public String getPicTStrBase64() {
		return picTStrBase64;
	}

	public void setPicTStrBase64(String picTStrBase64) {
		this.picTStrBase64 = picTStrBase64;
	}

	public String getPhotoType() {
		return photoType;
	}

	public void setPhotoType(String PhotoType) {
		this.photoType = PhotoType;
	}

	public String getConfidenceScore() {
		return confidenceScore;
	}

	public void setConfidenceScore(String confidenceScore) {
		this.confidenceScore = confidenceScore;
	}

	public String getPicTType() {
		return picTType;
	}

	public void setPicTType(String picTType) {
		this.picTType = picTType;
	}
}
