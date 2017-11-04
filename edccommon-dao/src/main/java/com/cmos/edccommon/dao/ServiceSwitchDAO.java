package com.cmos.edccommon.dao;

import java.util.List;
import java.util.Map;

import com.cmos.edccommon.beans.serviceSwitch.ServiceSwitchDO;
import com.cmos.edccommon.beans.serviceSwitch.ServiceSwitchInDTO;

public interface ServiceSwitchDAO {
    /**
     * 获取数据库所有该存入缓存的开关
     * @param input
     * @return
     */
    List<ServiceSwitchDO> slectByType(Map<String, Object> input);

    /**
     * 根据缓存key值获取开关信息
     * @param in
     * @return
     */
    ServiceSwitchDO getServiceSwitchByKey(Map in);

    /**根据开关类型和业务系统编码查询开关的key和value
     * @param map
     * @return
     */
    List<ServiceSwitchDO> select(Map<String, String> map);

    /**新增开关
     * @param switchdo
     */
    void insert(ServiceSwitchInDTO switchdo);

    /**删除开关，将标志位置为0
     * @param map
     */
    void delete(Map<String, String> map);

    /**修改相应的开关
     * @param switchdo
     */
    void update(ServiceSwitchInDTO switchdo);

    /**
     * 根据数据类型和缓存类型获取开关信息
     */
    List<ServiceSwitchDO> getServiceSwitchByDataType(String cacheTypeCd, String dataType);
}