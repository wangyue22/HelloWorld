package com.cmos.edccommon.web.cache;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cmos.cache.service.ICacheService;
import com.cmos.common.spring.AppContext;

@Controller
public class CacheServiceManager {

    private static CacheServiceManager cacheServiceClient = null;
    @Autowired
    private ICacheService cacheService;

    //私有化，不能new
    private CacheServiceManager() {
    }

    //获取实例对象
    public static CacheServiceManager getCacheService() {
        if (cacheServiceClient == null) {
            synchronized (CacheServiceManager.class) {
                cacheServiceClient = AppContext.getBean(CacheServiceManager.class);
               //cacheServiceClient = (CacheServiceManager) SpringContextUtils.getBeanById("cacheServiceManager");
            }
        }
        return cacheServiceClient;
    }


    //将String放入缓存，不设置失效时间
    public boolean put2Cache(String cacheKey, String value) {
        return cacheService.setString(cacheKey, value);
    }

    //将String放入缓存，可以设置失效时间
    public boolean put2Cache(String cacheKey, String value, int seconds) {
        return cacheService.setex(cacheKey, value, seconds);
    }

    //从缓存中获取字符串
    public String getFromCache(String cacheKey) {
        return cacheService.getString(cacheKey);
    }

    //自增序列
    public long incr(String key) {
        return cacheService.incr(key);
    }

    //自减序列
    public long decr(String key) {
        return cacheService.decr(key);
    }

    //将map放入缓存
    public String putMap(String key, Map<String, String> map) {
        //System.out.println("putMap  key="+key +"   value="+JsonUtil.convertObject2Json(map));
        return cacheService.putMap(key, map);
    }

    //从缓存中获取map对象；
    public Map<String, String> getMap(String key) {
        return cacheService.getMap(key);
    }

    //将无序集合存入缓存；
    public Long putAdd(String key, String... value) {
        return cacheService.sadd(key, value);
    }

    //从缓存中读出无序集合；
    public Set<String> getAdd(String key) {
        return cacheService.smembers(key);
    }

    //将字符数组存入缓存
    public Long lpush(String key, String... values) {
        return cacheService.lpush(key, values);
    }

    //从缓存中读出字符数组
    public Long rpush(String key, String... values) {
        return cacheService.rpush(key, values);
    }

    //返回字符串，第一个元素的值
    public String lpop(String key) {
        return cacheService.lpop(key);
    }

    //返回字符串，最后一个元素的值
    public String rpop(String key) {
        return cacheService.rpop(key);
    }

    //有序集合放入缓存
    public Long zadd(String key, double score, String member) {
        return cacheService.zadd(key, score, member);
    }

    //删除缓存内容
    public void delFromCache(String cacheKey) {
        cacheService.del(cacheKey);
    }
}