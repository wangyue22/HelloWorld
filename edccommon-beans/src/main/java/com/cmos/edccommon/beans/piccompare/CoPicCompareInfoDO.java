package com.cmos.edccommon.beans.piccompare;

import java.util.Date;

import com.cmos.common.bean.GenericBean;

public class CoPicCompareInfoDO extends GenericBean {
    private Long cmprId;

    private String reqstSrcCode;

    private String bizTypeCode;

    private String whntwkUniqBizSwftno;

    private String idno;

    private String rspCode;

    private String rspInfoCntt;

    private String cmprRslt;

    private String cmprScore;

    private String backtoMsgCntt;

    private String crtUserId;

    private Date crtTime;

    private String crtAppSysId;

    private String modfUserId;

    private Date modfTime;

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

    public String getWhntwkUniqBizSwftno() {
        return whntwkUniqBizSwftno;
    }

    public void setWhntwkUniqBizSwftno(String whntwkUniqBizSwftno) {
        this.whntwkUniqBizSwftno = whntwkUniqBizSwftno == null ? null : whntwkUniqBizSwftno.trim();
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno == null ? null : idno.trim();
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
}