package com.cmos.edccommon.beans.piccompare;

import java.util.Date;

import com.cmos.common.bean.GenericBean;

/**
 * 人像比对，人像双照比对存表
 *
 */
public class CoPicCompareInfoDO extends GenericBean {
	
	private static final long serialVersionUID = -966363633114254070L;

	/**
	 * 分表 YYYYMM
	 */
	private String splitName;
	
	/**
    * 对比ID 唯一主键
    */
	private Long cmprId;
	
	/**
	 * 请求源编码
	 */
    private String reqstSrcCode;

    /**
     * 业务类型编码
     */
    private String bizTypeCode;
    /**
     * 全网唯一业务流水号
     */
    private String swftno;

    /**
     * 人像图片路径
     */
    private String photoPath;
    /**
     * 标准图片路径
     */
    private String picTPath;
    /**
     * 人像图片类型  r人像 t头像
     */
    private String photoType;
    /**
     * 标准图片类型  g国政通，x芯片头像
     */
    private String picTType;   
    /**
     * 响应编码
     */
    private String rspCode;

    /**
     * 响应信息内容
     */
    private String rspInfoCntt;

    /**
     * 对比结果
     */
    private String cmprRslt;

    /**
     * 对比分值
     */
    private String cmprScore;

    /**
     * 其他分值
     */
    private String otherScore;
    /**
     * 返回报文内容
     */
    private String backtoMsgCntt;

    /**
     * 创建用户ID
     */
    private String crtUserId;

    /**
     * 创建时间
     */
    private Date crtTime;

    /**
     * 创建应用系统ID
     */
    private String crtAppSysId;

    /**
     * 修改用户ID
     */
    private String modfUserId;

    /**
     * 修改时间
     */
    private Date modfTime;

    /**
     * 修改应用系统ID
     */
    private String modfAppSysId;

    public Long getCmprId() {
        return cmprId;
    }

    public void setCmprId(Long cmprId) {
        this.cmprId = cmprId;
    }

    public String getReqstSrcCode() {
        return reqstSrcCode;
    }

    public void setReqstSrcCode(String reqstSrcCode) {
        this.reqstSrcCode = reqstSrcCode == null ? null : reqstSrcCode.trim();
    }

    public String getBizTypeCode() {
        return bizTypeCode;
    }

    public void setBizTypeCode(String bizTypeCode) {
        this.bizTypeCode = bizTypeCode == null ? null : bizTypeCode.trim();
    }

    public String getSwftno() {
        return swftno;
    }

    public void setSwftno(String swftno) {
        this.swftno = swftno == null ? null : swftno.trim();
    }

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode == null ? null : rspCode.trim();
    }

    public String getRspInfoCntt() {
        return rspInfoCntt;
    }

    public void setRspInfoCntt(String rspInfoCntt) {
        this.rspInfoCntt = rspInfoCntt == null ? null : rspInfoCntt.trim();
    }

    public String getCmprRslt() {
        return cmprRslt;
    }

    public void setCmprRslt(String cmprRslt) {
        this.cmprRslt = cmprRslt == null ? null : cmprRslt.trim();
    }

    public String getCmprScore() {
        return cmprScore;
    }

    public void setCmprScore(String cmprScore) {
        this.cmprScore = cmprScore == null ? null : cmprScore.trim();
    }

    public String getBacktoMsgCntt() {
        return backtoMsgCntt;
    }

    public void setBacktoMsgCntt(String backtoMsgCntt) {
        this.backtoMsgCntt = backtoMsgCntt == null ? null : backtoMsgCntt.trim();
    }

    public String getCrtUserId() {
        return crtUserId;
    }

    public void setCrtUserId(String crtUserId) {
        this.crtUserId = crtUserId == null ? null : crtUserId.trim();
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public String getCrtAppSysId() {
        return crtAppSysId;
    }

    public void setCrtAppSysId(String crtAppSysId) {
        this.crtAppSysId = crtAppSysId == null ? null : crtAppSysId.trim();
    }

    public String getModfUserId() {
        return modfUserId;
    }

    public void setModfUserId(String modfUserId) {
        this.modfUserId = modfUserId == null ? null : modfUserId.trim();
    }

    public Date getModfTime() {
        return modfTime;
    }

    public void setModfTime(Date modfTime) {
        this.modfTime = modfTime;
    }

    public String getModfAppSysId() {
        return modfAppSysId;
    }

    public void setModfAppSysId(String modfAppSysId) {
        this.modfAppSysId = modfAppSysId == null ? null : modfAppSysId.trim();
    }
    
    public String getSplitName() {
		return splitName;
	}

	public void setSplitName(String splitName) {
		this.splitName = splitName == null ? null : splitName.trim();
	}
	
	
	   public String getPhotoPath() {
	        return photoPath;
	    }

	    public void setPhotoPath(String photoPath) {
	        this.photoPath = photoPath == null ? null : photoPath.trim();
	    }

	    public String getPicTPath() {
	        return picTPath;
	    }

	    public void setPicTPath(String picTPath) {
	        this.picTPath = picTPath == null ? null : picTPath.trim();
	    }

	    public String getPhotoType() {
	        return photoType;
	    }

	    public void setPhotoType(String photoType) {
	        this.photoType = photoType == null ? null : photoType.trim();
	    }

	    public String getPicTType() {
	        return picTType;
	    }

	    public void setPicTType(String picTType) {
	        this.picTType = picTType == null ? null : picTType.trim();
	    }

	    public String getOtherScore() {
	        return otherScore;
	    }

	    public void setOtherScore(String otherScore) {
	        this.otherScore = otherScore == null ? null : otherScore.trim();
	    }
	
}