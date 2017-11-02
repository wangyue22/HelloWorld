package com.cmos.edccommon.service.impl.realityAccount;

import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.edccommon.beans.realityAccount.TOpRealityAccountDO;
import com.cmos.edccommon.dao.realityAccount.TOpRealityAccountDAO;
import com.cmos.edccommon.iservice.realityAccount.ITOpRealityAccountSV;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
}
