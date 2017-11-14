package com.cmos.edccommon.web.cache;

import com.cmos.cache.service.ICacheService;
import com.cmos.common.exception.GeneralException;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/**
 * 缓存工具类
 * @author renld
 */
@Component
public class RedisCacheDataUtil {
    private static final Logger logger = LoggerFactory.getLogger(RedisCacheDataUtil.class);
    @Autowired
    private ICacheService cacheService;
        
    public String getStringCache(String key) throws GeneralException {
        String re = null;
        try {
             re = cacheService.getString(key);
        }catch(Exception e){
            logger.error("缓存查询异常",e);
            throw new GeneralException(e.getMessage());
        }
        return re;
    }
    
    @SuppressWarnings("rawtypes")
	public Map getMapCache(String key) throws GeneralException {
        Map reMap = new HashMap();
        try {
            reMap = cacheService.getMap(key);
        }catch(Exception e){
            logger.error("缓存查询异常",e);
            throw new  GeneralException(e.getMessage());
        }
        return reMap;
    }
    
    @SuppressWarnings("rawtypes")
	public List getListCache(String key) throws GeneralException {
        List reList = new ArrayList();
        try {
        	reList = (List)cacheService.getObject(key);
        }catch(Exception e){
            logger.error("缓存查询异常",e);
            throw new GeneralException(e.getMessage());
        }
        return reList;
    }
    
    public Object getObjectCache(String key) throws GeneralException{
    	return cacheService.getObject(key);
    }
    
    @SuppressWarnings("rawtypes")
	public boolean putStringCache(Map<String, String> dataMap) throws GeneralException {
        boolean result = false;
        for(Iterator iterator = dataMap.entrySet().iterator(); iterator.hasNext();){
            Map.Entry entry = (Map.Entry)iterator.next();
            Map<String,String> map = new HashMap<String,String>();
            map.put(entry.getKey().toString(),entry.getValue().toString());
            try{
                result = cacheService.setString(entry.getKey().toString(),entry.getValue().toString());
            }catch(Exception e){
	            logger.error("缓存操作异常",e);
	            throw new GeneralException(e.getMessage());
            }
        }
        return result;
    }
    
    public boolean delCache(String key) throws GeneralException{
        boolean result = false;
        try{
            cacheService.del(key);
            result = true;  
        }catch(Exception e){
            logger.error("缓存清除异常",e);
            result = false;  
        }
        return result;
    }
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean putMapCache(Map<String,Map<String,String>> dataMap) throws GeneralException {
        boolean result = false;
        for(Iterator iterator = dataMap.entrySet().iterator(); iterator.hasNext();){
            Map.Entry entry = (Map.Entry)iterator.next();
            Map<String,String> value =(Map<String,String>) entry.getValue();
            try{
                cacheService.putMap(entry.getKey().toString(),value);
            }catch(Exception e){
                logger.error("缓存操作异常",e);
                throw new  GeneralException(e.getMessage());
            }
        }
        return  result;
    }
    
    @SuppressWarnings("rawtypes")
	public boolean putListCache(Map<String,List> dataMap) throws GeneralException {
        boolean result = false;
        for(Iterator iterator = dataMap.entrySet().iterator(); iterator.hasNext();){
            Map.Entry entry = (Map.Entry)iterator.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            try{
            	result = cacheService.setObject(key, value);
            }catch(Exception e){
                logger.error("缓存操作异常",e);
                throw new GeneralException(e.getMessage());
            }      
        }
        return  result;
    }
       
    @SuppressWarnings("rawtypes")
	public static String transMapToString(Map map){
    	StringBuilder sb = new StringBuilder();
        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();){
            Map.Entry entry = (Map.Entry)iterator.next();
            sb.append(entry.getKey().toString()).append( "'").append(null==entry.getValue()?"":
            entry.getValue().toString()).append(iterator.hasNext()?"^":"");
        }
        return sb.toString();
    }
}
