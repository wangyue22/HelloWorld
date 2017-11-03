package com.cmos.edccommon.dao.crkey;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cmos.edccommon.beans.crkey.CoRsaKeyDO;
import com.cmos.edccommon.beans.crkey.KeyInfoDTO;
import com.cmos.edccommon.beans.crkey.RsaKeyInDTO;

/**
 * 获取秘钥
 */
public interface CoRsaKeyDAO {

    /**
     * 根据KeyInfoDTO获取秘钥
     * @param record
     * @return
     */
    CoRsaKeyDO selectKey(KeyInfoDTO record);
    /**
     * 获取初始化缓存数据
     * @param cacheTypeCd
     * @param cacheDataTypeCd
     * @return
     */
    List<CoRsaKeyDO> getKeyByType(@Param(value="cacheTypeCd")  String cacheTypeCd, @Param(value="cacheDataTypeCd")   String cacheDataTypeCd);

    /**查询db密钥
     * @param dto
     * @return
     */
    public List<CoRsaKeyDO> select(RsaKeyInDTO dto);

    /**新增db密钥
     * @param dto
     */
    public void insert(RsaKeyInDTO dto);

    /**删除db密钥(标志位置为0)
     * @param dto
     */
    public void delete(RsaKeyInDTO dto);

    /**更新db密钥
     * @param dto
     */
    public void update(RsaKeyInDTO dto);
}