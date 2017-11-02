package com.cmos.edccommon.web.cache;

import com.cmos.common.exception.GeneralException;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 缓存工具类
 * @author renld
 *
 */
@Component
@SuppressWarnings("rawtypes")
public class CacheFatctoryUtil {
    private static final Logger logger = LoggerFactory.getLogger(CacheFatctoryUtil.class);
    @Autowired
    private RedisCacheDataUtil redisCacheDataUtil;

    /**
     *
     * @param Key 从JVM缓存中获取数据
     * @return String 缓存数据
     * @throws GeneralException
     */
    public String getJVMString(String cacheKey) throws GeneralException {
        String resultString="";
        String key = cacheKey;
        try{
            resultString = JVMCacheDataUtil.getStringCache(key);
        }catch(Exception e){
            logger.error("缓存获取异常");
            throw new GeneralException(e.getMessage());
        }
        return resultString;
    }
      
    /**
    *
    * @param Key 从redis缓存中获取数据
    * @return String 缓存数据
    * @throws GeneralException
    */
    public String getRedisString(String cacheKey) throws GeneralException {
       String resultString="";
       try{
           resultString = redisCacheDataUtil.getStringCache(cacheKey);
       }catch(Exception e){
           logger.error("缓存获取异常");
           throw new GeneralException(e.getMessage());
       }
       return resultString;
    }
     
    /**
    *
    * @param Key 从redis缓存中获取数据
    * @return Map 缓存数据
    * @throws GeneralException
    */
    public Map getRedisMap(String cacheKey) throws GeneralException {
  	   Map resultMap=new HashMap();
       try{
    	   resultMap = redisCacheDataUtil.getMapCache(cacheKey);
       }catch(Exception e){
           logger.error("缓存获取异常");
           throw new GeneralException(e.getMessage());
       }
       return resultMap;
    }
   
    /**
    *
    * @param Key 从JVM缓存中获取数据
    * @return Map 缓存数据
    * @throws GeneralException
    */
	public Map getJVMMap(String cacheKey) throws GeneralException {
  	   Map resultMap=new HashMap();
       try{
    	   resultMap = JVMCacheDataUtil.getMapCache(cacheKey);
       }catch(Exception e){
           logger.error("缓存获取异常");
           throw new GeneralException(e.getMessage());
       }
       return resultMap;
    }
   
   /**
    *
    * @param Key 从Redis缓存中获取数据
    * @return List 缓存数据
    * @throws GeneralException
    */
	public List getRedisList(String cacheKey) throws GeneralException {
	    List resultList = new ArrayList();
        try{
        	resultList = redisCacheDataUtil.getListCache(cacheKey);
        }catch(Exception e){
            logger.error("缓存获取异常");
            throw new GeneralException(e.getMessage());
        }
        return resultList;
    }
	
   /**
    *
    * @param Key 从JVM缓存中获取数据
    * @return List 缓存数据
    * @throws GeneralException
    */
	public List getJVMList(String cacheKey) throws GeneralException {
	    List resultList = new ArrayList();
        try{
        	resultList = JVMCacheDataUtil.getListCache(cacheKey);
        }catch(Exception e){
            logger.error("缓存获取异常");
            throw new GeneralException(e.getMessage());
        }
        return resultList;
    }	
	
    /**
    * @param inMap 将string对象存入JVM缓存,数据格式为{key ,value}
    * @return boolean  缓存数据
    * @throws GeneralException
    */
    public boolean putJVMStringData( Map<String,String> inMap) throws GeneralException {
        return JVMCacheDataUtil.putStringCache(inMap);
    }
    
    /**
    * @param inMap 将string对象存入redis缓存,数据格式为{key ,value}
    * @return Map  缓存数据
    * @throws GeneralException
    */
    public boolean putRedisStringData( Map<String,String> inMap) throws GeneralException {
        return redisCacheDataUtil.putStringCache(inMap);
    }
    
    /**
    * @param inMap 将map 存入JVM缓存 数据格式为{key ,value} 
    * @return boolean
    * @throws GeneralException
    */
    public boolean putJVMMapData(Map<String,Map<String,String>> inMap) throws GeneralException {
        return JVMCacheDataUtil.putMapCache(inMap);
    }
    
    /**
    * @param inMap 将map 存入Redis缓存 数据格式为{key ,value} 
    * @return boolean
    * @throws GeneralException
    */
    public boolean putRedisMapData(Map<String,Map<String,String>> inMap) throws GeneralException {
        return redisCacheDataUtil.putMapCache(inMap);
    }
    
    /**
    * @param inMap 将List存入JVM缓存 数据格式为{key ,value} 
    * @return boolean
    * @throws GeneralException
    */
    public boolean putJVMListData(Map<String,List> inMap) throws GeneralException {
        return JVMCacheDataUtil.putListCache(inMap);
    }
    
    /**
    * @param inMap 将List存入Redis缓存 数据格式为{key ,value} 
    * @return boolean
    * @throws GeneralException
    */
    public boolean putRedisListData(Map<String,List> inMap) throws GeneralException {
        return redisCacheDataUtil.putListCache(inMap);
    }
}
