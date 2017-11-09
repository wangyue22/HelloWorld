package com.cmos.edccommon.web.controller;

import io.swagger.annotations.ApiOperation;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.cache.CacheInDTO;
import com.cmos.edccommon.beans.cache.CacheOutDTO;
import com.cmos.edccommon.beans.crkey.RsaKeyDO;
import com.cmos.edccommon.beans.crkey.RsaKeyInDTO;
import com.cmos.edccommon.beans.crkey.RsaKeyOutDTO;
import com.cmos.edccommon.beans.realityAccount.RealityAccountDO;
import com.cmos.edccommon.beans.realityAccount.RealityAccountInDTO;
import com.cmos.edccommon.beans.realityAccount.RealityAccountOutDTO;
import com.cmos.edccommon.beans.rnfsCfg.RnfsCfgDO;
import com.cmos.edccommon.beans.rnfsCfg.RnfsCfgInDTO;
import com.cmos.edccommon.beans.rnfsCfg.RnfsCfgOutDTO;
import com.cmos.edccommon.beans.serviceSwitch.ServiceSwitchDO;
import com.cmos.edccommon.beans.serviceSwitch.ServiceSwitchInDTO;
import com.cmos.edccommon.beans.serviceSwitch.ServiceSwitchOutDTO;
import com.cmos.edccommon.iservice.IServiceSwitchSV;
import com.cmos.edccommon.iservice.crkey.IRsaKeySV;
import com.cmos.edccommon.iservice.realityAccount.IRealityAccountSV;
import com.cmos.edccommon.iservice.rnfsCfg.IRnfsCfgSV;
import com.cmos.edccommon.web.cache.JVMCacheDataUtil;
import com.cmos.edccommon.web.cache.RedisCacheDataUtil;

/**
 * @author 任林达
 */

@RestController
public class CacheManageController{
    private Logger log = LoggerFactory.getActionLog(CacheManageController.class);
    /**
     * 实现接口后各业务系统实现以下方法：
     * 1、实现缓存的增删改查方法
     * 2、实现所有模块db的增删改查方法，如果本业务系统没有对应的模块请返回默认错误
     */
    @Autowired
    private RedisCacheDataUtil redisCacheDataUtil;

    @Autowired
    private JVMCacheDataUtil jvmCacheDataUtil;

    @Reference(group = "edcco")
    private IServiceSwitchSV serviceSwitch;

    @Reference(group = "edcco")
    private IRnfsCfgSV rnfsCfg;

    @Reference(group = "edcco")
    private IRealityAccountSV realityAccount;

    @Reference(group = "edcco")
    private IRsaKeySV rsaKey;
    /**
     * 缓存增加或者更新
     * @param
     * @return boolean
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public CacheOutDTO addCache(CacheInDTO dataDto){
        CacheOutDTO outData = new CacheOutDTO();
        String key = dataDto.getKey();
        String cacheType = dataDto.getCacheType();
        String valueType = dataDto.getValueType();

        boolean result = false;
        Map dataMap = new HashMap();
        //校验入参不可为空
        if(StringUtils.isEmpty(key) || StringUtils.isEmpty(cacheType) || StringUtils.isEmpty(valueType)){
        	outData.setReturnCode("9999");
        	outData.setReturnMessage("key值，缓存类型，数据类型不可为空");
        	return  outData;
        }

        try{
            switch(valueType){
            case "1":
                dataMap.put(key, dataDto.getStringValue());//数据类型是string
                switch(cacheType){
                case "1":
                    result = JVMCacheDataUtil.putStringCache(dataMap);//JVM
                    break;
                case "2":
                    result = redisCacheDataUtil.putStringCache(dataMap);//redis
                    break;
                }
            case "2":
                dataMap.put(key, dataDto.getMapValue());//数据类型是Map
                switch(cacheType){
                case "1":
                    result = JVMCacheDataUtil.putStringCache(dataMap);
                    break;
                case "2":
                    result = redisCacheDataUtil.putStringCache(dataMap);
                    break;
                }
            case "3":
                dataMap.put(key, dataDto.getListValue());//数据类型是List
                switch(cacheType){
                case "1":
                    result = JVMCacheDataUtil.putStringCache(dataMap);
                    break;
                case "2":
                    result = redisCacheDataUtil.putStringCache(dataMap);
                    break;
                }
            }
        }catch(Exception e){
            log.error("存入缓存出错",e);
        }
        if(result == true ){
        	outData.setReturnCode("0000");
        }else{
        	outData.setReturnCode("9999");
        }
        return outData;
    }

    /**
     * 缓存删除
     * @param
     * @return boolean
     * @throws Exception
     */
    public CacheOutDTO deleteCache(CacheInDTO dataDto){
        CacheOutDTO outData = new CacheOutDTO();
        String key = dataDto.getKey();
        String cacheType = dataDto.getCacheType();

        //校验入参不可为空
        if(StringUtils.isEmpty(key) || StringUtils.isEmpty(cacheType)){
        	outData.setReturnCode("9999");
            return outData;
        }

        boolean result = false;
        try{
            switch(cacheType){
	            case "1":
	                result = JVMCacheDataUtil.delCache(key);
	            case "2":
	                result = redisCacheDataUtil.delCache(key);
	            }
        }catch(Exception e){
            log.error("存入缓存出错",e);
        }
        if(result == true){
        	outData.setReturnCode("0000");
        }else{
        	outData.setReturnCode("9999");
        }
        return outData;
    }

