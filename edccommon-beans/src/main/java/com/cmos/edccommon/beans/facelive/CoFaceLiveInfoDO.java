package com.cmos.edccommon.beans.facelive;

import com.cmos.common.bean.GenericBean;
import java.util.Date;

public class CoFaceLiveInfoDO extends GenericBean {

	private static final long serialVersionUID = -2173816809435877349L;

	/**
	 * 分表字段，默认是201709
	 */
	private String splitName = "201709";
	/**
	 * 检测ID, 主键
	 */
	private Long detctnId;
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
     * 响应编码
     */
    private String rspCode;
    /**
     * 响应信息内容
     */
    private String rspInfoCntt;
    /**
     * 识别人脸数
     */
    private String idntifFaceCnt;
    /**
     * 识别分值
     */
    private String idntifScore;
    /**
     * 静默活体服务器返回报文内容
     */
    private String backtoMsgCntt;

    private String crtUserId;
    /**
     * 创建时间
     */
    private Date crtTime;

    private String crtAppSysId;
    
    private String modfUserId;

    private Date modfTime;

    private String modfAppSysId;

    public Long getDetctnId() {
        return detctnId;
    }

    public void setDetctnId(Long detctnId) {
        this.detctnId = detctnId;
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

    public String getIdntifFaceCnt() {
        return idntifFaceCnt;
    }

    public void setIdntifFaceCnt(String idntifFaceCnt) {
        this.idntifFaceCnt = idntifFaceCnt == null ? null : idntifFaceCnt.trim();
    }

    public String getIdntifScore() {
        return idntifScore;
    }

    public void setIdntifScore(String idntifScore) {
        this.idntifScore = idntifScore == null ? null : idntifScore.trim();
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
}