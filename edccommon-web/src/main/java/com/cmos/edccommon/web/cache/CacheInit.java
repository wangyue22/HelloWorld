package com.cmos.edccommon.web.cache;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cmos.common.exception.GeneralException;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.crkey.CoRsaKeyDO;
import com.cmos.edccommon.beans.realityAccount.TOpRealityAccountDO;
import com.cmos.edccommon.beans.rnfsCfg.TOpRnfsCfgDO;
import com.cmos.edccommon.beans.serviceswitch.ServiceSwitchDO;
import com.cmos.edccommon.iservice.crkey.IKeyInfoSV;
import com.cmos.edccommon.iservice.realityAccount.ITOpRealityAccountSV;
import com.cmos.edccommon.iservice.rnfsCfg.ITOpRnfsCfgSV;
import com.cmos.edccommon.iservice.serviceswitch.IServiceSwitchSV;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

	@Reference(group = "edcco")
    private IServiceSwitchSV serviceSwitchSV;
	
	@Reference(group = "edcco")
	private ITOpRnfsCfgSV  opRnfsCfgSV;
	
	@Reference(group = "edcco")
	private ITOpRealityAccountSV opRealityAccountSV;
	
	@Reference(group = "edcco")
	private IKeyInfoSV keyInfoSV;
				
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
        	cacheFatctoryUtil.putJVMMapData(jvmMapData);
        	cacheFatctoryUtil.putJVMListData(jvmListData);
        } catch (GeneralException e) {
            e.printStackTrace();
        }      
        System.out.println("缓存数据初始化over");
    }
	
	@Override
	public Map<String, String> getJvmStringCacheDate() {
		Map all = new HashMap();
        all.put("cacheTypeCd","");
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
        //获取Rnfs配置表的初始化数据
        String cacheTypeCd = "";
        String cacheDataTypeCd = "";
        Map<String, String> rnfsData = new HashMap<String, String>(); 
        Map<String, Map<String, String>> returnMap = new HashMap<String, Map<String, String>>(); 
        List<TOpRnfsCfgDO> rnfsList = opRnfsCfgSV.getRnfsGrpNmByType(cacheTypeCd, cacheDataTypeCd);
        for(int i=0;i<rnfsList.size();i++){
        	String key2 = "";
        	rnfsData.put("RNFS_GRP_NM", rnfsList.get(i).getRnfsGrpNm());        	
        	rnfsData.put("ROOT_PATH", rnfsList.get(i).getRootPath()); 
        	rnfsData.put("RNFS_ADDR_PRTNUM", rnfsList.get(i).getRnfsAddrPrtnum());
        	rnfsData.put("UPLOAD_DWNLD_MODE_CD", rnfsList.get(i).getUploadDwnldModeCd());
        	rnfsData.put("FTP_PRTNUM", rnfsList.get(i).getFtpPrtnum());
        	rnfsData.put("FTP_USER_NM", rnfsList.get(i).getFtpUserNm());
        	rnfsData.put("FTP_USER_PW", rnfsList.get(i).getFtpUserPw());
        	rnfsData.put("STS_CD", rnfsList.get(i).getStsCd());
        	rnfsData.put("FTP_ALS", rnfsList.get(i).getFtpAls());
        	returnMap.put(key2, rnfsData);	
        }     
        
        //获取实名账户表的初始化数据
        Map<String, String> realityData = new HashMap<String, String>();
        List<TOpRealityAccountDO> realityAccountList = opRealityAccountSV.getRealityAccountByType(cacheTypeCd, cacheDataTypeCd);
        for(int i=0;i<realityAccountList.size();i++){
        	String key = "";
        	realityData.put("USER_NM", realityAccountList.get(i).getUserNm());
        	realityData.put("PW", realityAccountList.get(i).getPw());
        	realityData.put("AES_KEY", realityAccountList.get(i).getAesKey());
        	realityData.put("DES_KEY", realityAccountList.get(i).getDesKey());
        	returnMap.put(key, realityData);
        }

        //获取rsa密钥表的初始化数据
        Map<String, String> coRsaKeyData = new HashMap<String, String>();
        List<CoRsaKeyDO> coRsaKeylist = keyInfoSV.getKeyByType(cacheTypeCd, cacheDataTypeCd);
        for(int i=0;i<coRsaKeylist.size();i++){
        	String key = "";
        	coRsaKeyData.put("PBKEY", coRsaKeylist.get(i).getPbkey());
        	coRsaKeyData.put("PRTKEY", coRsaKeylist.get(i).getPrtkey());
        	returnMap.put(key, coRsaKeyData);
        }
		return returnMap;
	}

	@Override
	public Map<String, Map<String, String>> getRedisMapCacheDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List> getJvmListCacheDate() {
        String cacheTypeCd = "";
        String cacheDataTypeCd = "";
    	String key1 = "";
        Map returnMap = new HashMap<String, List>();
        List returnList = new ArrayList();
        List<List<TOpRnfsCfgDO>> rnfsList = opRnfsCfgSV.getRnfsGrpNmByrnfsGrpNm(cacheTypeCd, cacheDataTypeCd);
        for(int i=0;i<rnfsList.size();i++){
        	List<TOpRnfsCfgDO> rnfsData = rnfsList.get(i);        	
            for(int j=0;j<rnfsData.size();j++){
            	
                Map<String, String> rnfsDataMap = new HashMap<String, String>(); 
                rnfsDataMap.put("RNFS_GRP_NM", rnfsData.get(j).getRnfsGrpNm());        	
                rnfsDataMap.put("ROOT_PATH", rnfsData.get(j).getRootPath()); 
                rnfsDataMap.put("RNFS_ADDR_PRTNUM", rnfsData.get(j).getRnfsAddrPrtnum());
                rnfsDataMap.put("UPLOAD_DWNLD_MODE_CD", rnfsData.get(j).getUploadDwnldModeCd());
                rnfsDataMap.put("FTP_PRTNUM", rnfsData.get(j).getFtpPrtnum());
                rnfsDataMap.put("FTP_USER_NM", rnfsData.get(j).getFtpUserNm());
                rnfsDataMap.put("FTP_USER_PW", rnfsData.get(j).getFtpUserPw());
                rnfsDataMap.put("STS_CD", rnfsData.get(j).getStsCd());
                rnfsDataMap.put("FTP_ALS", rnfsData.get(j).getFtpAls());
                returnList.add(rnfsDataMap);
            }
            returnMap.put(key1, returnList);
        }	
        return returnMap;
	}

	@Override
	public Map<String, List> getRedisListCacheDate() {
		// TODO Auto-generated method stub
		return null;
	}
	
}