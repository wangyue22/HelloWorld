package com.cmos.edccommon.iservice.rnfsCfg;

import java.util.List;

import com.cmos.edccommon.beans.rnfsCfg.RnfsCfgDO;

/**
 * Created by guozong on 2017/10/31.
 */
public interface IRnfsCfgSV {
    /**
     * 缓存list数据获取
     * @return
     */
    @SuppressWarnings("rawtypes")
	List getRnfsGrpNmByrnfsGrpNm(String cacheTypeCd, String cacheDataTypeCd);

    /**
     * 缓存map 数据获取
     * @param cacheTypeCd
     * @param cacheDataTypeCd
     * @return
     */
    List<RnfsCfgDO> getRnfsGrpNmByType(String cacheTypeCd, String cacheDataTypeCd);

    /**查询db配置表
     * @param RnfsCfgOutDTO
     * @return
     */
    public List<RnfsCfgDO> getRnfsCfg(RnfsCfgDO rnfsCfg);

    /**
     * 新增db配置表数据
     * @param RnfsCfgOutDTO
     *
     */
    public void saveRnfsCfg(RnfsCfgDO rnfsCfg);

    /**
     * 删除db配置表数据
     * @param RnfsCfgOutDTO
     */
    public void deleteRnfsCfg(RnfsCfgDO rnfsCfg);

    /**更新db配置表数据
     * @param RnfsCfgOutDTO
     */
    public void updateRnfsCfg(RnfsCfgDO rnfsCfg);
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
