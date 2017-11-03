package com.cmos.edccommon.iservice;

import java.util.List;
import java.util.Map;

import com.cmos.edccommon.beans.serviceSwitch.ServiceSwitchDO;
import com.cmos.edccommon.beans.serviceSwitch.ServiceSwitchInDTO;

/**
 * Created by guozong on 2017/10/17.
 */
public interface IServiceSwitchSV {
    List<ServiceSwitchDO> selectByType(Map<String, Object> input);
    void insertSelective(ServiceSwitchDO record);
    void updateByPrimaryKeySelective(ServiceSwitchDO record);
    ServiceSwitchDO getServiceSwitchByKey(String swtchKey);

    /** 
     * 查询开关
     * @param switchdo
     * @return
     */
    public List<ServiceSwitchDO> getServiceSwitchByCode(ServiceSwitchInDTO switchdo);

    /**
     * 新增开关 
     * @param switchdo
     * 
     */
    public void insertServiceSwitch(ServiceSwitchInDTO switchdo);

    /**
     * 逻辑删除开关，将标志位置为0
     * @param switchdo
     * 
     */
    public void deleteServiceSwitch(ServiceSwitchInDTO switchdo);

    /**
     * 更新开关
     * @param switchdo
     * 
     */
    public void updateServiceSwitch(ServiceSwitchInDTO switchdo);
}
