package com.cmos.edccommon.service.impl.rnfsCfg;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.edccommon.beans.rnfsCfg.RnfsCfgDO;
import com.cmos.edccommon.dao.rnfsCfg.RnfsCfgDAO;
import com.cmos.edccommon.iservice.rnfsCfg.IRnfsCfgSV;

/**
 * 运营管理 rnfs缓存配置实现类
 * Created by guozong on 2017/10/31.
 */
@Service(group = "edcco")
public class RnfsCfgSVImpl implements IRnfsCfgSV {

    @Autowired
    private RnfsCfgDAO rnfsCfgDAO;

    public List<RnfsCfgDO> getRnfsGrpNm() {
        return null;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public List getRnfsGrpNmByrnfsGrpNm(String cacheTypeCd, String cacheDataTypeCd) {
        List returnList = new ArrayList();
        List<RnfsCfgDO> grpNm = rnfsCfgDAO.getRnfsGrpNm(cacheTypeCd, cacheDataTypeCd);
        List<RnfsCfgDO> all;
        for(int i=0;i<grpNm.size();i++){
            if(grpNm.get(i)!=null) {
                all = rnfsCfgDAO.getRnfsGrpNmByrnfsGrpNm(grpNm.get(i).getRnfsGrpNm());
                returnList.add(all);
            }
        }
        return returnList;
    }

    @Override
    public List<RnfsCfgDO> getRnfsGrpNmByType(String cacheTypeCd, String cacheDataTypeCd) {
        return rnfsCfgDAO.getRnfsGrpNmByType(cacheTypeCd, cacheDataTypeCd);
    }

    /* 查询rnfs信息配置表 */
    @Override
    public List<RnfsCfgDO> getRnfsCfg(RnfsCfgDO rnfsCfg) {
        return rnfsCfgDAO.getRnfsCfg(rnfsCfg);
    }

    /* 新增rnfs配置信息 */
    @Override
    public void saveRnfsCfg(RnfsCfgDO rnfsCfg) {
        rnfsCfgDAO.saveRnfsCfg(rnfsCfg);
    }

    /* 删除rnfs配置信息(标志位置为0) */
    @Override
    public void deleteRnfsCfg(RnfsCfgDO rnfsCfg) {
        rnfsCfgDAO.deleteRnfsCfg(rnfsCfg);
    }

    /* 更新rnfs配置信息 */
    @Override
    public void updateRnfsCfg(RnfsCfgDO rnfsCfg) {
        rnfsCfgDAO.updateRnfsCfg(rnfsCfg);
    }
    @Override
    public RnfsCfgDO getRnfsGrpNmByAlsCacheKeyVal(String alsCacheKeyVal) {
        return rnfsCfgDAO.getRnfsGrpNmByAlsCacheKeyVal(alsCacheKeyVal);
    }

    @Override
    public List<RnfsCfgDO> getRnfsGrpNmByGrpCacheKeyVal(String alsCacheKeyVal) {
        return rnfsCfgDAO.getRnfsGrpNmByGrpCacheKeyVal(alsCacheKeyVal);
    }

}
