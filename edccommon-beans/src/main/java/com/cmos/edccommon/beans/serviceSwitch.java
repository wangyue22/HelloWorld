package com.cmos.edccommon.beans;

import java.io.Serializable;
import java.util.Date;

public class serviceSwitch implements Serializable {
   
	private static final long serialVersionUID = 1819442235693036139L;

	private Long swtchId;

    private String bizSysCode;

    private String swtchTypeCd;

    private String swtchKey;

    private String swtchVal;

    private String swtchDesc;

    private String pushType;

    private String validFlag;

    private String crtUserId;

    private Date crtTime;

    private String crtAppSysId;

    private String modfUserId;

    private Date modfTime;

    private String modfAppSysId;

    public Long getSwtchId() {
        return swtchId;
    }

    public void setSwtchId(Long swtchId) {
        this.swtchId = swtchId;
    }

    public String getBizSysCode() {
        return bizSysCode;
    }

    public void setBizSysCode(String bizSysCode) {
        this.bizSysCode = bizSysCode == null ? null : bizSysCode.trim();
    }

    public String getSwtchTypeCd() {
        return swtchTypeCd;
    }

    public void setSwtchTypeCd(String swtchTypeCd) {
        this.swtchTypeCd = swtchTypeCd == null ? null : swtchTypeCd.trim();
    }

    public String getSwtchKey() {
        return swtchKey;
    }

    public void setSwtchKey(String swtchKey) {
        this.swtchKey = swtchKey == null ? null : swtchKey.trim();
    }

    public String getSwtchVal() {
        return swtchVal;
    }

    public void setSwtchVal(String swtchVal) {
        this.swtchVal = swtchVal == null ? null : swtchVal.trim();
    }

    public String getSwtchDesc() {
        return swtchDesc;
    }

    public void setSwtchDesc(String swtchDesc) {
        this.swtchDesc = swtchDesc == null ? null : swtchDesc.trim();
    }

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType == null ? null : pushType.trim();
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
}