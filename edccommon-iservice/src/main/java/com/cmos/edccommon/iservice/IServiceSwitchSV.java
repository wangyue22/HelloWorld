package com.cmos.edccommon.iservice;

import java.util.List;
import java.util.Map;

import com.cmos.edccommon.beans.serviceSwitch.ServiceSwitchDO;
import com.cmos.edccommon.beans.serviceSwitch.ServiceSwitchInDTO;

/**
 * Created by guozong on 2017/10/17.
 */
public interface IServiceSwitchSV {
    /**
     * 获取数据库所有该存入缓存的开关
     * @param input
     * @return
     */
    List<ServiceSwitchDO> selectByType(Map<String, Object> input);
    /**
     * 根据开关key值获取开关
     * @param swtchKey
     * @return
     */
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
