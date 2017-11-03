package com.cmos.edccommon.service.impl.rnfsCfg;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.edccommon.beans.rnfsCfg.RnfsCfgInDTO;
import com.cmos.edccommon.beans.rnfsCfg.TOpRnfsCfgDO;
import com.cmos.edccommon.dao.rnfsCfg.TOpRnfsCfgDAO;
import com.cmos.edccommon.iservice.rnfsCfg.ITOpRnfsCfgSV;

/**
 * Created by guozong on 2017/10/31.
 */
@Service(group = "edcco")
public class TOpRnfsCfgSVImpl implements ITOpRnfsCfgSV {

    @Autowired
    private TOpRnfsCfgDAO tOpRnfsCfgDAO;
    public List<TOpRnfsCfgDO> getRnfsGrpNm() {
        return null;
    }

    @Override
    public List getRnfsGrpNmByrnfsGrpNm(String cacheTypeCd, String cacheDataTypeCd) {
        List returnList = new ArrayList();
        List<TOpRnfsCfgDO> grpNm = tOpRnfsCfgDAO.getRnfsGrpNm(cacheTypeCd,cacheDataTypeCd);
        List<TOpRnfsCfgDO> all = new ArrayList<TOpRnfsCfgDO>();
        for(int i=0;i<grpNm.size();i++){
            if(grpNm.get(i)!=null) {
                all = tOpRnfsCfgDAO.getRnfsGrpNmByrnfsGrpNm(grpNm.get(i).getRnfsGrpNm());
                returnList.add(all);
            }
        }
        return returnList;
    }

    @Override
    public List<TOpRnfsCfgDO> getRnfsGrpNmByType(String cacheTypeCd, String cacheDataTypeCd) {
        return tOpRnfsCfgDAO.getRnfsGrpNmByType(cacheTypeCd, cacheDataTypeCd);
    }

    @Override
    public List<TOpRnfsCfgDO> getRnfsCfg(RnfsCfgInDTO rnfsCfgDto) {
        return tOpRnfsCfgDAO.select(rnfsCfgDto);
    }

    @Override
    public void insertgetRnfsCfg(RnfsCfgInDTO rnfsCfgDto) {
        tOpRnfsCfgDAO.insert(rnfsCfgDto);
    }

    @Override
    public void deletegetRnfsCfg(RnfsCfgInDTO rnfsCfgDto) {
        tOpRnfsCfgDAO.delete(rnfsCfgDto);
    }

    @Override
    public void updategetRnfsCfg(RnfsCfgInDTO rnfsCfgDto) {
        tOpRnfsCfgDAO.update(rnfsCfgDto);
    }
    @Override
    public TOpRnfsCfgDO getRnfsGrpNmByAlsCacheKeyVal(String alsCacheKeyVal) {
        return tOpRnfsCfgDAO.getRnfsGrpNmByAlsCacheKeyVal(alsCacheKeyVal);
    }

    @Override
    public List<TOpRnfsCfgDO> getRnfsGrpNmByGrpCacheKeyVal(String alsCacheKeyVal) {
        return tOpRnfsCfgDAO.getRnfsGrpNmByGrpCacheKeyVal(alsCacheKeyVal);
    }

}
