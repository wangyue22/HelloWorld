package com.cmos.edccommon.dao.serviceswitch;

import java.util.List;
import java.util.Map;

import com.cmos.edccommon.beans.serviceswitch.ServiceSwitchDO;


public interface ServiceSwitchDAO {
    List<ServiceSwitchDO> slectByType(Map<String, Object> input);
   void insertSelective(ServiceSwitchDO record);
   void updateByPrimaryKeySelective(ServiceSwitchDO record);
    ServiceSwitchDO getServiceSwitchByKey(Map in);
}