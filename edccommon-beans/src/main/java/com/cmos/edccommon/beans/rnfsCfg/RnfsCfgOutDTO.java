package com.cmos.edccommon.beans.rnfsCfg;

import java.util.List;

import com.cmos.common.bean.GenericBean;
/**
 * 运营管理 rnfs
 * @author: 王飞
 */
public class RnfsCfgOutDTO extends GenericBean {
    private static final long serialVersionUID = 1L;

    private List<RnfsCfgDO> beans;

    private String returnCode;

    private String returnMessage;


    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public List<RnfsCfgDO> getBeans() {
        return beans;
    }

    public void setBeans(List<RnfsCfgDO> beans) {
        this.beans = beans;
    }
}