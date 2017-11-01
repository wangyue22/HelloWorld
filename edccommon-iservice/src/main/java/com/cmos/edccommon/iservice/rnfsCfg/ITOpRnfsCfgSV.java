package com.cmos.edccommon.iservice.rnfsCfg;

import com.cmos.edccommon.beans.rnfsCfg.TOpRnfsCfgDO;

import java.util.List;

/**
 * Created by guozong on 2017/10/31.
 */
public interface ITOpRnfsCfgSV {
    /**
     * 缓存list数据获取
     * @return
     */
    List<TOpRnfsCfgDO> getRnfsGrpNmByrnfsGrpNm(String cacheTypeCd, String cacheDataTypeCd);

    /**
     * 缓存map 数据获取
     * @param cacheTypeCd
     * @param cacheDataTypeCd
     * @return
     */
    List<TOpRnfsCfgDO> getRnfsGrpNmByType(String cacheTypeCd, String cacheDataTypeCd);
}
