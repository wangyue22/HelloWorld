package com.cmos.edccommon.service.impl.crkey;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.crkey.RsaKeyDO;
import com.cmos.edccommon.dao.crkey.RsaKeyDAO;
import com.cmos.edccommon.iservice.crkey.IRsaKeySV;

/**
 * 根据请求源编码，秘钥类型，和省编码获取秘钥
 * @author xdx
 *
 */
@Service(group = "edcco")
public  class RsaKeySVImpl implements  IRsaKeySV {
    @Autowired
    private RsaKeyDAO keyDAO;

    Logger logger = LoggerFactory.getActionLog(RsaKeySVImpl.class);

    /**
     * 获取RSA秘钥
     */
    @Override
    public RsaKeyDO getRsaKeyByDto(RsaKeyDO inParam) {

        RsaKeyDO result = keyDAO.selectKey(inParam);
        if (result != null) {
            logger.debug("key=" + result.getPrtkey());
        }
        return result;
    }
    @Override
    public List<RsaKeyDO> getKeyByType(String cacheTypeCd, String cacheDataTypeCd) {
        return keyDAO.getKeyByType(cacheTypeCd,cacheDataTypeCd);
    }

    @Override
    public List<RsaKeyDO> getRsaKey(RsaKeyDO rsaKey) {
        return keyDAO.getRsaKey(rsaKey);
    }

    @Override
    public void saveRsaKey(RsaKeyDO rsaKey) {
        keyDAO.saveRsaKey(rsaKey);
    }

    @Override
    public void deleteRsaKey(RsaKeyDO rsaKey) {
        keyDAO.deleteRsaKey(rsaKey);
    }

    @Override
    public void updateRsaKey(RsaKeyDO rsaKey) {
        keyDAO.updateRsaKey(rsaKey);
    }

    @Override
    public RsaKeyDO getKeyByCacheKey(String cacheKeyVal) {
        return keyDAO.getKeyByCacheKey(cacheKeyVal);
    }


}
