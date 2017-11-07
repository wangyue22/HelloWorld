package com.cmos.edccommon.beans.rnfsCfg;

import java.sql.Timestamp;

import com.cmos.common.bean.GenericBean;

/**rnfs配置表dto
 * @author wangfei
 */
public class RnfsCfgInDTO extends GenericBean{

    private static final long serialVersionUID = 1L;

    /**主键
     * 配置id
     */
    private Long configId;

    /**
     * RNFS组名称
     */
    private String rnfsGrpNm;

    /**
     * 根路径
     */
    private String rootPath;

    /**
     * RNFS地址端口号
     */
    private String rnfsAddrPrtnum;

    /**
     * 上传下载方式代码
     */
    private String uploadDwnldModeCd;

    /**
     * FTP端口号
     */
    private String ftpPrtnum;

    /**
     * FTP用户名称
     */
    private String ftpUserNm;

    /**
     * FTP用户密码
     */
    private String ftpUserPw;

    /**
     * FTP别名
     */
    private String ftpAls;

    /**
     * 备注
     */
    private String rmk;

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
     * 有效标志
     */
    private String validFlag;

    /**
     * 缓存数据类型代码
     */
    private String cacheDataTypeCd;

    /**
     * 推送提醒标志
     */
    private String pushAlertFlag;

    /**
     * 组缓存KEY值
     */
    private String grpCacheKeyVal;

    /**
     * 别名缓存KEY值
     */
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