    /**
     * 缓存查询
     * @param
     * @return boolean
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public CacheOutDTO queryJvmCache(CacheInDTO dataDto){
        CacheOutDTO outData = new CacheOutDTO();
        String key = dataDto.getKey();
        String cacheType = dataDto.getCacheType();
        String dataType = dataDto.getValueType();

        //校验入参不可为空
        if(StringUtils.isEmpty(key) || StringUtils.isEmpty(cacheType) || StringUtils.isEmpty(dataType)){
        	outData.setReturnCode("9999");
        	outData.setReturnMessage("key值,数据类型，缓存类型不可为空");
            return outData;
        }
        
        outData.setReturnCode("0000");
        try {
            switch(cacheType){
	            case "1":
	            	switch(dataType){
	            		case "1" : 
	            			outData.setStringValue(JVMCacheDataUtil.getStringCache(key));
	            			return outData;
	            		case "2" :
	            			outData.setMapValue(JVMCacheDataUtil.getMapCache(key));
	            			return outData;
	            		case "3" :
	            			outData.setListValue(JVMCacheDataUtil.getListCache(key));
	            			return outData;
	            	}
	            case "2":
	            	switch(dataType){
	            		case "1" : 
	            			outData.setStringValue(redisCacheDataUtil.getStringCache(key));
	            			return outData;
	            		case "2" :
	            			outData.setMapValue(redisCacheDataUtil.getMapCache(key));
	            			return outData;
	            		case "3" :
	            			outData.setListValue(redisCacheDataUtil.getListCache(key));
	            			return outData;
            	    }
            }
        }catch(Exception e){
            log.error("查询缓存出错",e);
        	outData.setReturnCode("9999");
        }
        return outData;
    }

    /**
     * 业务db的开关缓存数据增加
     *
     * @param
     * @return ServiceSwitchOutDTO
     * @throws Exception
     */
    @RequestMapping(value = "/saveSwitchDb", method = RequestMethod.POST)
    public ServiceSwitchOutDTO saveSwitchDb(@RequestBody ServiceSwitchInDTO inDto) {
        ServiceSwitchOutDTO dto = new ServiceSwitchOutDTO();
        ServiceSwitchDO bean = new ServiceSwitchDO();
        Long time = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(time);
        try {
            BeanUtils.copyProperties(bean, inDto);
            bean.setCrtTime(timestamp);
            serviceSwitch.saveServiceSwitch(bean);
            dto.setReturnCode("0000");
            dto.setReturnMessage("保存成功");
        } catch (Exception e) {
            dto.setReturnCode("9999");
            dto.setReturnMessage("保存失败");
        }
        return dto;
    }

    /**
     * 业务db的开关缓存数据删除或者更新
     *
     * @param
     * @return ServiceSwitchOutDTO
     * @throws Exception
     */
    @RequestMapping(value = "/updateSwitchDb", method = RequestMethod.POST)
    public ServiceSwitchOutDTO updateSwitchDb(@RequestBody ServiceSwitchInDTO inDto) {
        ServiceSwitchOutDTO dto = new ServiceSwitchOutDTO();
        ServiceSwitchDO bean = new ServiceSwitchDO();
        Long time = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(time);
        try {
            BeanUtils.copyProperties(bean, inDto);
            bean.setModfTime(timestamp);
            serviceSwitch.updateServiceSwitch(bean);
            dto.setReturnCode("0000");
            dto.setReturnMessage("修改成功");
        } catch (Exception e) {
            dto.setReturnCode("9999");
            dto.setReturnMessage("修改失败");
        }
        return dto;
    }

