package com.cmos.edccommon.dao.rnfsCfg;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cmos.edccommon.beans.rnfsCfg.RnfsCfgInDTO;
import com.cmos.edccommon.beans.rnfsCfg.TOpRnfsCfgDO;

/**
 * Created by guozong on 2017/10/31.
 */
public interface TOpRnfsCfgDAO {

    List<TOpRnfsCfgDO> getRnfsGrpNm(@Param(value = "cacheTypeCd") String cacheTypeCd, @Param(value = "cacheDataTypeCd") String cacheDataTypeCd);
    List<TOpRnfsCfgDO> getRnfsGrpNmByrnfsGrpNm(String rnfsGrpNm);
    List<TOpRnfsCfgDO> getRnfsGrpNmByType(@Param(value = "cacheTypeCd") String cacheTypeCd, @Param(value = "cacheDataTypeCd") String cacheDataTypeCd);

    /**查询db配置表信息
     * @param dto
     * @return
     */
    List<TOpRnfsCfgDO> select(RnfsCfgInDTO dto);

    /**新增db配置
     * @param dto
     */
    void insert(RnfsCfgInDTO dto);

    /**删除db配置
     * @param dto
     */
    void delete(RnfsCfgInDTO dto);

    /**更新db配置
     * @param dto
     */
    void update(RnfsCfgInDTO dto);
}