package com.cmos.edccommon.web.serviceSwitch;

import com.cmos.cache.service.ICacheService;
import com.cmos.common.exception.GeneralException;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by 66408 on 2017/10/19.
 */
@RestController
@Aspect
public class RedisCacheDataUtil {
    private static final Logger logger = LoggerFactory.getLogger(RedisCacheDataUtil.class);
    @Autowired
    private ICacheService cacheService;
    public String getStringCache(String key) throws GeneralException {
        String re=null;
        try {
             re = cacheService.getString(key);
        }catch(Exception e){
            logger.error("缓存查询异常");
            throw new  GeneralException("缓存查询异常");
        }
        if(re==null) {
            re="缓存数据不存在";
        }
        return re;
    }
    public Map getMapCache(String key) throws GeneralException {
        Map reMap=new HashMap();
        try {
            reMap = cacheService.getMap(key);
        }catch(Exception e){
            logger.error("缓存查询异常");
            throw new  GeneralException("缓存查询异常");
        }
        return reMap;
    }
    public boolean  putStringCache(Map<String, Object> dataMap) throws GeneralException {
        boolean result = false;
        for(Iterator iterator = dataMap.entrySet().iterator(); iterator.hasNext();)
        {
            Map.Entry entry = (Map.Entry)iterator.next();
            Map<String,String> map = new HashMap<String,String>();
            map.put(entry.getKey().toString(),entry.getValue().toString());
            try{
                result = cacheService.setString(entry.getKey().toString(),entry.getValue().toString());
            }catch(Exception e){
            logger.error("缓存操作异常");
            throw new  GeneralException("缓存操作异常");
            }
        }
    return  result;
    }
    public boolean  putMapCache(Map<String,Map<String,String>> dataMap) throws GeneralException {
        boolean result = false;
        for(Iterator iterator = dataMap.entrySet().iterator(); iterator.hasNext();)
        {
            Map.Entry entry = (Map.Entry)iterator.next();
            Map<String,String> m =(Map<String,String>) entry.getValue();
            Map<String,String> a;
            try{
                cacheService.putMap(entry.getKey().toString(),m);
                a = cacheService.getMap(entry.getKey().toString());
            }catch(Exception e){
                logger.error("缓存操作异常");
                throw new  GeneralException("缓存操作异常");
            }
            if(transMapToString(m).equals(transMapToString(a))){
                result=true;
            }else{
                logger.error("缓存操作异常");
            }
        }
        return  result;
    }
    public static String transMapToString(Map map){
        StringBuffer sb = new StringBuffer();
        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)
        {
            Map.Entry entry = (Map.Entry)iterator.next();
            sb.append(entry.getKey().toString()).append( "'").append(null==entry.getValue()?"":
                    entry.getValue().toString()).append(iterator.hasNext()?"^":"");
        }
        return sb.toString();
    }
}
