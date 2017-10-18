package com.cmos.edccommon.iservice.crkey;

import com.cmos.edccommon.beans.crkey.CoKeyDO;
import com.cmos.edccommon.beans.crkey.KeyInfoDTO;

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
  public  CoKeyDO getKey(KeyInfoDTO inParam);
}
