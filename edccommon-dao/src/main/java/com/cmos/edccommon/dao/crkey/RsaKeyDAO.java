package com.cmos.edccommon.dao.crkey;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cmos.edccommon.beans.crkey.RsaKeyDO;

/**
 * 获取秘钥
 */
public interface RsaKeyDAO {

    /**
     * 根据KeyInfoDTO获取秘钥
     * @param record
     * @return
     */
    RsaKeyDO selectKey(RsaKeyDO record);
    /**
     * 获取初始化缓存数据
     * @param cacheTypeCd
     * @param cacheDataTypeCd
     * @return
     */
    List<RsaKeyDO> getKeyByType(@Param(value = "cacheTypeCd") String cacheTypeCd,
        @Param(value = "cacheDataTypeCd") String cacheDataTypeCd);

    /**查询db密钥
     * @param dto
     * @return
     */
    public List<RsaKeyDO> getRsaKey(RsaKeyDO rsaKey);

    /**新增db密钥
     * @param dto
     */
    public void saveRsaKey(RsaKeyDO rsaKey);

    /**删除db密钥(标志位置为0)
     * @param dto
     */
    public void deleteRsaKey(RsaKeyDO rsaKey);

    /**更新db密钥
     * @param dto
     */
    public void updateRsaKey(RsaKeyDO rsaKey);
    /**
     * 根据数据库key 获取秘钥
     */
    RsaKeyDO getKeyByCacheKey(String cacheKeyVal);
}