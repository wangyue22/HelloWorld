package com.cmos.edccommon.beans.cache;

import java.util.List;
import java.util.Map;

import com.cmos.common.bean.GenericBean;

/**
 * 操作缓存入参DTO对象
 * @ClassName:  OperationCacheDTO
 * @Description:TODO
 * @author: 任林达
 * @date:   2017年10月30日 上午11:25:39
 */
@SuppressWarnings("rawtypes")
public class CacheOutDTO  extends GenericBean{
    private static final long serialVersionUID = 1L;
    /**
	 * 返回编码
	 * 0000  成功
	 * 2999  业务异常，如参数为空
	 * 9999 系统异常
	 */
	private String returnCode;
	/**
	 * 返回描述
	 */
	private String returnMessage;
	
	/**
     * value(object类型出参 )
     */
    private Object objectValue;
        
    /**
     * value(string类型出参 )
     */
    private String stringValue;
    
    /**
     * value(存放map类型出参)
     */
    private Map<String,String> mapValue;
    
    /**
     * value(存放list类型出参 )
     */
	private List listValue;
    
    /**
     * 缓存类型（1:JVM 2:redis)
     */
    private String cacheType;
    
    /**
     * 缓存数据类型（1:String 2:Map 3:List)
     */
    private String valueType;

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public Map<String,String> getMapValue() {
		return mapValue;
	}

	public void setMapValue(Map<String,String> mapValue) {
		this.mapValue = mapValue;
	}

	public List getListValue() {
		return listValue;
	}

	public void setListValue(List listValue) {
		this.listValue = listValue;
	}

	public String getCacheType() {
		return cacheType;
	}

	public void setCacheType(String cacheType) {
		this.cacheType = cacheType;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public Object getObjectValue() {
		return objectValue;
	}

	public void setObjectValue(Object objectValue) {
		this.objectValue = objectValue;
	} 
}
