package com.cmos.edccommon.dao.facelive;

import com.cmos.edccommon.beans.facelive.CoFaceLiveInfoDO;

/**
 * 保存静默活体调用记录
 */
public interface CoFaceLiveInfoDAO {
	/**
	 * 保存静默活体调用记录
	 * @param record
	 * @return
	 */
    int insert(CoFaceLiveInfoDO record);
}