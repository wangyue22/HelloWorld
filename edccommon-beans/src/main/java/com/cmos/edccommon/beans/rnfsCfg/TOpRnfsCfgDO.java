package com.cmos.edccommon.beans.rnfsCfg;

import com.cmos.common.bean.GenericBean;

import java.util.Date;
/**
 * 人像比对rnfs配置
 *
 */
public class TOpRnfsCfgDO extends GenericBean {
    private static final long serialVersionUID = 1L;
    private Long configId;

    private String rnfsGrpNm;

    private String rootPath;

    private String rnfsAddrPrtnum;

    private String uploadDwnldModeCd;

    private String ftpPrtnum;

    private String ftpUserNm;

    private String ftpUserPw;

    private String ftpAls;

    private String rmk;

    private String bizSysCode;

    private String crtUserId;

    private Date crtTime;

    private String crtAppSysId;

    private String modfUserId;

    private Date modfTime;

    private String modfAppSysId;

    private String cacheTypeCd;

    private String validFlag;
    private String grpCacheKeyVal;

    private String alsCacheKeyVal;

    public String getGrpCacheKeyVal() {
        return grpCacheKeyVal;
    }

    public void setGrpCacheKeyVal(String grpCacheKeyVal) {
        this.grpCacheKeyVal = grpCacheKeyVal;
    }

    public String getAlsCacheKeyVal() {
        return alsCacheKeyVal;
    }

    public void setAlsCacheKeyVal(String alsCacheKeyVal) {
        this.alsCacheKeyVal = alsCacheKeyVal;
    }

    public String getCacheDataTypeCd() {
        return cacheDataTypeCd;
    }

    public void setCacheDataTypeCd(String cacheDataTypeCd) {
        this.cacheDataTypeCd = cacheDataTypeCd;
    }

    public String getPushAlertFlag() {
        return pushAlertFlag;
    }

    public void setPushAlertFlag(String pushAlertFlag) {
        this.pushAlertFlag = pushAlertFlag;
    }

    private String cacheDataTypeCd;

    private String pushAlertFlag;
    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public String getRnfsGrpNm() {
        return rnfsGrpNm;
    }

    public void setRnfsGrpNm(String rnfsGrpNm) {
        this.rnfsGrpNm = rnfsGrpNm == null ? null : rnfsGrpNm.trim();
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath == null ? null : rootPath.trim();
    }

    public String getRnfsAddrPrtnum() {
        return rnfsAddrPrtnum;
    }

    public void setRnfsAddrPrtnum(String rnfsAddrPrtnum) {
        this.rnfsAddrPrtnum = rnfsAddrPrtnum == null ? null : rnfsAddrPrtnum.trim();
    }

    public String getUploadDwnldModeCd() {
        return uploadDwnldModeCd;
    }

    public void setUploadDwnldModeCd(String uploadDwnldModeCd) {
        this.uploadDwnldModeCd = uploadDwnldModeCd == null ? null : uploadDwnldModeCd.trim();
    }

    public String getFtpPrtnum() {
        return ftpPrtnum;
    }

    public void setFtpPrtnum(String ftpPrtnum) {
        this.ftpPrtnum = ftpPrtnum == null ? null : ftpPrtnum.trim();
    }

    public String getFtpUserNm() {
        return ftpUserNm;
    }

    public void setFtpUserNm(String ftpUserNm) {
        this.ftpUserNm = ftpUserNm == null ? null : ftpUserNm.trim();
    }

    public String getFtpUserPw() {
        return ftpUserPw;
    }

    public void setFtpUserPw(String ftpUserPw) {
        this.ftpUserPw = ftpUserPw == null ? null : ftpUserPw.trim();
    }

    public String getFtpAls() {
        return ftpAls;
    }

    public void setFtpAls(String ftpAls) {
        this.ftpAls = ftpAls == null ? null : ftpAls.trim();
    }

    public String getRmk() {
        return rmk;
    }

    public void setRmk(String rmk) {
        this.rmk = rmk == null ? null : rmk.trim();
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

    public String getCacheTypeCd() {
        return cacheTypeCd;
    }

    public void setCacheTypeCd(String cacheTypeCd) {
        this.cacheTypeCd = cacheTypeCd == null ? null : cacheTypeCd.trim();
    }

    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag == null ? null : validFlag.trim();
    }
}