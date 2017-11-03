package com.cmos.edccommon.iservice.serviceswitch;

import java.util.List;
import java.util.Map;

import com.cmos.edccommon.beans.serviceswitch.ServiceSwitchDO;
import com.cmos.edccommon.beans.serviceswitch.ServiceSwitchInDTO;


/**
 * 缓存开关
 * Created by 66408 on 2017/10/17.
 */
public interface IServiceSwitchSV {
    /**
     * 检索出加入内存的数据
     * @param input
     * @return
     */
    List<ServiceSwitchDO> selectByType(Map<String, Object> input);

    /**
     * 新增
     * @param record
     */
    void insertSelective(ServiceSwitchDO record);

    /**
     * 更新
     * @param record
     */
    void updateByPrimaryKeySelective(ServiceSwitchDO record);

    /**
     * 根据开关关键字检索开关值
     * @param record
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
