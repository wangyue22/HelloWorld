package com.cmos.edccommon.dao.crkey;

import com.cmos.edccommon.beans.crkey.CoKeyDO;
import com.cmos.edccommon.beans.crkey.KeyInfoDTO;

public interface CoKeyDAO {

    CoKeyDO selectKey(KeyInfoDTO record);
}