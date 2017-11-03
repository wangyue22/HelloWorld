package com.cmos.edccommon.web.controller;

import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.cache.CacheInDTO;
import com.cmos.edccommon.beans.cache.CacheOutDTO;
import com.cmos.edccommon.beans.crkey.CoRsaKeyDO;
import com.cmos.edccommon.beans.crkey.RsaKeyInDTO;
import com.cmos.edccommon.beans.crkey.RsaKeyOutDTO;
import com.cmos.edccommon.beans.realityAccount.RealityAccountInDTO;
import com.cmos.edccommon.beans.realityAccount.RealityAccountOutDTO;
import com.cmos.edccommon.beans.realityAccount.TOpRealityAccountDO;
import com.cmos.edccommon.beans.rnfsCfg.RnfsCfgInDTO;
import com.cmos.edccommon.beans.rnfsCfg.RnfsCfgOutDTO;
import com.cmos.edccommon.beans.rnfsCfg.TOpRnfsCfgDO;
import com.cmos.edccommon.beans.serviceswitch.ServiceSwitchDO;
import com.cmos.edccommon.beans.serviceswitch.ServiceSwitchInDTO;
import com.cmos.edccommon.beans.serviceswitch.ServiceSwitchOutDTO;
import com.cmos.edccommon.iservice.crkey.IKeyInfoSV;
import com.cmos.edccommon.iservice.realityAccount.ITOpRealityAccountSV;
import com.cmos.edccommon.iservice.rnfsCfg.ITOpRnfsCfgSV;
import com.cmos.edccommon.iservice.serviceswitch.IServiceSwitchSV;
import com.cmos.edccommon.web.cache.JVMCacheDataUtil;
import com.cmos.edccommon.web.cache.RedisCacheDataUtil;



