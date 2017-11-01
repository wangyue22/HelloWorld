package com.cmos.edccommon.dao.rnfsCfg;

import com.cmos.edccommon.beans.rnfsCfg.TOpRnfsCfgDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by guozong on 2017/10/31.
 */
public interface TOpRnfsCfgDAO {
    /**
     * 根据缓存类型和数据类型查询出网缓存放的RnfsGrpNm类别
     * @param cacheTypeCd
     * @param cacheDataTypeCd
     * @return
     */
    List<TOpRnfsCfgDO> getRnfsGrpNm(@Param(value = "cacheTypeCd") String cacheTypeCd, @Param(value = "cacheDataTypeCd") String cacheDataTypeCd);
    /**
     * 根据nfsGrpNm类别查询网缓存存放的list
     * @param rnfsGrpNm
     * @return
     */
    List<TOpRnfsCfgDO> getRnfsGrpNmByrnfsGrpNm(String rnfsGrpNm);
    /**
     * 根据缓存类型和数据类型查询出网缓存放的数据
     * @param cacheTypeCd
     * @param cacheDataTypeCd
     * @return
     */
    List<TOpRnfsCfgDO> getRnfsGrpNmByType(@Param(value = "cacheTypeCd") String cacheTypeCd, @Param(value = "cacheDataTypeCd") String cacheDataTypeCd);
}