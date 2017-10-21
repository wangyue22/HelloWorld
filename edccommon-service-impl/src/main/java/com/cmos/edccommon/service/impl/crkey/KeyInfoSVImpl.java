package com.cmos.edccommon.service.impl.crkey;


import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.cache.ttl.annotation.CacheTTL;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.crkey.CoKeyDO;
import com.cmos.edccommon.beans.crkey.KeyInfoDTO;
import com.cmos.edccommon.dao.crkey.CoKeyDAO;
import com.cmos.edccommon.iservice.crkey.IKeyInfoSV;

/**
 * 根据请求源编码，秘钥类型，和省编码获取秘钥
 * @author xdx
 * 
 */
@Service(group = "edcco")
public  class KeyInfoSVImpl implements  IKeyInfoSV {
	@Autowired
	private CoKeyDAO keyDAO;
	
    Logger logger = LoggerFactory.getActionLog(KeyInfoSVImpl.class);

	/**
	 * 通过开关配置，选择是否优先从redis获取秘钥
	 */
	public CoKeyDO getKey(KeyInfoDTO inParam) {
		String cacheFlag = "false";//BsStaticDataUtil.getCodeValue("EDCCO", "GET_KEY_FROM_CACHE_CONF", "JVM");
		if("true".equalsIgnoreCase(cacheFlag)){
			String reqstSrcCode=inParam.getReqstSrcCode();
			String crkeyTypeCd=inParam.getCrkeyTypeCd();
			String bizTypeCd=inParam.getBizTypeCd();	
			CoKeyDO result = getKeyFromcache(reqstSrcCode,crkeyTypeCd,bizTypeCd);	
			logger.debug("key="+result.getCrkey());
			return result;
		}
		
		CoKeyDO result = keyDAO.selectKey(inParam);
		logger.debug(result.getCrkey()+"|"+result.getReqstSrcCode());
		return result;
	}
	
	/**
	 * 优先从redis 中获取，其次去数据库取值
	 * @param reqstSrcCode
	 * @param crkeyTypeCd
	 * @param bizTypeCd
	 * @return
	 */
	@Cacheable(value="redisCache",key="'EDCCO_KEY_CONF:'+#reqstSrcCode+'_'+#crkeyTypeCd+'_'+#bizTypeCd")
	@CacheTTL(unit= TimeUnit.DAYS, time=30)
	public CoKeyDO getKeyFromcache(String reqstSrcCode, String crkeyTypeCd,String bizTypeCd) {
		KeyInfoDTO param= new KeyInfoDTO();
		
		param.setReqstSrcCode(reqstSrcCode);
		param.setCrkeyTypeCd(crkeyTypeCd);
		param.setBizTypeCd(bizTypeCd);
		CoKeyDO result = keyDAO.selectKey(param);
		logger.debug("数据库取值");
		return result;
	}
	
	/**
	 * 优先从redis 中获取，其次去数据库取值
	 * @param reqstSrcCode
	 * @param crkeyTypeCd
	 * @param bizTypeCd
	 * @return
	 */
	@Cacheable(value="redisCache",key="'EDCCO_KEY_CONF:'+#reqstSrcCode.reqstSrcCode+'_'+#reqstSrcCode.crkeyTypeCd+'_'+#reqstSrcCode.bizTypeCd")
	@CacheTTL(unit= TimeUnit.DAYS, time=30)
	public CoKeyDO getKeyFromcache(KeyInfoDTO reqstSrcCode) {
		CoKeyDO result = keyDAO.selectKey(reqstSrcCode);
		return result;
	}
	
	
	
	
	
/*    *//**
     * 路由缓存增加
     * @param interRouter
     * @return
     *//*
    @CachePut(value="redisCache",key="'NGKM_INTER_ROUTER:'+#interRouter.intfMdlCd+'_'+#interRouter.provCode")
    @CacheTTL(unit= TimeUnit.DAYS, time=30)
    public TKmInterRouter refreshCache(TKmInterRouter interRouter){
        return interRouter;
    }

    *//**
     * 路由缓存删除
     * @param interRouter
     * @return
     *//*
    @CacheEvict(value="redisCache",key="'NGKM_INTER_ROUTER:'+#interRouter.intfMdlCd+'_'+#interRouter.provCode")
    public TKmInterRouter deleteCache(TKmInterRouter interRouter){
        return interRouter;
    }*/
	
}
