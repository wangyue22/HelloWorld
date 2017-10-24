package com.cmos.edccommon.beans.piccompare;

import com.cmos.common.bean.GenericBean;
import com.cmos.common.domain.UserResult;


/**
 * 人像比对判定入参数据类型
 * 
 */
public class PicCompareInDTO extends GenericBean implements UserResult {

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
	 * 头像照片or手持证件人像照片  图片路径
	 */
	private String photoPath;
	
	/**
	 * 标准图片路径
	 */
	private String picTPath;

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

	public String getHndhldCredPhotoPath() {
		return photoPath;
	}

	public void setHndhldCredPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getPicTPath() {
		return picTPath;
	}

	public void setPicTPath(String picTPath) {
		this.picTPath = picTPath;
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

	public void setConfidenceScore(String picScore) {
		this.confidenceScore = confidenceScore;
	}

	public String getPicTType() {
		return picTType;
	}

	public void setPicType(String picType) {
		this.picTType = picTType;
	}

}
