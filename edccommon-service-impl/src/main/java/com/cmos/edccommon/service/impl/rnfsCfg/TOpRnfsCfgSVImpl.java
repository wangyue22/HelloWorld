package com.cmos.edccommon.service.impl.rnfsCfg;

import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.edccommon.beans.rnfsCfg.TOpRnfsCfgDO;
import com.cmos.edccommon.dao.rnfsCfg.TOpRnfsCfgDAO;
import com.cmos.edccommon.iservice.rnfsCfg.ITOpRnfsCfgSV;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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
             all = tOpRnfsCfgDAO.getRnfsGrpNmByrnfsGrpNm(grpNm.get(i).getRnfsGrpNm());
            returnList.add(all);
        }
        return returnList;
    }

    @Override
    public List<TOpRnfsCfgDO> getRnfsGrpNmByType(String cacheTypeCd, String cacheDataTypeCd) {
        return tOpRnfsCfgDAO.getRnfsGrpNmByType(cacheTypeCd, cacheDataTypeCd);
    }
}
