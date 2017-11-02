package com.cmos.edccommon.dao.realityAccount;

import com.cmos.edccommon.beans.realityAccount.TOpRealityAccountDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by guozong on 2017/10/31.
 */
public interface TOpRealityAccountDAO {
    /**
     * 根据缓存类型和数据类型查询实名账户信息
     * @param cacheTypeCd
     * @param cacheDataTypeCd
     * @return
     */
    List<TOpRealityAccountDO> getRealityAccountByType(@Param(value = "cacheTypeCd") String cacheTypeCd, @Param(value = "cacheDataTypeCd") String cacheDataTypeCd);
}