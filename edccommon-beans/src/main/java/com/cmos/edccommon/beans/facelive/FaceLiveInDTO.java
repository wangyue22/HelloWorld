package com.cmos.edccommon.beans.facelive;

import com.cmos.common.bean.GenericBean;
import com.cmos.common.domain.UserResult;

/**
 * 人像双照比对入参数据类型
 * @author: xdx
 */
public class FaceLiveInDTO extends GenericBean implements UserResult {

	private static final long serialVersionUID = 4550693735329609512L;

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
	 * 人像图片路径
	 */
	private String picRPath;
	
	/**
	 * 人像图片解密秘钥
	 */
	private String crkey;
	
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
