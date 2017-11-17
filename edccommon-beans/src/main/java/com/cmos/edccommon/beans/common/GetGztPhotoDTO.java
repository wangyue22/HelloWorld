package com.cmos.edccommon.beans.common;

import com.cmos.common.bean.GenericBean;

public class GetGztPhotoDTO extends GenericBean {

    private static final long serialVersionUID = 1L;
    /**
     * 国政通头像路径
     */
    private String gztUrl;
    /**
     * 省编码
     */
    private String provCode;
    /**
     * 请求源
     */
    private String reqstSrcCode;
    /**
     * 来源系统
     */
    private String sourceSystem;

    /**
     * 流水号
     */
    private String swftno;

    /**
     * 获取流水号
     * @return
     */
    public String getSwftno() {
        return swftno;
    }

    /**
     * 设置流水号
     * @param swftno
     */
    public void setSwftno(String swftno) {
        this.swftno = swftno;
    }

    
    
    public String getGztUrl() {
        return gztUrl;
    }

    public void setGztUrl(String gztUrl) {
        this.gztUrl = gztUrl;
    }

    /**
     * 获取省编码
     * @return provCode 省编码
     */
    public String getProvCode() {
        return provCode;
    }
    /**
     * 设置省编码
     * @param provCode 省编码
     */
    public void setProvCode(String provCode) {
        this.provCode = provCode;
    }
    /**
     * 获取请求源
     * @return reqstSrcCode 请求源
     */
    public String getReqstSrcCode() {
        return reqstSrcCode;
    }
    /**
     * 设置请求源
     * @param reqstSrcCode 请求源
     */
    public void setReqstSrcCode(String reqstSrcCode) {
        this.reqstSrcCode = reqstSrcCode;
    }
    /**
     * 获取来源系统名称
     * @return sourceSystem 来源系统名称
     */
    public String getSourceSystem() {
        return sourceSystem;
    }
    /**
     * 设置来源系统名称
     * @param sourceSystem 来源系统名称
     */
    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

}
