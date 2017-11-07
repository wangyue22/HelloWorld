package com.cmos.edccommon.iservice.crkey;

import java.util.List;

import com.cmos.edccommon.beans.crkey.RsaKeyDO;

/**
 * 根据手机号得到对应的省端编码
 *
 * @author xdx
 *
 */
public interface IRsaKeySV {


    /**
     * @param param
     * @return
     * @throws Exception
     * @date 2017-10-17 17:00:00
     */
    public RsaKeyDO getRsaKeyByDto(RsaKeyDO inParam);
    /**
     * 获取初始化缓存数据
     * @param cacheTypeCd
     * @param cacheDataTypeCd
     * @return
     */
    List<RsaKeyDO> getKeyByType(String cacheTypeCd, String cacheDataTypeCd);

    /**查询db密钥
     * @param dto
     * @return
     */
    public List<RsaKeyDO> getRsaKey(RsaKeyDO rsaKey);

    /**新增db密钥
     * @param dto
     */
    public void saveRsaKey(RsaKeyDO rsaKey);

    /**删除db密钥
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
