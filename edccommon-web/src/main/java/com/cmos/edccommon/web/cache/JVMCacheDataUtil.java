package com.cmos.edccommon.web.cache;

import com.cmos.common.exception.GeneralException;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

/**
 * 缓存工具类
 * @author renld
 */
@Component
@SuppressWarnings("rawtypes")
public class JVMCacheDataUtil {
    private static final Logger logger = LoggerFactory.getLogger(JVMCacheDataUtil.class);
        
	public static final Map map = new ConcurrentHashMap();
	
    public static String getStringCache(String key){
    	String value = (String) map.get(key);
        return value;
    }    
    
	public static Map getMapCache(String key){
        Map value = (Map)map.get(key);
        return value;
    }
	
    public static List getListCache(String key){
    	List value = (List)map.get(key);
        return value;
    }
    
    public static Object getObjectCache(String key){
    	return map.get(key);   
    }
    
    public static boolean delCache(String key){
    	boolean result = false;
	    try{
            map.remove(key);
            result = true;
        }catch(Exception e){
            logger.error("清除缓存异常",e);
            result=false;
        }
	    return result;
    }
    
    
    @SuppressWarnings("unchecked")
	public static boolean putStringCache(Map<String, String> dataMap) throws GeneralException {
        boolean result = false;
        for(Iterator iterator = dataMap.entrySet().iterator(); iterator.hasNext();)
        {
            Map.Entry entry = (Map.Entry)iterator.next();
            try{
	            map.put(entry.getKey(),entry.getValue());
	            result = true;
            }catch(Exception e){
        	    logger.error("加载缓存异常",e);
                result=false;
                break;
            }
        }
        return result;
    }
    
    @SuppressWarnings("unchecked")
	public static boolean putMapCache(Map<String,Map<String,String>> dataMap) throws GeneralException {
        boolean result = false;
        for(Iterator iterator = dataMap.entrySet().iterator(); iterator.hasNext();)
        {
            Map.Entry entry = (Map.Entry)iterator.next();
            try{
                map.put(entry.getKey(),entry.getValue());
                result = true;
            }catch(Exception e){
            	logger.error("加载缓存异常",e);
                result=false;
                break;
            }
        }
        return result;
    }
    
    @SuppressWarnings("unchecked")
	public static boolean putListCache(Map<String,List> dataMap) throws GeneralException {
        boolean result = false;
        for(Iterator iterator = dataMap.entrySet().iterator(); iterator.hasNext();)
        {
            Map.Entry entry = (Map.Entry)iterator.next();
            try{
                map.put(entry.getKey(),entry.getValue());
                result = true;
            }catch(Exception e){
            	logger.error("加载缓存异常",e);
                result=false;
                break;
            }
        }
        return result;
    }
}
