package com.cmos.edccommon.dao.piccompare;

import com.cmos.edccommon.beans.piccompare.CoPicCompareInfoDO;

public interface CoPicCompareInfoDAO {
    int deleteByPrimaryKey(Long cmprId);

    int insert(CoPicCompareInfoDO record);

    int insertSelective(CoPicCompareInfoDO record);

    CoPicCompareInfoDO selectByPrimaryKey(Long cmprId);

    int updateByPrimaryKeySelective(CoPicCompareInfoDO record);

    int updateByPrimaryKey(CoPicCompareInfoDO record);
}