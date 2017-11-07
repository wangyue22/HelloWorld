package com.cmos.edccommon.dao;

import java.util.List;
import java.util.Map;

import com.cmos.edccommon.beans.serviceSwitch.ServiceSwitchDO;


public interface ServiceSwitchDAO {
    /**
     * 获取所有存入缓存的开关
     * @param input
     * @return
     */
    List<ServiceSwitchDO> slectByType(Map<String, Object> input);
    /**
     * 根据开关key获取开关
     * @param in
     * @return
     */
    ServiceSwitchDO getServiceSwitchByKey(Map in);

    /**
     * 根据数据类型和缓存类型获取开关信息
     */
    List<ServiceSwitchDO> getServiceSwitchByDataType(String cacheTypeCd, String dataType);

    /**查询开关信息
     * @param dto
     * @return
     */
    List<ServiceSwitchDO> getServiceSwitch(ServiceSwitchDO serviceSwitch);

    /**新增开关
     * @param dto
     */
    void saveServiceSwitch(ServiceSwitchDO serviceSwitch);

    /**删除开关，将标志位置为0
     * @param dto
     */
    void deleteServiceSwitch(ServiceSwitchDO serviceSwitch);

    /**修改相应的开关
     * @param dto
     */
    void updateServiceSwitch(ServiceSwitchDO serviceSwitch);

}