    /**
     * 业务db的开关缓存数据查询
     *
     * @param
     * @return ServiceSwitchOutDTO
     * @throws Exception
     */
    @PostMapping(value = "/getSwitchDb")
    public ServiceSwitchOutDTO getSwitchDb(@RequestBody ServiceSwitchInDTO inDto) {
        ServiceSwitchOutDTO dto = new ServiceSwitchOutDTO();
        ServiceSwitchDO bean = new ServiceSwitchDO();
        try {
            log.info("dto信息:" + inDto.toJSONString());
            BeanUtils.copyProperties(bean, inDto);
            List<ServiceSwitchDO> list = serviceSwitch.getServiceSwitch(bean);
            if (list.size() > 0) {
                dto.setBeans(list);
                dto.setReturnCode("0000");
                dto.setReturnMessage("查询成功");
            } else {
                dto.setReturnCode("2999");
                dto.setReturnMessage("查询失败");
            }
        } catch (Exception e) {
            dto.setReturnCode("9999");
            dto.setReturnMessage("查询失败");
        }
        return dto;
    }

    /**
     * 业务db的配置数据查询
     *
     * @param
     * @return RnfsCfgOutDTO
     * @throws Exception
     */
    @RequestMapping(value = "/getRnfsCfgDb", method = RequestMethod.POST)
    public RnfsCfgOutDTO getRnfsCfgDb(@RequestBody RnfsCfgInDTO inDto) {
        RnfsCfgOutDTO dto = new RnfsCfgOutDTO();
        RnfsCfgDO bean = new RnfsCfgDO();
        try {
            BeanUtils.copyProperties(bean, inDto);
            List<RnfsCfgDO> list = rnfsCfg.getRnfsCfg(bean);
            if (list.size() > 0) {
                dto.setBeans(list);
                dto.setReturnCode("0000");
                dto.setReturnMessage("查询成功");
            } else {
                dto.setReturnCode("2999");
                dto.setReturnMessage("查询失败");
            }
        } catch (Exception e) {
            dto.setReturnCode("9999");
            dto.setReturnMessage("查询失败");
        }
        return dto;
    }

    /**
     * 业务db的配置缓存数据删除或者更新
     *
     * @param
     * @return RnfsCfgDTO
     * @throws Exception
     */
    @RequestMapping(value = "/updateRnfsCfgDb", method = RequestMethod.POST)
    public RnfsCfgOutDTO updateRnfsCfgDb(@RequestBody RnfsCfgInDTO inDto) {
        RnfsCfgOutDTO dto = new RnfsCfgOutDTO();
        RnfsCfgDO bean = new RnfsCfgDO();
        Long time = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(time);
        try {
            BeanUtils.copyProperties(bean, inDto);
            bean.setModfTime(timestamp);
            rnfsCfg.updateRnfsCfg(bean);
            dto.setReturnCode("0000");
            dto.setReturnMessage("修改成功");
        } catch (Exception e) {
            dto.setReturnCode("9999");
            dto.setReturnMessage("修改失败");
        }
        return dto;
    }

    /**
     * 业务db的配置缓存数据增加
     *
     * @param
     * @return ServiceSwitchDTO
     * @throws Exception
     */
    @RequestMapping(value = "/saveRnfsCfgDb", method = RequestMethod.POST)
    public RnfsCfgOutDTO saveRnfsCfgDb(@RequestBody RnfsCfgInDTO inDto) {
        RnfsCfgOutDTO dto = new RnfsCfgOutDTO();
        RnfsCfgDO bean = new RnfsCfgDO();
        Long time = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(time);
        try {
            BeanUtils.copyProperties(bean, inDto);
            bean.setCrtTime(timestamp);
            rnfsCfg.saveRnfsCfg(bean);
            dto.setReturnCode("0000");
            dto.setReturnMessage("保存成功");
        } catch (Exception e) {
            dto.setReturnCode("9999");
            dto.setReturnMessage("保存失败");
        }
        return dto;
    }

    /**
     * 业务db的实名账户数据查询
     *
     * @param
     * @return RealityAccountOutDTO
     * @throws Exception
     */
    @RequestMapping(value = "/getRealityAccount", method = RequestMethod.POST)
    public RealityAccountOutDTO getRealityAccount(@RequestBody RealityAccountInDTO inDto) {
        RealityAccountOutDTO dto = new RealityAccountOutDTO();
        RealityAccountDO bean = new RealityAccountDO();
        try {
            BeanUtils.copyProperties(bean, inDto);
            List<RealityAccountDO> list = realityAccount.getRealityAccount(bean);
            if (list.size() > 0) {
                dto.setBeans(list);
                dto.setReturnCode("0000");
                dto.setReturnMessage("查询成功");
            } else {
                dto.setReturnCode("2999");
                dto.setReturnMessage("查询失败");
            }
        } catch (Exception e) {
            dto.setReturnCode("9999");
            dto.setReturnMessage("查询失败");
        }
        return dto;
    }

