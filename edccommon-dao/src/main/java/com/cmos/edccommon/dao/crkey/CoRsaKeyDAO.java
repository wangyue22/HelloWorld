package com.cmos.edccommon.dao.crkey;

import com.cmos.edccommon.beans.crkey.CoRsaKeyDO;
import com.cmos.edccommon.beans.crkey.KeyInfoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
	/**
	 * 获取初始化缓存数据
	 * @param cacheTypeCd
	 * @param cacheDataTypeCd
	 * @return
	 */
	List<CoRsaKeyDO> getKeyByType(@Param(value="cacheTypeCd")  String cacheTypeCd, @Param(value="cacheDataTypeCd")   String cacheDataTypeCd);
}