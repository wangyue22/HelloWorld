package com.cmos.edccommon.web.cache;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.cmos.common.exception.GeneralException;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.crkey.CoRsaKeyDO;
import com.cmos.edccommon.beans.crkey.RsaKeyInDTO;
import com.cmos.edccommon.beans.realityAccount.RealityAccountInDTO;
import com.cmos.edccommon.beans.realityAccount.TOpRealityAccountDO;
import com.cmos.edccommon.beans.rnfsCfg.TOpRnfsCfgDO;
import com.cmos.edccommon.beans.serviceSwitch.ServiceSwitchDO;
import com.cmos.edccommon.iservice.IServiceSwitchSV;
import com.cmos.edccommon.iservice.crkey.IKeyInfoSV;
import com.cmos.edccommon.iservice.realityAccount.ITOpRealityAccountSV;
import com.cmos.edccommon.iservice.rnfsCfg.ITOpRnfsCfgSV;
import com.cmos.edccommon.utils.BeanUtil;

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

	@Reference(group = "edcco")
    private IServiceSwitchSV serviceSwitchSV;
	
	@Reference(group = "edcco")
	private ITOpRnfsCfgSV  opRnfsCfgSV;
	
	@Reference(group = "edcco")
	private ITOpRealityAccountSV opRealityAccountSV;
	
	@Reference(group = "edcco")
	private IKeyInfoSV keyInfoSV;
    /**
    *
    * @param Key ，type 解析key值，并根据key值从数据库中捞取数据（type是数据类型，acms，开关表是String类型的，rnsf表有Map，和List类型）
    * @return String 表名字
    */
    public String getStringFromDB (String key) throws GeneralException{
    	 if(StringUtils.isEmpty(key)){
    		 logger.error("传入key值不能为空");
    		 return "";
         }
    	 int start = key.indexOf("_") + 1;//取出首个下划线位置
    	 int end = key.indexOf(":");//取出首个冒号为止
    	 String table = key.substring(start,end);//取出表的名字
    	 try{
             switch(table){
	  	         case "SWITCH" :
	  			     ServiceSwitchDO swtichDo = serviceSwitchSV.getServiceSwitchByKey(key);
	  			     if(null != swtichDo){
		  				 return swtichDo.getSwtchVal();
	  			     }else{
	  			    	 return "";
	  			     }
	  			 default:
	  	    		 logger.error("传入key不符合规范");
	  	        	 return "";
	         } 
    	 }catch(Exception e){
    		 logger.error("从DB中取值异常",e);
    		 return "";
    	 }    	        
    }
    
    /**
    *
    * @param Key ，type 解析key值，并根据key值从数据库中捞取数据（type是数据类型，acms，开关表是String类型的，rnsf表有Map，和List类型）
    * @return String 表名字
    */
    public Map getMapFromDB (String key) throws GeneralException{
    	Map<String, String> rnfsData = new HashMap<String, String>(); 

    	if(StringUtils.isEmpty(key)){
    		logger.error("传入key值不能为空");
	        return rnfsData;
        }
    	int start = key.indexOf("_") + 1;//取出首个下划线位置
    	int end = key.indexOf(":");//取出首个冒号位置
    	String table = key.substring(start,end);//取出表的名字
    	   	 
        try{
            switch(table){
	  	        case "RNFS" :
	  	            TOpRnfsCfgDO opRnfsDo = opRnfsCfgSV.getRnfsGrpNmByAlsCacheKeyVal(key);
	  	            if(null != opRnfsDo){
	  	                rnfsData.put("rnfsGrpNm", opRnfsDo.getRnfsGrpNm());        	
		  	        	rnfsData.put("rootPath", opRnfsDo.getRootPath()); 
		  	        	rnfsData.put("rnfsAddrPrtnum", opRnfsDo.getRnfsAddrPrtnum());
		  	        	rnfsData.put("uploadDwnldModeCd", opRnfsDo.getUploadDwnldModeCd());
		  	        	rnfsData.put("ftpPrtnum", opRnfsDo.getFtpPrtnum());
		  	        	rnfsData.put("ftpUserNm", opRnfsDo.getFtpUserNm());
		  	        	rnfsData.put("ftpUserPw", opRnfsDo.getFtpUserPw());
		  	        	rnfsData.put("ftpAls", opRnfsDo.getFtpAls());
	  	            }
	  	        case "REALACC" :
                    TOpRealityAccountDO realityDto = opRealityAccountSV.getRealityAccountBycacheKey(key);
	  	        	if(null != realityDto){
	  	        		rnfsData.put("userNm", realityDto.getUserNm());
	  	        		rnfsData.put("pw", realityDto.getPw());
	  	        		rnfsData.put("aesKey", realityDto.getAesKey());
	  	        		rnfsData.put("desKey", realityDto.getDesKey());
	  	        	}
	  	        	return rnfsData;
	  	        case "RSAKEY" :
                    CoRsaKeyDO rsaDto = keyInfoSV.getKeyByCacheKey(key);
	  	        	if(null != rsaDto){
	  	        		rnfsData.put("pbkey", rsaDto.getPbkey());
	  	        		rnfsData.put("prtkey", rsaDto.getPrtkey());
	  	        	}
	  	        	return rnfsData;
	  			default:
	  	    		logger.error("传入key不符合规范");
	  	        	return rnfsData;
            }
        }catch(Exception e){
    		logger.error("从DB中取值异常",e);
    		return rnfsData;
        }
    }
    
    
    /**
    *
    * @param Key ，type 解析key值，并根据key值从数据库中捞取数据（type是数据类型，acms，开关表是String类型的，rnsf表有Map，和List类型）
    * @return List 表名字
    */
    @SuppressWarnings("unchecked")
	public List getListFromDB(String key) throws GeneralException{
        List resultList = new ArrayList();
        if(StringUtils.isEmpty(key)){
    		logger.error("传入key值不能为空");
    		return resultList;
        }
    	int start = key.indexOf("_") + 1;//取出首个下划线位置
    	int end = key.indexOf(":");//取出首个冒号为止
    	String table = key.substring(start,end);//取出表的名字 	   	    	 
        try{
    	    switch(table){
	            case "RNFS" :
	        	    List<TOpRnfsCfgDO> opRnfsDoList = opRnfsCfgSV.getRnfsGrpNmByGrpCacheKeyVal(key);
	        	    for(int i=0;i<opRnfsDoList.size();i++){
	                    //将bean转成map用于后面方法的逻辑调用
	                    Map<String, String> map = BeanUtil.convertBean(opRnfsDoList.get(i));
	                    resultList.add(map);
	        	    }
	       	    return resultList;
			    default:
	  	   			logger.error("传入key不符合规范");
	  	   		    return resultList;
            }	 
        }catch(Exception e){
        	logger.error("从DB中取值异常",e);
    		return resultList;
        }
           
    }
    
    
    /**
     *
     * @param Key 从JVM缓存中获取数据
     * @return String 缓存数据
     * @throws GeneralException
     */
    public String getJVMString(String cacheKey) throws GeneralException {
        String resultString="";
        if(StringUtils.isEmpty(cacheKey)){
        	throw new GeneralException("传入值不可为空");
        }
        
        try{
            resultString = JVMCacheDataUtil.getStringCache(cacheKey);
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        
        if(StringUtils.isEmpty(resultString)){//取值为空的时候从DB中获取数据
        	resultString = this.getStringFromDB(cacheKey);
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
           logger.error(e.getMessage());
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
           logger.error("缓存获取异常",e);
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
           logger.error("缓存获取异常",e);
       }
       
       if(resultMap == null){//取值为空的时候从DB中获取数据
    	   resultMap = this.getMapFromDB(cacheKey);
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
            logger.error("缓存获取异常",e);
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
            logger.error("缓存获取异常",e);
        }
        
        if(null == resultList || resultList.size() == 0){//取值为空的时候从DB中获取数据
        	resultList = this.getListFromDB(cacheKey);
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
