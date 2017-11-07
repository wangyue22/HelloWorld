package com.cmos.edccommon.dao.realityAccount;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cmos.edccommon.beans.realityAccount.RealityAccountDO;

/**
 * Created by guozong on 2017/10/31.
 */
public interface RealityAccountDAO {
    /**
     * 根据缓存类型和数据类型查询实名账户信息
     * @param cacheTypeCd
     * @param cacheDataTypeCd
     * @return
     */
    List<RealityAccountDO> getRealityAccountByType(@Param(value = "cacheTypeCd") String cacheTypeCd, @Param(value = "cacheDataTypeCd") String cacheDataTypeCd);

    /**
     * 根据缓存key获取实名账户信息
     */
    RealityAccountDO getRealityAccountBycacheKey(String cacheKeyVal);

    /**查询db实名账户信息
     * @param dto
     * @return
     */
    List<RealityAccountDO> getRealityAccount(RealityAccountDO realityAccount);

    /**新增db实名账户信息
     * @param dto
     */
    void saveRealityAccount(RealityAccountDO realityAccount);

    /**删除db实名账户信息
     * @param dto
     */
    void deleteRealityAccount(RealityAccountDO realityAccount);

    /**更改sb实名账户信息
     * @param dto
     */
    void updateRealityAccount(RealityAccountDO realityAccount);
}