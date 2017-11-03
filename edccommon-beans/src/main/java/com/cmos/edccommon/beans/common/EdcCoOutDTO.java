package com.cmos.edccommon.beans.common;

import java.util.HashMap;
import java.util.Map;

import com.cmos.common.bean.GenericBean;
import com.cmos.common.domain.UserResult;



/**
 * 统一出参数据类型  
 * 
 */
public class EdcCoOutDTO extends GenericBean implements UserResult {
	private static final long serialVersionUID = -5298399887946833718L;

	private String returnCode;//返回编码 

	private String returnMessage;//返回信息
	
	private Map<String, String> bean=new HashMap<String,String>();//返回报文体
	
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
	public Map<String, String> getBean() {
		return bean;
	}
	public void setBean(Map<String, String> bean) {
		this.bean = bean;
	}
}
