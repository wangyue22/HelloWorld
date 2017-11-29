package com.cmos.edccommon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.edccommon.beans.serviceSwitch.ServiceSwitchDO;
import com.cmos.edccommon.dao.ServiceSwitchDAO;
import com.cmos.edccommon.iservice.IServiceSwitchSV;

/**
 * 运营管理缓存开关实现类
 * Created by guozong on 2017/10/17.
 */
@Service(group = "edcco")
public class ServiceSwitchSVImpl implements IServiceSwitchSV {
    @Autowired
    private ServiceSwitchDAO serviceSwitchDao;
    @Override
    public List<ServiceSwitchDO> selectByType(Map<String, Object> input) {
        List<ServiceSwitchDO> list = serviceSwitchDao.slectByType(input);
        return list;
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public ServiceSwitchDO getServiceSwitchByKey(String swtchKey) {
        Map a= new HashMap();
        a.put("swtchKey",swtchKey);
        ServiceSwitchDO serviceSwitchDO = serviceSwitchDao.getServiceSwitchByKey(a);
        return serviceSwitchDO;
    }

    @Override
    public List<ServiceSwitchDO> getServiceSwitchByDataType(String cacheTypeCd, String dataType) {
        List<ServiceSwitchDO> serviceSwitch = serviceSwitchDao.getServiceSwitchByDataType(cacheTypeCd, dataType);
        return serviceSwitch;
    }

    /**
     * 开关表查询开关信息
     */
    @Override
    public List<ServiceSwitchDO> getServiceSwitch(ServiceSwitchDO switchdo) {
        return serviceSwitchDao.getServiceSwitch(switchdo);
    }

    /**
     * 开关表新增开关信息
     */
    @Override
    public void saveServiceSwitch(ServiceSwitchDO switchdo) {
        serviceSwitchDao.saveServiceSwitch(switchdo);
    }

    /**
     * 开关表删除开关信息(将标志位置为0)
     */
    @Override
    public void deleteServiceSwitch(ServiceSwitchDO switchdo) {
        serviceSwitchDao.deleteServiceSwitch(switchdo);
    }

    /**
     * 开关表修改开关信息
     */
    @Override
    public void updateServiceSwitch(ServiceSwitchDO switchdo) {
        serviceSwitchDao.updateServiceSwitch(switchdo);
    }

}
