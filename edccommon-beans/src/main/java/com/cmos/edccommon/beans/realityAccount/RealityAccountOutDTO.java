package com.cmos.edccommon.beans.realityAccount;

import java.util.List;

import com.cmos.common.bean.GenericBean;

/**
 * 实名账户DTO
 * @author: 王飞
 */
public class RealityAccountOutDTO extends GenericBean {

    private static final long serialVersionUID = 1L;

    private List<RealityAccountDO> beans;

    private String returnCode;

    private String returnMessage;

    public List<RealityAccountDO> getBeans() {
        return beans;
    }

    public void setBeans(List<RealityAccountDO> beans) {
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
