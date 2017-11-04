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

    /**
     * 根据缓存key获取实名账户信息
     */
    @Override
    public TOpRealityAccountDO getRealityAccountBycacheKey(String cacheKeyVal) {
        return tOpRealityAccountDAO.getRealityAccountBycacheKey(cacheKeyVal);
    };

    /* 查询实名账户信息 */
    @Override
    public List<TOpRealityAccountDO> getRealityAccount(RealityAccountInDTO dto) {
        return tOpRealityAccountDAO.select(dto);
    }

    /* 新增实名账户信息 */
    @Override
    public void insertRealityAccount(RealityAccountInDTO dto) {
        tOpRealityAccountDAO.insert(dto);
    }

    /* 删除实名账户信息(将标识位置为0) */
    @Override
    public void deleteRealityAccount(RealityAccountInDTO dto) {
        tOpRealityAccountDAO.delete(dto);
    }

    /* 更新实名账户信息 */
    @Override
    public void updaterealityAccount(RealityAccountInDTO dto) {
        tOpRealityAccountDAO.update(dto);
    }

}
