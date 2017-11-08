package com.cmos.edccommon.iservice.facelive;

import com.cmos.edccommon.beans.facelive.CoFaceLiveInfoDO;


/**
 * 人像比对
 *
 * @author xdx
 * @since 1.0
 */

public interface IFaceLiveSV{
	/**
	 * 保存静默活体检测记录
	 * @param param
	 * @return
	 */
	public void saveFaceLiveLog(CoFaceLiveInfoDO param);

}
