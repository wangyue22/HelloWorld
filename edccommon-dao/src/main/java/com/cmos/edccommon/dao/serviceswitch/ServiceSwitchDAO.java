package com.cmos.edccommon.dao.serviceswitch;

import java.util.List;
import java.util.Map;

import com.cmos.edccommon.beans.serviceswitch.ServiceSwitchDO;
import com.cmos.edccommon.beans.serviceswitch.ServiceSwitchInDTO;


public interface ServiceSwitchDAO {
    List<ServiceSwitchDO> slectByType(Map<String, Object> input);
    void insertSelective(ServiceSwitchDO record);
    void updateByPrimaryKeySelective(ServiceSwitchDO record);
    ServiceSwitchDO getServiceSwitchByKey(Map in);

    /**
     * 根据数据类型和缓存类型获取开关信息
     */
    List<ServiceSwitchDO> getServiceSwitchByDataType(String cacheTypeCd, String dataType);

    /**查询开关信息
     * @param dto
     * @return
     */
    List<ServiceSwitchDO> select(ServiceSwitchInDTO dto);

    /**新增开关
     * @param dto
     */
    void insert(ServiceSwitchInDTO dto);

    /**删除开关，将标志位置为0
     * @param dto
     */
    void delete(ServiceSwitchInDTO dto);

    /**修改相应的开关
     * @param dto
     */
    void update(ServiceSwitchInDTO dto);

}
