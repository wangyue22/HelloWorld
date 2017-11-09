package com.cmos.edccommon.web.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.crkey.RsaKeyDO;
import com.cmos.edccommon.beans.realityAccount.RealityAccountDO;
import com.cmos.edccommon.beans.rnfsCfg.RnfsCfgDO;
import com.cmos.edccommon.beans.serviceSwitch.ServiceSwitchDO;
import com.cmos.edccommon.iservice.IServiceSwitchSV;
import com.cmos.edccommon.iservice.crkey.IRsaKeySV;
import com.cmos.edccommon.iservice.realityAccount.IRealityAccountSV;
import com.cmos.edccommon.iservice.rnfsCfg.IRnfsCfgSV;
import com.cmos.edccommon.utils.BeanUtil;

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
    private IRnfsCfgSV  opRnfsCfgSV;

    @Reference(group = "edcco")
    private IRealityAccountSV opRealityAccountSV;

    @Reference(group = "edcco")
    private IRsaKeySV keyInfoSV;
    /**
     *
     * @param Key ，type 解析key值，并根据key值从数据库中捞取数据（type是数据类型，acms，开关表是String类型的，rnsf表有Map，和List类型）
     * @return String 表名字
     */
    @SuppressWarnings("unchecked")
	public String getStringFromDB (String key) {
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
	                	Map dataMap = new HashMap();
	                	String value = swtichDo.getSwtchVal();
	                	dataMap.put(key, value);
	                	JVMCacheDataUtil.putStringCache(dataMap);//将从DB中查询出的值加载到缓存中
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
    @SuppressWarnings("unchecked")
	public Map getMapFromDB (String key){
        Map<String, String> rnfsData = new HashMap<String, String>();
        if(StringUtils.isEmpty(key)){
            logger.error("传入key值不能为空");
            return rnfsData;
        }
        int start = key.indexOf("_") + 1;//取出首个下划线位置
        int end = key.indexOf(":");//取出首个冒号位置
        String table = key.substring(start,end);//取出表的名字

        try{
    		Map dataMap = new HashMap();
            switch(table){
	            case "RNFS" :
	                RnfsCfgDO opRnfsDo = opRnfsCfgSV.getRnfsGrpNmByAlsCacheKeyVal(key);
	                if(null != opRnfsDo){
	                    rnfsData.put("rnfsGrpNm", opRnfsDo.getRnfsGrpNm());
	                    rnfsData.put("rootPath", opRnfsDo.getRootPath());
	                    rnfsData.put("rnfsAddrPrtnum", opRnfsDo.getRnfsAddrPrtnum());
	                    rnfsData.put("uploadDwnldModeCd", opRnfsDo.getUploadDwnldModeCd());
	                    rnfsData.put("ftpPrtnum", opRnfsDo.getFtpPrtnum());
	                    rnfsData.put("ftpUserNm", opRnfsDo.getFtpUserNm());
	                    rnfsData.put("ftpUserPw", opRnfsDo.getFtpUserPw());
	                    rnfsData.put("ftpAls", opRnfsDo.getFtpAls());
	                	dataMap.put(key, rnfsData);
	                	JVMCacheDataUtil.putMapCache(dataMap);
	                }
	                return rnfsData;
	            case "REALACC" :
	                RealityAccountDO realityDto = opRealityAccountSV.getRealityAccountBycacheKey(key);
	                if(null != realityDto){
	                    rnfsData.put("userNm", realityDto.getUserNm());
	                    rnfsData.put("pw", realityDto.getPw());
	                    rnfsData.put("aesKey", realityDto.getAesKey());
	                    rnfsData.put("desKey", realityDto.getDesKey());
	                    dataMap.put(key, rnfsData);
	                	JVMCacheDataUtil.putMapCache(dataMap);
	                }
	                return rnfsData;
	            case "RSAKEY" :
	                RsaKeyDO rsaDto = keyInfoSV.getKeyByCacheKey(key);
	                if(null != rsaDto){
	                    rnfsData.put("pbkey", rsaDto.getPbkey());
	                    rnfsData.put("prtkey", rsaDto.getPrtkey());
	                    dataMap.put(key, rnfsData);
	                	JVMCacheDataUtil.putMapCache(dataMap);
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
    public List getListFromDB(String key){
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
                List<RnfsCfgDO> opRnfsDoList = opRnfsCfgSV.getRnfsGrpNmByGrpCacheKeyVal(key);
                for(int i=0;i<opRnfsDoList.size();i++){
                    //将bean转成map用于后面方法的逻辑调用
                    Map<String, String> map = BeanUtil.convertBean(opRnfsDoList.get(i));
                    resultList.add(map);
                }
            	Map dataMap = new HashMap();
                dataMap.put(key, resultList);
            	JVMCacheDataUtil.putMapCache(dataMap);
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
     */
    public String getJVMString(String cacheKey){
        String resultString="";
        if(StringUtils.isEmpty(cacheKey)){
            logger.error("传入key不符合规范");
            return "";
        }

        try{
            resultString = JVMCacheDataUtil.getStringCache(cacheKey);
        }catch(Exception e){
            logger.error(e.getMessage());
        }

        if(StringUtils.isEmpty(resultString)){//取值为空的时候从DB中获取数据
            resultString = getStringFromDB(cacheKey);
        }
        return resultString;
    }

    /**
     *
     * @param Key 从redis缓存中获取数据
     * @return String 缓存数据
     */
    public String getRedisString(String cacheKey){
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
     */
    public Map getRedisMap(String cacheKey){
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
     */
    public Map getJVMMap(String cacheKey){
        Map resultMap=new HashMap();
        try{
            resultMap = JVMCacheDataUtil.getMapCache(cacheKey);
        }catch(Exception e){
            logger.error("缓存获取异常",e);
        }

        if(resultMap == null){//取值为空的时候从DB中获取数据
            resultMap = getMapFromDB(cacheKey);
        }
        return resultMap;
    }

    /**
     *
     * @param Key 从Redis缓存中获取数据
     * @return List 缓存数据
     */
    public List getRedisList(String cacheKey){
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
     */
    public List getJVMList(String cacheKey){
        List resultList = new ArrayList();
        try{
            resultList = JVMCacheDataUtil.getListCache(cacheKey);
        }catch(Exception e){
            logger.error("缓存获取异常",e);
        }

        if(null == resultList || resultList.size() == 0){//取值为空的时候从DB中获取数据
            resultList = getListFromDB(cacheKey);
        }
        return resultList;
    }

    /**
     * @param inMap 将string对象存入JVM缓存,数据格式为{key ,value}
     * @return boolean  缓存数据
     */
    public boolean putJVMStringData( Map<String,String> inMap){
        try {
            return JVMCacheDataUtil.putStringCache(inMap);
        } catch (Exception e) {
            logger.error("存入缓存异常",e);
            return false;
        }
    }

    /**
     * @param inMap 将string对象存入redis缓存,数据格式为{key ,value}
     * @return Map  缓存数据
     */
    public boolean putRedisStringData( Map<String,String> inMap){
        try {
            return redisCacheDataUtil.putStringCache(inMap);
        } catch (Exception e) {
            logger.error("存入缓存异常",e);
            return false;
        }
    }

    /**
     * @param inMap 将map 存入JVM缓存 数据格式为{key ,value}
     * @return boolean
     */
    public boolean putJVMMapData(Map<String,Map<String,String>> inMap){
        try {
            return JVMCacheDataUtil.putMapCache(inMap);
        } catch (Exception e) {
            logger.error("存入缓存异常",e);
            return false;
        }
    }

    /**
     * @param inMap 将map 存入Redis缓存 数据格式为{key ,value}
     * @return boolean
     */
    public boolean putRedisMapData(Map<String,Map<String,String>> inMap){
        try {
            return redisCacheDataUtil.putMapCache(inMap);
        } catch (Exception e) {
            logger.error("存入缓存异常",e);
            return false;
        }
    }

    /**
     * @param inMap 将List存入JVM缓存 数据格式为{key ,value}
     * @return boolean
     */
    public boolean putJVMListData(Map<String,List> inMap){
        try {
            return JVMCacheDataUtil.putListCache(inMap);
        } catch (Exception e) {
            logger.error("存入缓存异常",e);
            return false;
        }
    }

    /**
     * @param inMap 将List存入Redis缓存 数据格式为{key ,value}
     * @return boolean
     */
    public boolean putRedisListData(Map<String,List> inMap){
        try {
            return redisCacheDataUtil.putListCache(inMap);
        } catch (Exception e) {
            logger.error("存入缓存异常",e);
            return false;
        }
    }
}
