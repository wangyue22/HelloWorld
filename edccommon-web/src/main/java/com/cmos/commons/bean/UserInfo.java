package com.cmos.commons.bean;

import com.cmos.common.bean.GenericBean;

/**
 * 公用对象
 * @author Administrator
 *
 */
public class UserInfo extends GenericBean {

	private static final long serialVersionUID = 1109105654966370300L;

	private String jsessionId;

	private String serialNumber;

	private String ip;

	private String operId;
	
	public UserInfo(){
		
	}
	
	/**
	 * 
	 * @param jsessionId
	 * @param serialNumber
	 * @param ip
	 * @param operId
	 */
	public UserInfo(String jsessionId ,String serialNumber ,String ip ,String operId){
		 
		this.jsessionId = jsessionId;
		this.serialNumber = serialNumber;
		this.ip = ip;
		this.operId = operId;
		
	}

	public String getJsessionId() {
		return jsessionId;
	}

	public void setJsessionId(String jsessionId) {
		this.jsessionId = jsessionId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}
	
}
