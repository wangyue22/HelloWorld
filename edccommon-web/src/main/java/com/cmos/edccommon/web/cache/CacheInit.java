package com.cmos.edccommon.web.cache;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cmos.common.exception.GeneralException;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.serviceswitch.ServiceSwitchDO;
import com.cmos.edccommon.iservice.serviceswitch.IServiceSwitchSV;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 缓存初始化
 * 向初始化的方法里面提供数据
 * @author renld
 */
@Component
@SuppressWarnings({"unchecked", "rawtypes"})
public class CacheInit implements ICacheInit,CommandLineRunner{
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CacheInit.class);
	
    @Autowired
    private CacheFatctoryUtil cacheFatctoryUtil;

	@Reference
    private IServiceSwitchSV serviceSwitchSV;
			
	@Override
	public void run(String... args) throws Exception {
        System.out.println("缓存数据初始化");
        Map<String, String> jvmStringData = new HashMap<String, String>();
        Map<String, Map<String, String>> jvmMapData = new HashMap<String, Map<String,String>>();
        Map<String, List> jvmListData = new HashMap<String, List>();
        Map<String, String> redisStringData = new HashMap<String, String>();
        Map<String, Map<String, String>> redisMapData = new HashMap<String, Map<String,String>>();
        Map<String, List> redisListData = new HashMap<String, List>();

        //获取需要在JVM中初始化的String类型数据                    
        jvmStringData = this.getJvmStringCacheDate();
        
        //获取需要在JVM中初始化的Map类型数据                    
        jvmMapData = this.getJvmMapCacheDate();

        //获取需要在JVM中初始化的List类型数据                    
        jvmListData = this.getJvmListCacheDate();
        
        //获取需要在redis中初始化的String类型数据                    
        redisStringData = this.getRedisStringCacheDate();
        
        //获取需要在JVM中初始化的Map类型数据                    
        redisMapData = this.getRedisMapCacheDate();
        
        //获取需要在JVM中初始化的List类型数据                    
        redisListData = this.getRedisListCacheDate();
                     
        try {
        	cacheFatctoryUtil.putJVMStringData(jvmStringData);
        } catch (GeneralException e) {
            e.printStackTrace();
        }      
        System.out.println("缓存数据初始化over");
    }
	
	@Override
	public Map<String, String> getJvmStringCacheDate() {
		Map all = new HashMap();
        all.put("pushType","");
        Map<String, String> StringData = new HashMap<String, String>();
        
        //获取t_op_service_switch表的初始化数据
        List<ServiceSwitchDO> allList = serviceSwitchSV.selectByType(all);
        for(int i=0;i<allList.size();i++){
        	if(allList.get(i).getCacheTypeCd()!=null){
                String value = allList.get(i).getSwtchVal();
                String key = allList.get(i).getSwtchKey();
                StringData.put(key, value);            
        	}
        }
        
        //获取****表的初始化数据
                    
        return StringData;
	}

	@Override
	public Map<String, String> getRedisStringCacheDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Map<String, String>> getJvmMapCacheDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Map<String, String>> getRedisMapCacheDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List> getJvmListCacheDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List> getRedisListCacheDate() {
		// TODO Auto-generated method stub
		return null;
	}
	
}