package com.cmos.edccommon.service.impl.realityAccount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.edccommon.beans.realityAccount.RealityAccountDO;
import com.cmos.edccommon.dao.realityAccount.RealityAccountDAO;
import com.cmos.edccommon.iservice.realityAccount.IRealityAccountSV;

/**
 * Created by guozong on 2017/10/31.
 */
@Service(group = "edcco")
public class RealityAccountSVImpl implements IRealityAccountSV {
    @Autowired
    RealityAccountDAO realityAccountDAO;
    @Override
    public List<RealityAccountDO> getRealityAccountByType(String cacheTypeCd, String cacheDataTypeCd) {
        List<RealityAccountDO> list = realityAccountDAO.getRealityAccountByType(cacheTypeCd, cacheDataTypeCd);
        return list;
    }

    /**
     * 根据缓存key获取实名账户信息
     */
    @Override
    public RealityAccountDO getRealityAccountBycacheKey(String cacheKeyVal) {
        return realityAccountDAO.getRealityAccountBycacheKey(cacheKeyVal);
    };

    /* 查询实名账户信息 */
    @Override
    public List<RealityAccountDO> getRealityAccount(RealityAccountDO realityAccount) {
        return realityAccountDAO.getRealityAccount(realityAccount);
    }

    /* 新增实名账户信息 */
    @Override
    public void saveRealityAccount(RealityAccountDO realityAccount) {
        realityAccountDAO.saveRealityAccount(realityAccount);
    }

    /* 删除实名账户信息(将标识位置为0) */
    @Override
    public void deleteRealityAccount(RealityAccountDO realityAccount) {
        realityAccountDAO.deleteRealityAccount(realityAccount);
    }

    /* 更新实名账户信息 */
    @Override
    public void updaterealityAccount(RealityAccountDO realityAccount) {
        realityAccountDAO.updateRealityAccount(realityAccount);
    }
}
