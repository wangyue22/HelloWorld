package com.cmos.edccommon.beans.hlrinfo;

import java.util.HashMap;
import java.util.Map;
import com.cmos.common.bean.GenericBean;
import com.cmos.common.domain.UserResult;
/**
 * 根据手机号得到省编码以及手机所属运营商统一返回对象
 * 
 * @author: zhaohu
 *
 * @created: 2017年10月27日
 */
public class HlrInfoDTO extends GenericBean implements UserResult {
	private static final long serialVersionUID = 3862163719814136111L;
	/**
	 * 返回码
	 */
	private String returnCode="0000";
	/**
	 * 返回信息
	 */
	private String returnMessage="success";
	/**
	 * 返回体
	 */
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
