package com.cmos.edccommon.service.impl.crkey;


import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.cache.ttl.annotation.CacheTTL;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.crkey.CoRsaKeyDO;
import com.cmos.edccommon.beans.crkey.KeyInfoDTO;
import com.cmos.edccommon.dao.crkey.CoRsaKeyDAO;
import com.cmos.edccommon.iservice.crkey.IKeyInfoSV;

/**
 * 根据请求源编码，秘钥类型，和省编码获取秘钥
 * @author xdx
 * 
 */
@Service(group = "edcco")
public  class KeyInfoSVImpl implements  IKeyInfoSV {
	@Autowired
	private CoRsaKeyDAO keyDAO;
	
    Logger logger = LoggerFactory.getActionLog(KeyInfoSVImpl.class);

	/**
	 * 通过开关配置，选择是否优先从redis获取秘钥
	 */
	public CoRsaKeyDO getKey(KeyInfoDTO inParam) {
		String cacheFlag = "false";//BsStaticDataUtil.getCodeValue("EDCCO", "GET_KEY_FROM_CACHE_CONF", "JVM");
		if("true".equalsIgnoreCase(cacheFlag)){
			String reqstSrcCode=inParam.getReqstSrcCode();
			String crkeyTypeCd=inParam.getCrkeyTypeCd();
			String bizTypeCd=inParam.getBizTypeCd();	
			CoRsaKeyDO result = getKeyFromcache(reqstSrcCode,crkeyTypeCd,bizTypeCd);	
			if (result != null) {
				logger.debug("key=" + result.getPrtkey());
			}
			return result;
		}
		
		CoRsaKeyDO result = keyDAO.selectKey(inParam);
		if (result != null) {
			logger.debug("key=" + result.getPrtkey());
		}
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
	public CoRsaKeyDO getKeyFromcache(String reqstSrcCode, String crkeyTypeCd,String bizTypeCd) {
		KeyInfoDTO param= new KeyInfoDTO();
		
		param.setReqstSrcCode(reqstSrcCode);
		param.setCrkeyTypeCd(crkeyTypeCd);
		param.setBizTypeCd(bizTypeCd);
		CoRsaKeyDO result = keyDAO.selectKey(param);
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
	public CoRsaKeyDO getKeyFromcache(KeyInfoDTO reqstSrcCode) {
		CoRsaKeyDO result = keyDAO.selectKey(reqstSrcCode);
		return result;
	}

}
