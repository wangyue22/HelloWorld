package com.cmos.edccommon.iservice.serviceswitch;

import java.util.List;
import java.util.Map;

import com.cmos.edccommon.beans.serviceswitch.ServiceSwitchDO;
import com.cmos.edccommon.beans.serviceswitch.ServiceSwitchInDTO;

/**
 * Created by guozong on 2017/10/17.
 */
public interface IServiceSwitchSV {

    void insertSelective(ServiceSwitchDO record);
    void updateByPrimaryKeySelective(ServiceSwitchDO record);
    ServiceSwitchDO getServiceSwitchByKey(String swtchKey);
    List<ServiceSwitchDO> getServiceSwitchByDataType(String cacheTypeCd,String dataType);

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
    /**
     * 根据缓存类型获取开关的key  value
     * @param input
     * @return
     */
    List<ServiceSwitchDO> selectByType(Map<String, Object> input);

}