    /**
     * 业务db的实名账户数据删除或者更新
     * @param
     * @return RealityAccountOutDTO
     * @throws Exception
     */
    @RequestMapping(value = "/updateRealityAccountDb", method = RequestMethod.POST)
    public RealityAccountOutDTO updateRealityAccountDb(@RequestBody RealityAccountInDTO inDto) {
        RealityAccountOutDTO dto = new RealityAccountOutDTO();
        RealityAccountDO bean = new RealityAccountDO();
        Long time = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(time);
        try {
            log.info("入参indto:" + inDto.toJSONString());
            BeanUtils.copyProperties(bean, inDto);
            bean.setModifyTime(timestamp);
            realityAccount.updaterealityAccount(bean);
            dto.setReturnCode("0000");
            dto.setReturnMessage("修改成功");
            log.info("出参dto:" + dto.toJSONString());
        } catch (Exception e) {
            dto.setReturnCode("9999");
            dto.setReturnMessage("修改失败");
        }
        return dto;
    }

    /**
     * 业务db的实名账户数据增加
     * @param
     * @return RealityAccountOutDTO
     * @throws Exception
     */
    @RequestMapping(value = "/saveRealityAccountDb", method = RequestMethod.POST)
    public RealityAccountOutDTO saveRealityAccountDb(@RequestBody RealityAccountInDTO inDto) {
        RealityAccountOutDTO dto = new RealityAccountOutDTO();
        RealityAccountDO bean = new RealityAccountDO();
        Long time = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(time);
        try {
            BeanUtils.copyProperties(bean, inDto);
            bean.setCrtTime(timestamp);
            realityAccount.saveRealityAccount(bean);
            dto.setReturnCode("0000");
            dto.setReturnMessage("保存成功");
        } catch (Exception e) {
            dto.setReturnCode("9999");
            dto.setReturnMessage("保存失败");
        }
        return dto;
    }

    /**
     * 业务db的密钥数据查询
     *
     * @param
     * @return RsaKeyOutDTO
     * @throws Exception
     */
    @RequestMapping(value = "/getRsaKeyDb", method = RequestMethod.POST)
    public RsaKeyOutDTO getRsaKeyDb(@RequestBody RsaKeyInDTO inDto) {
        RsaKeyOutDTO dto = new RsaKeyOutDTO();
        RsaKeyDO bean = new RsaKeyDO();
        try {
            BeanUtils.copyProperties(bean, inDto);
            List<RsaKeyDO> list = rsaKey.getRsaKey(bean);
            if (list.size() > 0) {
                dto.setBeans(list);
                dto.setReturnCode("0000");
                dto.setReturnMessage("查询成功");
            } else {
                dto.setReturnCode("2999");
                dto.setReturnMessage("查询失败");
            }
        } catch (Exception e) {
            dto.setReturnCode("9999");
            dto.setReturnMessage("查询失败");
        }
        return dto;
    }

    /**
     * 业务db的密钥数据删除或者更新
     * @param
     * @return RsaKeyOutDTO
     * @throws Exception
     */
    @RequestMapping(value = "/updateRsaKeyDb", method = RequestMethod.POST)
    public RsaKeyOutDTO updateRsaKeyDb(@RequestBody RsaKeyInDTO inDto) {
        RsaKeyOutDTO dto = new RsaKeyOutDTO();
        RsaKeyDO bean = new RsaKeyDO();
        Long time = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(time);
        try {
            BeanUtils.copyProperties(bean, inDto);
            bean.setModfTime(timestamp);
            rsaKey.updateRsaKey(bean);
            dto.setReturnCode("0000");
            dto.setReturnMessage("修改成功");
        } catch (Exception e) {
            dto.setReturnCode("9999");
            dto.setReturnMessage("修改失败");
        }
        return dto;
    }

    /**
     * 业务db的密钥数据增加
     * @param
     * @return RsaKeyOutDTO
     * @throws Exception
     */
    @RequestMapping(value = "/saveRsaKeyDb", method = RequestMethod.POST)
    public RsaKeyOutDTO saveRsaKeyDb(@RequestBody RsaKeyInDTO inDto) {
        RsaKeyOutDTO dto = new RsaKeyOutDTO();
        RsaKeyDO bean = new RsaKeyDO();
        Long time = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(time);
        try {
            BeanUtils.copyProperties(bean, inDto);
            bean.setCrtTime(timestamp);
            rsaKey.saveRsaKey(bean);
            dto.setReturnCode("0000");
            dto.setReturnMessage("保存成功");
        } catch (Exception e) {
            dto.setReturnCode("9999");
            dto.setReturnMessage("保存失败");
        }
        return dto;
    }
}
