package com.cmos.edccommon.beans.phoneprovcode;

import com.cmos.common.bean.GenericBean;
import java.util.Date;

public class HlrInfoDO extends GenericBean {
    private Long sctnoId;

    private String sctnoCode;

    private String sctnoTypeCd;

    private String regnCode;

    private String hlrBelgCityCode;

    private String brudrctCode;

    private Date validTime;

    private String provCode;

    private String crtUserId;

    private Date crtTime;

    private String crtAppSysId;

    private String modfUserId;

    private Date modfTime;

    private String modfAppSysId;

    public Long getSctnoId() {
        return sctnoId;
    }

    public void setSctnoId(Long sctnoId) {
        this.sctnoId = sctnoId;
    }

    public String getSctnoCode() {
        return sctnoCode;
    }

    public void setSctnoCode(String sctnoCode) {
        this.sctnoCode = sctnoCode == null ? null : sctnoCode.trim();
    }

    public String getSctnoTypeCd() {
        return sctnoTypeCd;
    }

    public void setSctnoTypeCd(String sctnoTypeCd) {
        this.sctnoTypeCd = sctnoTypeCd == null ? null : sctnoTypeCd.trim();
    }

    public String getRegnCode() {
        return regnCode;
    }

    public void setRegnCode(String regnCode) {
        this.regnCode = regnCode == null ? null : regnCode.trim();
    }

    public String getHlrBelgCityCode() {
        return hlrBelgCityCode;
    }

    public void setHlrBelgCityCode(String hlrBelgCityCode) {
        this.hlrBelgCityCode = hlrBelgCityCode == null ? null : hlrBelgCityCode.trim();
    }

    public String getBrudrctCode() {
        return brudrctCode;
    }

    public void setBrudrctCode(String brudrctCode) {
        this.brudrctCode = brudrctCode == null ? null : brudrctCode.trim();
    }

    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    public String getProvCode() {
        return provCode;
    }

    public void setProvCode(String provCode) {
        this.provCode = provCode == null ? null : provCode.trim();
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