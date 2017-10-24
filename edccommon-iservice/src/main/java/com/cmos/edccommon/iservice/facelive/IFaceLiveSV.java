package com.cmos.edccommon.iservice.facelive;

import com.cmos.edccommon.beans.facelive.CoFaceLiveInfoDO;
import com.cmos.msg.common.IConsumerProcessor;


/**
 * 人像比对
 *
 * @author xdx
 * @since 1.0
 */

public interface IFaceLiveSV extends IConsumerProcessor{
	/**
	 * 保存静默活体检测记录
	 * @param param
	 * @return
	 */
	public void saveFaceLiveLog(CoFaceLiveInfoDO param);

}
