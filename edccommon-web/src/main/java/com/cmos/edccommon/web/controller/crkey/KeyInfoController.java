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
import com.cmos.edccommon.beans.crkey.KeyInfoDTO;
import com.cmos.edccommon.utils.consts.KeyInfoConstants;
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
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/getRsaKey", method = RequestMethod.POST)
    public EdcCoOutDTO getRsaKey(@RequestBody KeyInfoDTO inParam) throws GeneralException {
        EdcCoOutDTO outParam = new EdcCoOutDTO();

        if (inParam == null || StringUtil.isEmpty(inParam.getReqstSrcCode())) {
            throw new GeneralException("2999", "参数异常");
        }
        String reqstSrcCode = inParam.getReqstSrcCode();// 请求源
        String bizTypeCd = inParam.getBizTypeCd();// 业务类型
        
		if (StringUtil.isNotEmpty(bizTypeCd)) {
			bizTypeCd = KeyInfoConstants.CACHEKEY.SEPARATOR + bizTypeCd;
		} else {
			bizTypeCd = "";
		}
		String cacheKey = KeyInfoConstants.CACHEKEY.CO_RSAKEY_PREFIX + reqstSrcCode + bizTypeCd;
		
        Map<String, String> bean = cacheFatctoryUtil.getJVMMap(cacheKey);
        outParam.setBean(bean);
        outParam.setReturnCode("0000");
        outParam.setReturnMessage("success");
        return outParam;
    }

    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDesKey", method = RequestMethod.POST)
    public EdcCoOutDTO getDesKey(@RequestBody KeyInfoDTO inParam) throws GeneralException {
        EdcCoOutDTO outParam = new EdcCoOutDTO();
        outParam.setReturnCode("2999");
        outParam.setReturnMessage("未能查到该秘钥");
        if (inParam == null || StringUtil.isEmpty(inParam.getReqstSrcCode())) {
            throw new GeneralException("2999", "参数异常");
        }
        String reqstSrcCode = inParam.getReqstSrcCode();// 请求源
        String reqstSrcNm = inParam.getBizTypeCd();// 业务类型
        if (StringUtil.isNotEmpty(reqstSrcNm)) {
            reqstSrcNm = KeyInfoConstants.CACHEKEY.SEPARATOR  + reqstSrcNm;
        } else {
            reqstSrcNm = "";
        }
        String cacheKey = KeyInfoConstants.CACHEKEY.CO_REALACC_PREFIX + reqstSrcCode + reqstSrcNm;
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
