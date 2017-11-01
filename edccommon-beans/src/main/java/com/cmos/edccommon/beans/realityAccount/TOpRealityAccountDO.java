package com.cmos.edccommon.beans.realityAccount;

import com.cmos.common.bean.GenericBean;

import java.util.Date;

/**
 * 人像比对实名账户
 *
 */
public class TOpRealityAccountDO extends GenericBean {
    private static final long serialVersionUID = 1L;
    private Long realAcctId;

    private String reqstSrcCode;

    private String reqstSrcNm;

    private String userNm;

    private String pw;

    private String aesKey;

    private String desKey;

    private String intfPrivCode;

    private String validFlag;

    private String cacheTypeCd;

    private String bizSysCode;

    private String crtUserId;

    private Date crtTime;

    private String crtAppSysId;

    private String modfUserId;

    private Date modifyTime;

    private String modifyAppSysId;

    public String getPushAlertFlag() {
        return pushAlertFlag;
    }

    public void setPushAlertFlag(String pushAlertFlag) {
        this.pushAlertFlag = pushAlertFlag;
    }


    public String getCacheDataTypeCd() {
        return cacheDataTypeCd;
    }

    public void setCacheDataTypeCd(String cacheDataTypeCd) {
        this.cacheDataTypeCd = cacheDataTypeCd;
    }

    private String cacheDataTypeCd;
    private String pushAlertFlag;

    public Long getRealAcctId() {
        return realAcctId;
    }

    public void setRealAcctId(Long realAcctId) {
        this.realAcctId = realAcctId;
    }

    public String getReqstSrcCode() {
        return reqstSrcCode;
    }

    public void setReqstSrcCode(String reqstSrcCode) {
        this.reqstSrcCode = reqstSrcCode == null ? null : reqstSrcCode.trim();
    }

    public String getReqstSrcNm() {
        return reqstSrcNm;
    }

    public void setReqstSrcNm(String reqstSrcNm) {
        this.reqstSrcNm = reqstSrcNm == null ? null : reqstSrcNm.trim();
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm == null ? null : userNm.trim();
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw == null ? null : pw.trim();
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey == null ? null : aesKey.trim();
    }

    public String getDesKey() {
        return desKey;
    }

    public void setDesKey(String desKey) {
        this.desKey = desKey == null ? null : desKey.trim();
    }

    public String getIntfPrivCode() {
        return intfPrivCode;
    }

    public void setIntfPrivCode(String intfPrivCode) {
        this.intfPrivCode = intfPrivCode == null ? null : intfPrivCode.trim();
    }

    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag == null ? null : validFlag.trim();
    }

    public String getCacheTypeCd() {
        return cacheTypeCd;
    }

    public void setCacheTypeCd(String cacheTypeCd) {
        this.cacheTypeCd = cacheTypeCd == null ? null : cacheTypeCd.trim();
    }

    public String getBizSysCode() {
        return bizSysCode;
    }

    public void setBizSysCode(String bizSysCode) {
        this.bizSysCode = bizSysCode == null ? null : bizSysCode.trim();
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

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyAppSysId() {
        return modifyAppSysId;
    }

    public void setModifyAppSysId(String modifyAppSysId) {
        this.modifyAppSysId = modifyAppSysId == null ? null : modifyAppSysId.trim();
    }
}