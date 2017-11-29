package com.cmos.edccommon.iservice.realityAccount;

import java.util.List;

import com.cmos.edccommon.beans.realityAccount.RealityAccountDO;

/**
 * 运营管理 缓存实名账户信息
 * Created by guozong on 2017/10/31.
 */
public interface IRealityAccountSV {
    /**
     * 根据缓存类型和数据类型查询实名账户信息
     * @param cacheTypeCd
     * @param cacheDataTypeCd
     * @return
     */
    List<RealityAccountDO> getRealityAccountByType(String cacheTypeCd, String cacheDataTypeCd);

    /**查询db实名账户信息
     * @param realityAccount
     * @return
     */
    public List<RealityAccountDO> getRealityAccount(RealityAccountDO realityAccount);

    /**新增db实名账户信息
     * @param realityAccount
     */
    public void saveRealityAccount(RealityAccountDO realityAccount);

    /**删除db实名账户信息(将标志位置为0)
     * @param realityAccount
     */
    public void deleteRealityAccount(RealityAccountDO realityAccount);

    /**修改db实名账户信息
     * @param realityAccount
     */
    public void updaterealityAccount(RealityAccountDO realityAccount);
    /**
     * 根据缓存key获取实名账户信息
     */
    RealityAccountDO getRealityAccountBycacheKey(String cacheKeyVal);
}
