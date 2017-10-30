package com.cmos.edccommon.web.serviceSwitch;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cmos.common.exception.GeneralException;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.serviceswitch.ServiceSwitchDO;
import com.cmos.edccommon.iservice.serviceswitch.IServiceSwitchSV;
import com.cmos.edccommon.utils.StringUtil;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by 66408 on 2017/10/19.
 */
@RestController
@Aspect
@RequestMapping("/cacheFatctory")
public class CacheFatctoryUtil {
    private static final Logger logger = LoggerFactory.getLogger(CacheFatctoryUtil.class);
    @Autowired
    private RedisCacheDataUtil redisCacheDataUtil;
    @Reference
    private IServiceSwitchSV serviceSwitchSV;

    /**
     *
     * @param cacheKey 从缓存中获取数据需要参数 key  和cacheType  如  {"key":"111yyyyyyyyyyyyy","cacheType":"1"}
     * @return Map  缓存数据
     * @throws GeneralException
     */
    @RequestMapping(value ="/getJVMString", method = RequestMethod.POST)
    public String  getJVMString(@RequestBody String cacheKey) throws GeneralException {
        String resultString="";
        String key = cacheKey;
        try{
            resultString = JVMCacheDataUtil.getStringCache(key);
        }catch(Exception e){
            logger.error("缓存获取异常");
            throw new GeneralException("缓存获取异常",e);
        }
        if(StringUtil.isEmpty(resultString)){
            ServiceSwitchDO serviceSwitchDO = serviceSwitchSV.getServiceSwitchByKey(cacheKey);
            resultString = serviceSwitchDO.getSwtchVal();
        }

        return resultString;
    }



    /**
     *
     * @param cacheKey 从缓存中获取数据需要参数 key  和cacheType  如  {"key":"111yyyyyyyyyyyyy","cacheType":"1"}
     * @return Map  缓存数据
     * @throws GeneralException
     */
    @RequestMapping(value ="/getRedisString", method = RequestMethod.POST)
    public String  getRedisString(@RequestBody String cacheKey) throws GeneralException {
        String resultString="";
        String key = cacheKey;
        try{
            resultString = redisCacheDataUtil.getStringCache(key);
        }catch(Exception e){
            logger.error("缓存获取异常");
            throw new GeneralException("缓存获取异常",e);
        }
        if(StringUtil.isEmpty(resultString)){
            ServiceSwitchDO serviceSwitchDO = serviceSwitchSV.getServiceSwitchByKey(cacheKey);
            resultString = serviceSwitchDO.getSwtchVal();
        }
        return resultString;
    }
    /**
     *
     * @param in 从缓存中获取数据需要参数 key  和cacheType  如  {"key":"111yyyyyyyyyyyyy","cacheType":"1"}
     * @return Map  缓存数据
     * @throws GeneralException
     */
    @RequestMapping(value ="/queryStringCacheData", method = RequestMethod.POST)
    public String  getStringCacheData(@RequestBody Map in) throws GeneralException {
        String resultString="";
        if(in.get("key")==null||("").equals(in.get("key").toString())){
            throw new GeneralException("请传入缓存所对应的key值");
        }
        String key = in.get("key").toString();
        /**
         * 通过前台获取推送方式，1标识存放到JVM中，2为存放到redis中
         */
        String type = in.get("cacheType").toString();
        try {
            switch (type) {
                case "1":
                    resultString = JVMCacheDataUtil.getStringCache(key);
                    break;
                case "2":
                    resultString = redisCacheDataUtil.getStringCache(key);
                    break;
            }
        }catch(Exception e){
            logger.error("缓存获取异常");
            throw new GeneralException("缓存获取异常",e);
        }
        return resultString;
    }
    /**
     *
     * @param in 从缓存中获取数据需要参数 key  和cacheType {"key":"111yyyyyyyyyyyyy","cacheType":"1"}
     * @return Map  缓存数据
     * @throws GeneralException
     */
    @RequestMapping(value ="/queryMapCacheData", method = RequestMethod.POST)
    public Map  getMapCacheData(@RequestBody Map in) throws GeneralException {
        Map resultMap=new HashMap();
        if(in.get("key")==null||("").equals(in.get("key").toString())){
            throw new GeneralException("请传入缓存所对应的key值");
        }
        String key = in.get("key").toString();
        /**
         * 通过前台获取推送方式，1标识存放到JVM中，2为存放到redis中
         */
        String type = in.get("cacheType").toString();
        try{
        switch (type){
            case "1" : resultMap = JVMCacheDataUtil.getMapCache(key) ;
            break;
            case "2" : resultMap = redisCacheDataUtil.getMapCache(key);
            break;
        }
         }catch(Exception e){
        logger.error("缓存获取异常");
        throw new GeneralException("缓存获取异常",e);
    }
        return resultMap;
    }
    /**
     *
     * @param in 将string对象存入缓存,数据格式为{"放置缓存时的key";{"cacheType":"1","value":"存入缓存的String串"}}
     * @return Map  缓存数据
     * @throws GeneralException
     */
    @RequestMapping(value ="/putStringCacheData", method = RequestMethod.POST)
    public boolean putStringCacheData( @RequestBody Map<String,Map<String,String>> in) throws GeneralException {
        boolean result = false;
        for(Iterator iterator = in.entrySet().iterator(); iterator.hasNext();){
            Map.Entry<String,Map<String,String>> entry = (Map.Entry)iterator.next();
            String value= entry.getValue().get("value");
            if(value==null||("").equals(value)){
                throw new GeneralException("存入数据为空");
            }
            String pushType = entry.getValue().get("cacheType");
            if(pushType==null||("").equals(pushType)){
                throw new GeneralException("存取位置为空");
            }
            Map<String,Object> inMap = new HashMap<String,Object>();
            inMap.put(entry.getKey(),entry.getValue().get("value"));
            try{
                switch (pushType){
                    case "1" : result = JVMCacheDataUtil.putStringCache(inMap) ;
                    break;
                    case "2" : result = redisCacheDataUtil.putStringCache(inMap);
                    break;
                }
            }catch(Exception e){
            logger.error("存储缓存异常");
            throw new GeneralException("存储缓存异常",e);
            }
        }
        return result;
    }
    /**
     *
     * @param in  将map 存入缓存{"放置缓存时的key";{"cacheType":"1","value":{存入缓存的map}}}
     * @return
     * @throws GeneralException
     */
    @RequestMapping(value ="/putMapCacheData", method = RequestMethod.POST)
    public boolean putMapCacheData(@RequestBody Map<String,Map<String,Object>> in) throws GeneralException {
        boolean result = false;
        for(Iterator iterator = in.entrySet().iterator(); iterator.hasNext();){
            Map.Entry<String,Map<String,Object>> entry = (Map.Entry)iterator.next();
            Map<String,String> value= (Map<String,String>)entry.getValue().get("value");
            if(value==null){
                throw new GeneralException("存入数据为空");
            }
            String pushType = entry.getValue().get("cacheType").toString();
            if(pushType==null||("").equals(pushType)){
                throw new GeneralException("存取位置为空");
            }
            Map<String,Map<String,String>> inMap = new HashMap<String,Map<String,String>>();
            inMap.put(entry.getKey(),value);
            try{
            switch (pushType){
                case "1" : result = JVMCacheDataUtil.putMapCache(inMap) ;
                break;
                case "2" : result = redisCacheDataUtil.putMapCache(inMap);
                break;
            }
        }catch(Exception e){
            logger.error("存储缓存异常");
            throw new GeneralException("存储缓存异常",e);
        }
        }
        return result;
    }


}
