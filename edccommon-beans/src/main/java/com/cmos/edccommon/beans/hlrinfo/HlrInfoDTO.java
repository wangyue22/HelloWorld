package com.cmos.edccommon.beans.hlrinfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.cmos.common.bean.GenericBean;
import com.cmos.common.domain.UserResult;



/**
 * 统一出参数据类型  
 * 
 */
public class HlrInfoDTO extends GenericBean implements UserResult {
	private static final long serialVersionUID = 3862163719814136111L;
	private String returnCode="0000";
	private String returnMessage="success";
	private Map<String, String> bean=new HashMap<String,String>();
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
