package com.cmos.edccommon.service.impl.realityAccount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.edccommon.beans.realityAccount.RealityAccountInDTO;
import com.cmos.edccommon.beans.realityAccount.TOpRealityAccountDO;
import com.cmos.edccommon.dao.realityAccount.TOpRealityAccountDAO;
import com.cmos.edccommon.iservice.realityAccount.ITOpRealityAccountSV;

/**
 * Created by guozong on 2017/10/31.
 */
@Service(group = "edcco")
public class TOpRealityAccountSVImpl implements ITOpRealityAccountSV {
    @Autowired
    TOpRealityAccountDAO tOpRealityAccountDAO;
    @Override
    public List<TOpRealityAccountDO> getRealityAccountByType(String cacheTypeCd, String cacheDataTypeCd) {
        List<TOpRealityAccountDO> list = tOpRealityAccountDAO.getRealityAccountByType(cacheTypeCd,cacheDataTypeCd);
        return list;
    }

    @Override
    public List<TOpRealityAccountDO> getRealityAccount(RealityAccountInDTO dto) {
        return tOpRealityAccountDAO.select(dto);
    }

    @Override
    public void insertRealityAccount(RealityAccountInDTO dto) {
        tOpRealityAccountDAO.insert(dto);

    }

    @Override
    public void deleteRealityAccount(RealityAccountInDTO dto) {
        tOpRealityAccountDAO.delete(dto);
    }

    @Override
    public void updaterealityAccount(RealityAccountInDTO dto) {
        tOpRealityAccountDAO.update(dto);
    }
}
