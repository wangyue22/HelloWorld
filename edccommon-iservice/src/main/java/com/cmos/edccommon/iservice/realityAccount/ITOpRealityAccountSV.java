package com.cmos.edccommon.iservice.realityAccount;

import java.util.List;

import com.cmos.edccommon.beans.realityAccount.RealityAccountInDTO;
import com.cmos.edccommon.beans.realityAccount.TOpRealityAccountDO;

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

    /**查询db实名账户信息
     * @param dto
     * @return
     */
    public List<TOpRealityAccountDO> getRealityAccount(RealityAccountInDTO dto);

    /**新增db实名账户信息
     * @param dto
     */
    public void insertRealityAccount(RealityAccountInDTO dto);

    /**删除db实名账户信息(将标志位置为0)
     * @param dto
     */
    public void deleteRealityAccount(RealityAccountInDTO dto);

    /**修改db实名账户信息
     * @param dto
     */
    public void updaterealityAccount(RealityAccountInDTO dto);
}
