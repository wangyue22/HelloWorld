package com.cmos.edccommon.iservice;

import java.util.List;
import java.util.Map;

import com.cmos.edccommon.beans.serviceSwitch.ServiceSwitchDO;

/**
 * 运营管理 缓存开关
 * Created by guozong on 2017/10/17.
 */
public interface IServiceSwitchSV {
    /**
     * 根据开关key值获取开关
     * @param swtchKey
     * @return
     */
    ServiceSwitchDO getServiceSwitchByKey(String swtchKey);
    /**
     * 根据开关类型获取开关
     * @param cacheTypeCd dataType
     * @return
     */
    List<ServiceSwitchDO> getServiceSwitchByDataType(String cacheTypeCd,String dataType);

    /**
     * 查询开关
     * @param switchdo
     * @return
     */
    public List<ServiceSwitchDO> getServiceSwitch(ServiceSwitchDO switchdo);

    /**
     * 新增开关
     * @param switchdo
     *
     */
    public void saveServiceSwitch(ServiceSwitchDO switchdo);

    /**
     * 逻辑删除开关，将标志位置为0
     * @param switchdo
     *
     */
    public void deleteServiceSwitch(ServiceSwitchDO switchdo);

    /**
     * 更新开关
     * @param switchdo
     *
     */
    public void updateServiceSwitch(ServiceSwitchDO switchdo);
    /**
     * 根据缓存类型获取开关的key  value
     * @param input
     * @return
     */
    List<ServiceSwitchDO> selectByType(Map<String, Object> input);

}
