package com.cmos.edccommon.iservice.realityAccount;

import com.cmos.edccommon.beans.realityAccount.TOpRealityAccountDO;

import java.util.List;

/**
 * Created by guozong on 2017/10/31.
 */
public interface ITOpRealityAccountSV {
    /**
     * 根据缓存类型和数据类型查询实名账户信息
     * @param cacheTypeCd
     * @param cacheDataTypeCd
     * @return
     */
    List<TOpRealityAccountDO> getRealityAccountByType(String cacheTypeCd, String cacheDataTypeCd);
}
