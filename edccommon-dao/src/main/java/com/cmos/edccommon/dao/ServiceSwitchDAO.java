package com.cmos.edccommon.dao;

import java.util.List;
import java.util.Map;

import com.cmos.edccommon.beans.ServiceSwitchDO;

public interface ServiceSwitchDAO {
    List<ServiceSwitchDO> slectByType(Map<String, Object> input);
   void insertSelective(ServiceSwitchDO record);
   void updateByPrimaryKeySelective(ServiceSwitchDO record);
    ServiceSwitchDO getServiceSwitchByKey(Map in);
}