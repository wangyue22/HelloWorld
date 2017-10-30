package com.cmos.edccommon.beans.piccompare;

import com.cmos.common.bean.GenericBean;
import com.cmos.common.domain.UserResult;


/**
 * 人像双照比对入参数据类型
 * 
 */
public class PicDoubleCompareInDTO extends GenericBean implements UserResult {

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
	 * 芯片图片路径
	 */
	private String picStoinPath;
	
	/**
	 * 国政通图片路径
	 */
	private String gztAvtrPath;
	
	/**
	 *  人像照片是头像照片，还是手持证件人像照片  t:头像 r:手持人像 
	 */
	private String photoType;
	
	/**
	 * 芯片图片比对分值区间
	 */
	private String picStoinScore;
	
	/**
	 * 国政通图片比对分值区间
	 */
	private String gztAvtrScore;

	/**
	 *  双照比对规则类型，
	 *  1 当取值为true时，要求人像图片和国政通，以及人像图片和芯片，两次都比对成功，才返回比对成功，否则比对失败
	 *  2 取值不是true 或为空时 （默认规则）：
	 *  	如果国政通获取图片为空,nfc芯片图片不为空 则 优先比对nfc芯片图片 没有二次比对,
	 * 		如果国政通图片不为空 先比对国政通, 国政通比对失败或者比对不一致时,使用nfc芯片图片进行比对；
	 *  
	 */
	private String bothCompFlag;

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

	public String getPicStoinPath() {
		return picStoinPath;
	}

	public void setPicStoinPath(String picStoinPath) {
		this.picStoinPath = picStoinPath;
	}

	public String getGztAvtrPath() {
		return gztAvtrPath;
	}

	public void setGztAvtrPath(String gztAvtrPath) {
		this.gztAvtrPath = gztAvtrPath;
	}

	public String getPhotoType() {
		return photoType;
	}

	public void setPhotoType(String photoType) {
		this.photoType = photoType;
	}

	public String getPicStoinScore() {
		return picStoinScore;
	}

	public void setPicStoinScore(String picStoinScore) {
		this.picStoinScore = picStoinScore;
	}

	public String getGztAvtrScore() {
		return gztAvtrScore;
	}

	public void setGztAvtrScore(String gztAvtrScore) {
		this.gztAvtrScore = gztAvtrScore;
	}

	public String getBothCompFlag() {
		return bothCompFlag;
	}

	public void setBothCompFlag(String bothCompFlag) {
		this.bothCompFlag = bothCompFlag;
	}

}
