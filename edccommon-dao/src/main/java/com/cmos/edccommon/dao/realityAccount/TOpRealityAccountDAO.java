package com.cmos.edccommon.dao.realityAccount;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cmos.edccommon.beans.realityAccount.RealityAccountInDTO;
import com.cmos.edccommon.beans.realityAccount.TOpRealityAccountDO;

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

    /**查询db实名账户信息
     * @param dto
     * @return
     */
    List<TOpRealityAccountDO> select(RealityAccountInDTO dto);

    /**新增db实名账户信息
     * @param dto
     */
    void insert(RealityAccountInDTO dto);

    /**删除db实名账户信息
     * @param dto
     */
    void delete(RealityAccountInDTO dto);

    /**更改sb实名账户信息
     * @param dto
     */
    void update(RealityAccountInDTO dto);
    /**
     * 根据缓存key获取实名账户信息
     */
    RealityAccountInDTO getRealityAccountBycacheKey(String cacheKeyVal);
}