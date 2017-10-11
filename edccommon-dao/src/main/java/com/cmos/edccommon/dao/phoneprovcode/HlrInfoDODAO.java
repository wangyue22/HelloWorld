package com.cmos.edccommon.dao.phoneprovcode;

import java.util.List;

import com.cmos.edccommon.beans.phoneprovcode.HlrInfoDO;

public interface HlrInfoDODAO {
    int deleteByPrimaryKey(Long sctnoId);

    int insert(HlrInfoDO record);

    HlrInfoDO selectByPrimaryKey(Long sctnoId);

    List<HlrInfoDO> selectAll();

    int updateByPrimaryKey(HlrInfoDO record);
    //
    public String getProvCodeByPhoneNum(String phoneNum);
}