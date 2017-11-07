package com.cmos.edccommon.web.controller.crkey;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cmos.common.exception.GeneralException;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.common.EdcCoOutDTO;
import com.cmos.edccommon.beans.crkey.RsaKeyDO;
import com.cmos.edccommon.web.cache.CacheFatctoryUtil;
import com.github.pagehelper.StringUtil;

/**
 * 获取秘钥
 *
 * @author xdx
 *
 */
@RestController
@RequestMapping()
public class KeyInfoController {
    @Autowired
    CacheFatctoryUtil cacheFatctoryUtil;
    Logger log=LoggerFactory.getActionLog(KeyInfoController.class);

    /**
     * http://localhost:18080/co/getRsaKey?reqstSrcCode=371
     * 获取加密秘钥 需要区分业务类型和秘钥类型时
     * @param inParam
     * @return
     * @throws GeneralException
     */
    @RequestMapping(value = "/getRsaKey", method = RequestMethod.POST)
    public EdcCoOutDTO getRsaKey(@RequestBody RsaKeyDO inParam) throws GeneralException {
        EdcCoOutDTO outParam = new EdcCoOutDTO();

        if (inParam == null || StringUtil.isEmpty(inParam.getReqstSrcCode())) {
            throw new GeneralException("2999", "参数异常");
        }
        String reqstSrcCode = inParam.getReqstSrcCode();// 请求源
        String bizTypeCd = inParam.getBizTypeCd();// 业务类型
        String cacheKey = "CO_RSAKEY:371";
        Map<String, String> bean = cacheFatctoryUtil.getJVMMap(cacheKey);
        outParam.setBean(bean);
        outParam.setReturnCode("0000");
        outParam.setReturnMessage("success");
        return outParam;
    }

    @RequestMapping(value = "/getDesKey", method = RequestMethod.POST)
    public EdcCoOutDTO getDesKey(@RequestBody RsaKeyDO inParam) throws GeneralException {
        EdcCoOutDTO outParam = new EdcCoOutDTO();
        outParam.setReturnCode("2999");
        outParam.setReturnMessage("未能查到该秘钥");
        if (inParam == null || StringUtil.isEmpty(inParam.getReqstSrcCode())) {
            throw new GeneralException("2999", "参数异常");
        }
        String reqstSrcCode = inParam.getReqstSrcCode();// 请求源
        String reqstSrcNm = inParam.getBizTypeCd();// 业务类型
        if (StringUtil.isNotEmpty(reqstSrcNm)) {
            reqstSrcNm = "_" + reqstSrcNm;
        } else {
            reqstSrcNm = "";
        }
        String cacheKey = "CO_REALACC:" + reqstSrcCode + reqstSrcNm;
        Map<String, String> bean = cacheFatctoryUtil.getJVMMap(cacheKey);

        if (bean != null) {
            Map<String, String> result = new HashMap<String, String>();
            String desKey = bean.get("desKey");
            if (StringUtil.isNotEmpty(desKey)) {
                desKey = desKey.trim();
                result.put("desKey", desKey);
                outParam.setBean(result);
                outParam.setReturnCode("0000");
                outParam.setReturnMessage("success");
            }
        }
        return outParam;
    }
}
