package com.cmos.edccommon.beans.piccompare;

import com.cmos.common.bean.GenericBean;
import com.cmos.common.domain.UserResult;


/**
 * 人像比对判定入参数据类型
 * @author: xdx
 */
public class PicCompareInDTO extends GenericBean implements UserResult {

	private static final long serialVersionUID = -6516310978206066441L;
	
	/**
	 * 定义各个业务系统id
     * 用于存储表中crtSysId,mdfSysId
	 */
	private String appSysID;
	
    /**
     * 定义各个业务系统程序用户id
     * 用于存储表中crtUserId,mdfUserId
     */
	private String appUserID;
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
	
	/**
	 * 人像图片解密秘钥
	 */
	private String crkey;
	

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

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
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

	public void setConfidenceScore(String confidenceScore) {
		this.confidenceScore = confidenceScore;
	}

	public String getPicTType() {
		return picTType;
	}

	public void setPicTType(String picTType) {
		this.picTType = picTType;
	}

	public String getCrkey() {
		return crkey;
	}

	public void setCrkey(String crkey) {
		this.crkey = crkey;
	}
	public String getAppSysID() {
		return appSysID;
	}
	public void setAppSysID(String appSysID) {
		this.appSysID = appSysID;
	}
	public String getAppUserID() {
		return appUserID;
	}
	public void setAppUserID(String appUserID) {
		this.appUserID = appUserID;
	}

}
