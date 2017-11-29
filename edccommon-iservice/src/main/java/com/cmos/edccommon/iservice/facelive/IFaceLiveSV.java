package com.cmos.edccommon.iservice.facelive;

import com.cmos.edccommon.beans.facelive.CoFaceLiveInfoDO;


/**
 * 保存静默活体调用日志（若使用vertica，该文件废弃）
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
