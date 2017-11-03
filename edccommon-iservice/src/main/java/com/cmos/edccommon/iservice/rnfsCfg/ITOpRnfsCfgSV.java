package com.cmos.edccommon.iservice.rnfsCfg;

import java.util.List;

import com.cmos.edccommon.beans.rnfsCfg.RnfsCfgInDTO;
import com.cmos.edccommon.beans.rnfsCfg.TOpRnfsCfgDO;

/**
 * Created by guozong on 2017/10/31.
 */
public interface ITOpRnfsCfgSV {
    /**
     * 缓存list数据获取
     * @return
     */
    List getRnfsGrpNmByrnfsGrpNm(String cacheTypeCd, String cacheDataTypeCd);

    /**
     * 缓存map 数据获取
     * @param cacheTypeCd
     * @param cacheDataTypeCd
     * @return
     */
    List<TOpRnfsCfgDO> getRnfsGrpNmByType(String cacheTypeCd, String cacheDataTypeCd);

    /**查询db配置表
     * @param RnfsCfgOutDTO
     * @return
     */
    public List<TOpRnfsCfgDO> getRnfsCfg(RnfsCfgInDTO rnfsCfgDto);

    /**
     * 新增db配置表数据
     * @param RnfsCfgOutDTO
     *
     */
    public void insertgetRnfsCfg(RnfsCfgInDTO rnfsCfgDto);

    /**
     * 删除db配置表数据
     * @param RnfsCfgOutDTO
     */
    public void deletegetRnfsCfg(RnfsCfgInDTO rnfsCfgDto);

    /**更新db配置表数据
     * @param RnfsCfgOutDTO
     */
    public void updategetRnfsCfg(RnfsCfgInDTO rnfsCfgDto);
}
