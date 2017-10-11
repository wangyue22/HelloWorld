package com.cmos.edccommon.beans.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.cmos.common.bean.GenericBean;

public class InputObject extends GenericBean {

	
	private static final long serialVersionUID = 7181946961034601621L;
	
	private String busiId;//业务编码
	private Map<String, String> params =null ;     // 业务入参Map类型
	private Map<String, String> logParams =null;// 如需记录登录日志则都在此
	private Map<String, String> images=null;//存放图片流
	private List<Map<String, String>> beans =null ;// 业务入参List 类型
	public  InputObject(){
	   this.params=new HashMap<String, String>() ; 
	   this.logParams=new  HashMap<String, String>();
	   this.images=new HashMap<String, String>() ; 
	}
	public Map<String, String> getImages() {
		return images;
	}
	public void setImages(Map<String, String> images) {
		this.images = images;
	}

	public String getBusiId() {
		return busiId;
	}
	
	public void setBusiId(String busiId) {
		this.busiId = busiId;
	}

	public Map<String, String> getLogParams() {
		return logParams;
	}

	public void setLogParams(Map<String, String> logParams) {
		this.logParams = logParams;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public List<Map<String, String>> getBeans() {
		return beans;
	}

	public void setBeans(List<Map<String, String>> beans) {
		this.beans = beans;
	}
	
	public static  String inputObjectToString(InputObject in){
		return JSON.toJSONString(in);
	}
	
	public static  String objectToString(Object object){
		return JSON.toJSONString(object);
	}
	
	public static InputObject StringToInputObject(String json){
		return JSON.parseObject(json, InputObject.class);
	}
	
	
	public static Object StringToInputObject(String json,Object obj){
		return JSON.parseObject(json, obj.getClass());
	}
}
