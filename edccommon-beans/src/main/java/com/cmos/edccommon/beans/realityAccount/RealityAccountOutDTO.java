package com.cmos.edccommon.beans.realityAccount;

import java.util.List;

import com.cmos.common.bean.GenericBean;

public class RealityAccountOutDTO extends GenericBean {

	private static final long serialVersionUID = 112084917690811921L;

	private List<TOpRealityAccountDO> beans;

    private String returnCode;

    private String returnMessage;

    public List<TOpRealityAccountDO> getBeans() {
        return beans;
    }

    public void setBeans(List<TOpRealityAccountDO> beans) {
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