/**
 * @author 任林达
 */

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
    private ITOpRnfsCfgSV rnfsCfg;

    @Reference(group = "edcco")
    private ITOpRealityAccountSV realityAccount;

    @Reference(group = "edcco")
    private IKeyInfoSV rsaKey;
    /**
     * 缓存增加或者更新
     * @param
     * @return boolean
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
    @ApiOperation(value="String类型缓存增加和修改")
    @RequestMapping(value = "/addCache",method = RequestMethod.POST)
    public boolean addCache(CacheInDTO dataDto){
        String key = dataDto.getKey();
        String cacheType = dataDto.getCacheType();
        String valueType = dataDto.getValueType();

        boolean result = false;
        Map dataMap = new HashMap();
        //校验入参不可为空
        if(StringUtils.isEmpty(key) || StringUtils.isEmpty(cacheType) || StringUtils.isEmpty(valueType)){
            return false;
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
            log.error("存入缓存出错");
        }
        return result;
    }

    /**
     * 缓存删除
     * @param
     * @return boolean
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    public boolean deleteCache(CacheInDTO dataDto){
        String key = dataDto.getKey();
        String cacheType = dataDto.getCacheType();

        //校验入参不可为空
        if(StringUtils.isEmpty(key) || StringUtils.isEmpty(cacheType)){
            return false;
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
            log.error("存入缓存出错");
        }
        return result;
    }

    /**
     * 缓存查询
     * @param
     * @return boolean
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    public CacheOutDTO queryJvmCache(CacheInDTO dataDto){
        CacheOutDTO outData = new CacheOutDTO();
        String key = dataDto.getKey();
        String cacheType = dataDto.getCacheType();

        //校验入参不可为空
        if(StringUtils.isEmpty(key) || StringUtils.isEmpty(cacheType)){
            outData.setReturnMessage("key值和缓存类型不可为空");
            return outData;
        }

        Object value = null;
        try {
            switch(cacheType){
            case "1":
                value = JVMCacheDataUtil.getObjectCache(key);
            case "2":
                value = redisCacheDataUtil.getObjectCache(key);
            }
            outData.setObjectValue(value);
        }catch(Exception e){
            log.error("查询缓存出错");
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
    @RequestMapping(value = "/addSwitchDb", method = RequestMethod.POST)
    public ServiceSwitchOutDTO addSwitchDb(@RequestBody ServiceSwitchInDTO bean) {
        ServiceSwitchOutDTO dto = new ServiceSwitchOutDTO();
        Date crtTime = new Date();
        bean.setCrtTime(crtTime);
        bean.setCrtUserId("HN0001"); // 操作员编号，根据需要修改
        bean.setCrtAppSysId("1"); // 操作系统编号，根据需要修改
        bean.setValidFlag("1");
        try {
            serviceSwitch.insertServiceSwitch(bean);
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
    public ServiceSwitchOutDTO updateSwitchDb(@RequestBody ServiceSwitchInDTO bean) {
        ServiceSwitchOutDTO dto = new ServiceSwitchOutDTO();
        Date modfTime = new Date();
        bean.setModfTime(modfTime);
        bean.setModfUserId("HN0001"); // 操作员编号，根据需要修改
        bean.setModfAppSysId("1"); // 操作系统编号，根据需要修改
        try {
            serviceSwitch.updateServiceSwitch(bean);
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
    @RequestMapping(value = "/querySwitchDb", method = RequestMethod.POST)
    public ServiceSwitchOutDTO querySwitchDb(@RequestBody ServiceSwitchInDTO bean) {
        ServiceSwitchOutDTO dto = new ServiceSwitchOutDTO();
        try {
            List<ServiceSwitchDO> list = serviceSwitch.getServiceSwitchByCode(bean);
            if (list.size() > 0) {
                dto.setBeans(list);
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
    @RequestMapping(value = "/queryRnfsCfgDb", method = RequestMethod.POST)
    public RnfsCfgOutDTO querySwitchDb(@RequestBody RnfsCfgInDTO bean) {
        RnfsCfgOutDTO dto = new RnfsCfgOutDTO();
        try {
            List<TOpRnfsCfgDO> list = rnfsCfg.getRnfsCfg(bean);
            if (list.size() > 0) {
                dto.setBeans(list);
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
    public RnfsCfgOutDTO updateRnfsCfgDb(@RequestBody RnfsCfgInDTO bean) {
        RnfsCfgOutDTO dto = new RnfsCfgOutDTO();
        Date modfTime = new Date();
        bean.setModfTime(modfTime);
        bean.setModfUserId("HN0001"); // 操作员编号，根据需要修改
        bean.setModfAppSysId("1"); // 操作系统编码，根据需要修改
        try {
            rnfsCfg.updategetRnfsCfg(bean);
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
    @RequestMapping(value = "/addRnfsCfgDb", method = RequestMethod.POST)
    public RnfsCfgOutDTO addRnfsCfgDb(@RequestBody RnfsCfgInDTO bean) {
        RnfsCfgOutDTO dto = new RnfsCfgOutDTO();
        Date crtTime = new Date();
        bean.setCrtTime(crtTime);
        bean.setCrtUserId("HN0001"); // 操作员编号，根据需要修改
        bean.setCrtAppSysId("1"); // 操作系统编码，根据需要修改
        bean.setValidFlag("1");
        try {
            rnfsCfg.insertgetRnfsCfg(bean);
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
    @RequestMapping(value = "/queryRsaKeyDb", method = RequestMethod.POST)
    public RealityAccountOutDTO queryRsaKeyDb(@RequestBody RealityAccountInDTO bean) {
        RealityAccountOutDTO dto = new RealityAccountOutDTO();
        try {
            List<TOpRealityAccountDO> list = realityAccount.getRealityAccount(bean);
            if (list.size() > 0) {
                dto.setBeans(list);
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
    public RealityAccountOutDTO updateRealityAccountDb(@RequestBody RealityAccountInDTO bean) {
        RealityAccountOutDTO dto = new RealityAccountOutDTO();
        Date modfTime = new Date();
        bean.setModifyTime(modfTime);
        bean.setModfUserId("HN0001"); // 操作员编号，根据需要修改
        bean.setModifyAppSysId("1"); // 操作系统编码，根据需要修改
        try {
            realityAccount.updaterealityAccount(bean);
            dto.setReturnMessage("修改成功");
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
    @RequestMapping(value = "/addRealityAccountDb", method = RequestMethod.POST)
    public RealityAccountOutDTO addRealityAccountDb(@RequestBody RealityAccountInDTO bean) {
        RealityAccountOutDTO dto = new RealityAccountOutDTO();
        Date crtTime = new Date();
        bean.setCrtTime(crtTime);
        bean.setCrtUserId("HN0001"); // 操作员编号，根据需要修改
        bean.setCrtAppSysId("1"); // 操作系统编码，根据需要修改
        bean.setValidFlag("1");
        try {
            realityAccount.insertRealityAccount(bean);
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
    @RequestMapping(value = "/queryRsaKeyDb", method = RequestMethod.POST)
    public RsaKeyOutDTO queryRsaKeyDb(@RequestBody RsaKeyInDTO bean) {
        RsaKeyOutDTO dto = new RsaKeyOutDTO();
        try {
            List<CoRsaKeyDO> list = rsaKey.getRsaKey(bean);
            if (list.size() > 0) {
                dto.setBeans(list);
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
    public RsaKeyOutDTO updateRsaKeyDb(@RequestBody RsaKeyInDTO bean) {
        RsaKeyOutDTO dto = new RsaKeyOutDTO();
        Date modfTime = new Date();
        bean.setModfTime(modfTime);
        bean.setModfUserId("HN0001"); // 操作员编号，根据需要修改
        bean.setModfAppSysId("1"); // 操作系统编码，根据需要修改
        try {
            rsaKey.updateRsaKey(bean);
            dto.setReturnMessage("修改成功");
        } catch (Exception e) {
            dto.setReturnCode("9999");
            dto.setReturnMessage("修改失败");
        }
        return dto;
    }

    /**
     * 业务db的实名账户数据增加
     * @param
     * @return RsaKeyOutDTO
     * @throws Exception
     */
    @RequestMapping(value = "/addRsaKeyDb", method = RequestMethod.POST)
    public RsaKeyOutDTO addRsaKeyDb(@RequestBody RsaKeyInDTO bean) {
        RsaKeyOutDTO dto = new RsaKeyOutDTO();
        Date crtTime = new Date();
        bean.setCrtTime(crtTime);
        bean.setCrtUserId("HN0001"); // 操作员编号，根据需要修改
        bean.setCrtAppSysId("1"); // 操作系统编码，根据需要修改
        bean.setValidFlag("1");
        try {
            rsaKey.insertRsaKey(bean);
            dto.setReturnMessage("保存成功");
        } catch (Exception e) {
            dto.setReturnCode("9999");
            dto.setReturnMessage("保存失败");
        }
        return dto;
    }

}
