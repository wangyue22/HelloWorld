package com.cmos.edccommon.beans.cache;

import java.util.List;
import java.util.Map;

import com.cmos.common.bean.GenericBean;

/**
 * 操作缓存入参DTO对象
 * @ClassName:  CacheInDTO
 * @Description:TODO
 * @author: 任林达
 * @date:   2017年10月30日 上午11:25:39
 */
@SuppressWarnings("rawtypes")
public class CacheInDTO  extends GenericBean{
    private static final long serialVersionUID = 1L;
    /**
     * 缓存key
     */
    private String key;
    /**
     * 缓存value(存放string类型值 )
     */
    private String stringValue;
    
    /**
     * 缓存value(存放map类型值 )
     */
    private Map<String,String> mapValue;
    
    /**
     * 缓存value(存放list类型值 )
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

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
}