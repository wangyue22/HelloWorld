package com.cmos.edccommon.dao.crkey;

import com.cmos.edccommon.beans.crkey.CoRsaKeyDO;
import com.cmos.edccommon.beans.crkey.KeyInfoDTO;

/**
 * 获取秘钥
 */
public interface CoRsaKeyDAO {

	/**
	 * 根据KeyInfoDTO获取秘钥
	 * @param record
	 * @return
	 */
	CoRsaKeyDO selectKey(KeyInfoDTO record);
}