package com.cmos.edccommon.iservice.crkey;

import com.cmos.edccommon.beans.crkey.CoRsaKeyDO;
import com.cmos.edccommon.beans.crkey.KeyInfoDTO;

import java.util.List;

/**
 * 根据手机号得到对应的省端编码
 *
 * @author xdx
 *
 */
public interface IKeyInfoSV {

   
    /**
     * @param param
     * @return
     * @throws Exception 
     * @date 2017-10-17 17:00:00
     */
  public  CoRsaKeyDO getRsaKey(KeyInfoDTO inParam);
    /**
     * 获取初始化缓存数据
     * @param cacheTypeCd
     * @param cacheDataTypeCd
     * @return
     */
    List<CoRsaKeyDO> getKeyByType(String cacheTypeCd, String cacheDataTypeCd);
}
