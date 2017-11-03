package com.cmos.edccommon.service.impl.serviceswitch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.edccommon.beans.serviceswitch.ServiceSwitchDO;
import com.cmos.edccommon.beans.serviceswitch.ServiceSwitchInDTO;
import com.cmos.edccommon.dao.serviceswitch.ServiceSwitchDAO;
import com.cmos.edccommon.iservice.serviceswitch.IServiceSwitchSV;

/**
 * Created by guozong on 2017/10/17.
 */
@Service(group = "edcctsvs")
public class ServiceSwitchSVImpl implements IServiceSwitchSV {
    @Autowired
    private ServiceSwitchDAO serviceSwitchDao;
    @Override
    public List<ServiceSwitchDO> selectByType(Map<String, Object> input) {
        List<ServiceSwitchDO> list = serviceSwitchDao.slectByType(input);
        return list;
    }

    @Override
    public void insertSelective(ServiceSwitchDO record) {
        serviceSwitchDao.insertSelective(record);
    }

    @Override
    public void updateByPrimaryKeySelective(ServiceSwitchDO record) {
        serviceSwitchDao.updateByPrimaryKeySelective(record);
    }

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
    public List<ServiceSwitchDO> getServiceSwitchByCode(ServiceSwitchInDTO switchdo) {
        return serviceSwitchDao.select(switchdo);
    }

    /**
     * 开关表新增开关信息
     */
    @Override
    public void insertServiceSwitch(ServiceSwitchInDTO switchdo) {
        serviceSwitchDao.insert(switchdo);
    }

    /**
     * 开关表删除开关信息(将标志位置为0)
     */
    @Override
    public void deleteServiceSwitch(ServiceSwitchInDTO switchdo) {
        serviceSwitchDao.delete(switchdo);
    }

    /**
     * 开关表修改开关信息
     */
    @Override
    public void updateServiceSwitch(ServiceSwitchInDTO switchdo) {
        serviceSwitchDao.update(switchdo);
    }

}
