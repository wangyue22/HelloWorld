package com.cmos.edccommon.iservice.crkey;

import java.util.List;

import com.cmos.edccommon.beans.crkey.CoRsaKeyDO;
import com.cmos.edccommon.beans.crkey.KeyInfoDTO;
import com.cmos.edccommon.beans.crkey.RsaKeyInDTO;

/**
 * 根据手机号得到对应的省端编码
 *
 * @author xdx
 *
 */
public interface IKeyInfoSV {


    /**
     * @param param
     * @return
     * @throws Exception
     * @date 2017-10-17 17:00:00
     */
    public  CoRsaKeyDO getRsaKey(KeyInfoDTO inParam);
    /**
     * 获取初始化缓存数据
     * @param cacheTypeCd
     * @param cacheDataTypeCd
     * @return
     */
    List<CoRsaKeyDO> getKeyByType(String cacheTypeCd, String cacheDataTypeCd);

    /**查询db密钥
     * @param dto
     * @return
     */
    public List<CoRsaKeyDO> getRsaKey(RsaKeyInDTO dto);

    /**新增db密钥
     * @param dto
     */
    public void insertRsaKey(RsaKeyInDTO dto);

    /**删除db密钥
     * @param dto
     */
    public void deleteRsaKey(RsaKeyInDTO dto);

    /**更新db密钥
     * @param dto
     */
    public void updateRsaKey(RsaKeyInDTO dto);
    /**
     * 根据数据库key 获取秘钥
     */
    CoRsaKeyDO getKeyByCacheKey(String cacheKeyVal);
}
