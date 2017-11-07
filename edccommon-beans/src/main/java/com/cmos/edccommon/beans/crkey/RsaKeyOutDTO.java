package com.cmos.edccommon.beans.crkey;

import java.util.List;

import com.cmos.common.bean.GenericBean;

public class RsaKeyOutDTO extends GenericBean {
    private static final long serialVersionUID = 1L;

    private List<RsaKeyDO> beans;

    private String returnCode;

    private String returnMessage;

    public List<RsaKeyDO> getBeans() {
        return beans;
    }

    public void setBeans(List<RsaKeyDO> beans) {
        this.beans = beans;
    }

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

}
