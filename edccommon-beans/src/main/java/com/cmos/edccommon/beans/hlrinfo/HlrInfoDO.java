package com.cmos.edccommon.beans.hlrinfo;

import com.cmos.common.bean.GenericBean;
import java.util.Date;
/**
 * 根据手机号获取省份
 * @author: zhaohu
 */
public class HlrInfoDO extends GenericBean {
	
	private static final long serialVersionUID = 3862163719814136103L;
    private Long sctnoId;//号段ID

    private String sctnoCode;//号段编码

    private String sctnoTypeCd;//号段类型代码

    private String regnCode;//地区编码

    private String hlrBelgCityCode;//HLR所属城市编码

    private String brudrctCode;//局向编码

    private Date validTime;//有效时间

    private String provCode;//省份编码
    /**
     * 创建用户id
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
     * 修改系统
     */
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