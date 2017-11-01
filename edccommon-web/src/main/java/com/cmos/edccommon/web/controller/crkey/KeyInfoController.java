package com.cmos.edccommon.web.controller.crkey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cmos.common.exception.GeneralException;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.crkey.CoRsaKeyDO;
import com.cmos.edccommon.beans.crkey.KeyInfoDTO;
import com.cmos.edccommon.iservice.crkey.IKeyInfoSV;
import com.cmos.edccommon.web.cache.CacheFatctoryUtil;
import com.github.pagehelper.StringUtil;

/**
 * 获取秘钥
 *
 * @author xdx
 *
 */
@RestController
@RequestMapping(value = "/co")
public class KeyInfoController {
    Logger log=LoggerFactory.getActionLog(KeyInfoController.class);
    @Reference(group = "edcco")
    private IKeyInfoSV keyInfoSV;

    @Autowired
    private CacheFatctoryUtil cacheFatctoryUtil;

	/**
     * http://localhost:18080/co/getRsaKey?reqstSrcCode=371
     * 获取加密秘钥 需要区分业务类型和秘钥类型时
     * @param inParam
     * @return
     * @throws GeneralException 
     */
    @RequestMapping(value = "/getRsaKey", method = RequestMethod.POST)
    public CoRsaKeyDO getRsaKey(@RequestBody KeyInfoDTO inParam) throws GeneralException {
        CoRsaKeyDO outParam = new CoRsaKeyDO();

        if (inParam == null || StringUtil.isEmpty(inParam.getReqstSrcCode())) {
            throw new GeneralException("2999", "参数异常");
        }
        String reqstSrcCode = inParam.getReqstSrcCode();// 请求源
        String bizTypeCd = inParam.getBizTypeCd();// 业务类型
        if (StringUtil.isEmpty(reqstSrcCode)) {
            throw new GeneralException("2999", "参数异常");
        }
        try {
            String cacheKey = reqstSrcCode + "_" + bizTypeCd;
            //			cacheFatctoryUtil.getJVMString(cacheKey);
            outParam = keyInfoSV.getRsaKey(inParam);
        } catch (Exception e) {
            log.error("getDesKey方法异常", e);
            throw new GeneralException("9999", "系统异常", e);
        }

        return outParam;
    }

}
