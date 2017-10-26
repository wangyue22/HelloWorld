package com.cmos.edccommon.web.serviceSwitch;

import com.cmos.common.exception.GeneralException;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by 66408 on 2017/10/19.
 */
@RestController
@Aspect
public class JVMCacheDataUtil {
    private static final Logger logger = LoggerFactory.getLogger(JVMCacheDataUtil.class);
    public static Map map = new HashMap();
    public static String getStringCache(String key){
        Object  value = map.get(key);
            if(value==null){
                value = "缓存数据不存在";
                return value.toString();
        }

        return value.toString();
    }
    public static Map getMapCache(String key){
        Map  value = (Map)map.get(key);
        return value;
    }
    @SuppressWarnings("rawtypes")
	public static boolean putStringCache(Map<String, Object> dataMap) throws GeneralException {
        boolean result = false;
        for(Iterator iterator = dataMap.entrySet().iterator(); iterator.hasNext();)
        {
            Map.Entry entry = (Map.Entry)iterator.next();
            try{
            map.put(entry.getKey(),entry.getValue());
            result = true;
            }catch(Exception e){
                logger.error("缓存操作异常");
                result=false;
            }
        }
        return result;
    }
    public static boolean putMapCache(Map<String,Map<String,String>> dataMap) throws GeneralException {
        boolean result = false;
        for(Iterator iterator = dataMap.entrySet().iterator(); iterator.hasNext();)
        {
            Map.Entry entry = (Map.Entry)iterator.next();
            try{
                map.put(entry.getKey(),entry.getValue());
                result = true;
            }catch(Exception e){
                logger.error("缓存操作异常");
                result=false;
            }
        }
        return result;
    }
}
