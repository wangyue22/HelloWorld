package com.cmos.edccommon.beans.realityAccount;

import java.sql.Timestamp;

import com.cmos.common.bean.GenericBean;

/**
 * 人像比对实名账户
 *
 */
public class RealityAccountDO extends GenericBean {

    private static final long serialVersionUID = 1L;

    /**
     * 实名账号ID
     */
    private Long realAcctId;

    /**
     * 请求源编码
     */
    private String reqstSrcCode;

    /**
     * 请求源名称
     */
    private String reqstSrcNm;

    /**
     * 用户名
     */
    private String userNm;

    /**
     * 密码
     */
    private String pw;

    /**
     * AES加密秘钥
     */
    private String aesKey;

    /**
     * DES秘钥
     */
    private String desKey;

    /**
     * 接口权限编码
     */
    private String intfPrivCode;

    /**
     * 有效标志
     */
    private String validFlag;

    /**
     * 缓存类型代码
     */
    private String cacheTypeCd;

    /**
     * 业务系统编码
     */
    private String bizSysCode;

    /**
     * 创建用户ID
     */
    private String crtUserId;

    /**
     * 创建时间
     */
    private Timestamp crtTime;

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
    private Timestamp modifyTime;

    /**
     * 修改应用系统ID
     */
    private String modifyAppSysId;

    /**
     * 缓存数据类型代码
     */
    private String cacheDataTypeCd;

    /**
     * 推送提醒标志
     */
    private String pushAlertFlag;

    /**
     * 缓存KEY值
     */
    private String cacheKeyVal;

    public String getCacheKeyVal() {
        return cacheKeyVal;
    }

    public void setCacheKeyVal(String cacheKeyVal) {
        this.cacheKeyVal = cacheKeyVal;
    }

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

    public Timestamp getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Timestamp crtTime) {
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

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyAppSysId() {
        return modifyAppSysId;
    }

    public void setModifyAppSysId(String modifyAppSysId) {
        this.modifyAppSysId = modifyAppSysId == null ? null : modifyAppSysId.trim();
    }
}