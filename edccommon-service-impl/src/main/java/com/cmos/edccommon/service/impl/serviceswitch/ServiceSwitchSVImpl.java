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
@Service(group = "edcco")
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
    public List<ServiceSwitchDO> getServiceSwitchByCode(ServiceSwitchInDTO switchdo) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("swtchTypeCd", switchdo.getSwtchTypeCd());
        map.put("bizSysCode", switchdo.getBizSysCode());
        return serviceSwitchDao.select(map);
    }

    @Override
    public void insertServiceSwitch(ServiceSwitchInDTO switchdo) {
        serviceSwitchDao.insert(switchdo);
    }

    @Override
    public void deleteServiceSwitch(ServiceSwitchInDTO switchdo) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("swtchTypeCd", switchdo.getSwtchTypeCd());
        map.put("bizSysCode", switchdo.getBizSysCode());
        serviceSwitchDao.delete(map);
    }

    @Override
    public void updateServiceSwitch(ServiceSwitchInDTO switchdo) {
        serviceSwitchDao.update(switchdo);
    }
}
