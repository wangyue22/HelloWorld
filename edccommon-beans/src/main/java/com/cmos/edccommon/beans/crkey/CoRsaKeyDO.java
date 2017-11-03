package com.cmos.edccommon.beans.crkey;

import java.util.Date;

import com.cmos.common.bean.GenericBean;
/**
 * RSA秘钥数据表
 * @author xdx
 *
 */
public class CoRsaKeyDO extends GenericBean {
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
    private Date crtTime;
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
    private Date modfTime;
    /**
     * 缓存KEY值
     */
    private String cacheKeyVal;
    /**
     * 修改系统
     */
    private String modfAppSysId;
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

    public String getCacheKeyVal() {
        return cacheKeyVal;
    }

    public void setCacheKeyVal(String cacheKeyVal) {
        this.cacheKeyVal = cacheKeyVal == null ? null : cacheKeyVal.trim();
    }
}