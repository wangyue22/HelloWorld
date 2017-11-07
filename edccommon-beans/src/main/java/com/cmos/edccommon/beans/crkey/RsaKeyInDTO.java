package com.cmos.edccommon.beans.crkey;

import java.sql.Timestamp;

import com.cmos.common.bean.GenericBean;

/**RSA秘钥
 * @author 王飞
 *
 */
public class RsaKeyInDTO extends GenericBean {
    private static final long serialVersionUID = 90716934787134967L;

    /**
     * 主键
     */
    private Long crkeyId;

    /**
     * 请求源编码
     */
    private String reqstSrcCode;

    /**
     * 请求源名称
     */
    private String reqstSrcNm;

    /**
     * 公钥
     */
    private String pbkey;
    /**
     * 私钥
     */
    private String prtkey;
    /**
     * 业务类型代码
     */
    private String bizTypeCd;
    /**
     * 有效标志
     */
    private String validFlag;
    /**
     * 创建用户id
     *
     */
    private String crtUserId;
    /**
     * 创建时间
     */
    private Timestamp crtTime;
    /**
     * 创建系统id
     */
    private String crtAppSysId;
    /**
     * 修改用户名
     */
    private String modfUserId;
    /**
     * 修改时间
     */
    private Timestamp modfTime;

    /**
     * 修改应用系统ID
     */
    private String modfAppSysId;

    /**
     * 缓存类型代码
     */
    private String cacheTypeCd;

    /**
     * 业务系统编码
     */
    private String bizSysCode;

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

    public Long getCrkeyId() {
        return crkeyId;
    }

    public void setCrkeyId(Long crkeyId) {
        this.crkeyId = crkeyId;
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

    public String getPbkey() {
        return pbkey;
    }

    public void setPbkey(String pbkey) {
        this.pbkey = pbkey == null ? null : pbkey.trim();
    }

    public String getPrtkey() {
        return prtkey;
    }

    public void setPrtkey(String prtkey) {
        this.prtkey = prtkey == null ? null : prtkey.trim();
    }

    public String getBizTypeCd() {
        return bizTypeCd;
    }

    public void setBizTypeCd(String bizTypeCd) {
        this.bizTypeCd = bizTypeCd == null ? null : bizTypeCd.trim();
    }

    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag == null ? null : validFlag.trim();
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

    public Timestamp getModfTime() {
        return modfTime;
    }

    public void setModfTime(Timestamp modfTime) {
        this.modfTime = modfTime;
    }

    public String getModfAppSysId() {
        return modfAppSysId;
    }

    public void setModfAppSysId(String modfAppSysId) {
        this.modfAppSysId = modfAppSysId == null ? null : modfAppSysId.trim();
    }

    public String getCacheKeyVal() {
        return cacheKeyVal;
    }

    public void setCacheKeyVal(String cacheKeyVal) {
        this.cacheKeyVal = cacheKeyVal == null ? null : cacheKeyVal.trim();
    }

    public String getBizSysCode() {
        return bizSysCode;
    }

    public void setBizSysCode(String bizSysCode) {
        this.bizSysCode = bizSysCode;
    }

    public String getCacheTypeCd() {
        return cacheTypeCd;
    }

    public void setCacheTypeCd(String cacheTypeCd) {
        this.cacheTypeCd = cacheTypeCd;
    }
}
