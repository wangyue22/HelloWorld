package com.cmos.edccommon.service.impl.crkey;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.crkey.CoRsaKeyDO;
import com.cmos.edccommon.beans.crkey.KeyInfoDTO;
import com.cmos.edccommon.beans.crkey.RsaKeyInDTO;
import com.cmos.edccommon.dao.crkey.CoRsaKeyDAO;
import com.cmos.edccommon.iservice.crkey.IKeyInfoSV;

/**
 * 根据请求源编码，秘钥类型，和省编码获取秘钥
 * @author xdx
 *
 */
@Service(group = "edcco")
public  class KeyInfoSVImpl implements  IKeyInfoSV {
    @Autowired
    private CoRsaKeyDAO keyDAO;

    Logger logger = LoggerFactory.getActionLog(KeyInfoSVImpl.class);

    /**
     * 获取RSA秘钥
     */
    @Override
    public CoRsaKeyDO getRsaKey(KeyInfoDTO inParam) {

        CoRsaKeyDO result = keyDAO.selectKey(inParam);
        if (result != null) {
            logger.debug("key=" + result.getPrtkey());
        }
        return result;
    }
    @Override
    public List<CoRsaKeyDO> getKeyByType(String cacheTypeCd, String cacheDataTypeCd) {
        return keyDAO.getKeyByType(cacheTypeCd,cacheDataTypeCd);
    }

    @Override
    public List<CoRsaKeyDO> getRsaKey(RsaKeyInDTO dto) {
        return keyDAO.select(dto);
    }

    @Override
    public void insertRsaKey(RsaKeyInDTO dto) {
        keyDAO.insert(dto);
    }

    @Override
    public void deleteRsaKey(RsaKeyInDTO dto) {
        keyDAO.delete(dto);
    }

    @Override
    public void updateRsaKey(RsaKeyInDTO dto) {
        keyDAO.update(dto);
    }

}
