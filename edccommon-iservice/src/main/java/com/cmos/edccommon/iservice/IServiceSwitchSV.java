package com.cmos.edccommon.iservice;

import java.util.List;
import java.util.Map;

import com.cmos.edccommon.beans.ServiceSwitchDO;

/**
 * Created by 66408 on 2017/10/17.
 */
public interface IServiceSwitchSV {
   List<ServiceSwitchDO> selectByType(Map<String, Object> input);
   void insertSelective(ServiceSwitchDO record);
   void updateByPrimaryKeySelective(ServiceSwitchDO record);
   ServiceSwitchDO getServiceSwitchByKey(String swtchKey);
}
