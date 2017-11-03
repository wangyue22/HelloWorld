package com.cmos.edccommon.dao.piccompare;

import com.cmos.edccommon.beans.piccompare.CoPicCompareInfoDO;
/**
 * 人像比对DAO层
 * @author xdx
 *
 */
public interface CoPicCompareInfoDAO {

	/**
	 * 新增人像比对日志记录
	 * @param record
	 * @return
	 */
    int insert(CoPicCompareInfoDO record);
}