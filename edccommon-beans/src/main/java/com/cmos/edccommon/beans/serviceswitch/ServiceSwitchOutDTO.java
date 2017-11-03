package com.cmos.edccommon.beans.serviceswitch;

import java.util.List;

import com.cmos.common.bean.GenericBean;

public class ServiceSwitchOutDTO extends GenericBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ServiceSwitchDO> beans;
   
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

	public List<ServiceSwitchDO> getBeans() {
		return beans;
	}

	public void setBeans(List<ServiceSwitchDO> beans) {
		this.beans = beans;
	}
}