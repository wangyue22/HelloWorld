package com.cmos.edccommon.dao.rnfsCfg;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cmos.edccommon.beans.rnfsCfg.RnfsCfgDO;

/**
 * Created by guozong on 2017/10/31.
 */
public interface RnfsCfgDAO {

    List<RnfsCfgDO> getRnfsGrpNm(@Param(value = "cacheTypeCd") String cacheTypeCd,
        @Param(value = "cacheDataTypeCd") String cacheDataTypeCd);

    List<RnfsCfgDO> getRnfsGrpNmByrnfsGrpNm(String rnfsGrpNm);

    List<RnfsCfgDO> getRnfsGrpNmByType(@Param(value = "cacheTypeCd") String cacheTypeCd,
        @Param(value = "cacheDataTypeCd") String cacheDataTypeCd);

    /**查询db配置表信息
     * @param dto
     * @return
     */
    List<RnfsCfgDO> getRnfsCfg(RnfsCfgDO rnfsCfg);

    /**新增db配置
     * @param dto
     */
    void saveRnfsCfg(RnfsCfgDO rnfsCfg);

    /**删除db配置
     * @param dto
     */
    void deleteRnfsCfg(RnfsCfgDO rnfsCfg);

    /**更新db配置
     * @param dto
     */
    void updateRnfsCfg(RnfsCfgDO rnfsCfg);
    /**根据key获取单个对象
     * @param
     * @return
     */
    RnfsCfgDO getRnfsGrpNmByAlsCacheKeyVal(String alsCacheKeyVal);
    /**根据key获取缓存list
     * @param
     * @return
     */
    List<RnfsCfgDO> getRnfsGrpNmByGrpCacheKeyVal(String alsCacheKeyVal);
}