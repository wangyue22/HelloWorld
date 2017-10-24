package com.cmos.edccommon.iservice.piccompare;

import com.cmos.edccommon.beans.piccompare.CoPicCompareInfoDO;
import com.cmos.msg.common.IConsumerProcessor;

/**
 * 人像比对
 *
 * @author xdx
 * @since 1.0
 */

public interface IPicCompareSV extends IConsumerProcessor{
	/**
	 * 保存人像比对记录
	 * @param param
	 * @return
	 */
	public void savePicCompareLog(CoPicCompareInfoDO param);

}
