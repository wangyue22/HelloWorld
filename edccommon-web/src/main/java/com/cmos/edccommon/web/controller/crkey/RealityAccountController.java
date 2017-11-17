package com.cmos.edccommon.web.controller.crkey;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.common.EdcCoOutDTO;
//import com.cmos.edccommon.iservice.crkey.IRealityAccountSV;
import com.cmos.edccommon.utils.StringUtil;
import com.cmos.edccommon.utils.consts.KeyInfoConstants;
import com.cmos.edccommon.utils.enums.ReturnInfoEnums;
import com.cmos.edccommon.web.cache.CacheFatctoryUtil;
/**
 * author dongzhiqiang
 * email dongzhiqiang@cmos.chinamobile.com
 * 通用功能密钥获取service
 */
@RestController
@RequestMapping(value = "realityAccount")
@Api(description = "通用功能获取密钥服务")
public class RealityAccountController {
	
	private static Logger log=LoggerFactory.getActionLog(RealityAccountController.class);
    @Autowired
    private CacheFatctoryUtil cacheFatctoryUtil;

    @SuppressWarnings("unchecked")
	@ApiOperation(value = "根据请求源编码和请求源名称获取密钥")
    @RequestMapping(value = "/getRealityAccount", method = RequestMethod.POST)
    public EdcCoOutDTO getBySourceCode(@RequestParam String reqstSrcCode) {
        EdcCoOutDTO outParam = new EdcCoOutDTO();
    	outParam.setReturnCode(ReturnInfoEnums.PROCESS_FAILED.getCode());
        outParam.setReturnMessage(ReturnInfoEnums.PROCESS_FAILED.getMessage());
        
        log.info("RealityAccountController reqstSrcCode= " + reqstSrcCode);
        if (StringUtil.isEmpty(reqstSrcCode)) {
			outParam.setReturnCode(ReturnInfoEnums.PROCESS_INPARAM_ERROR.getCode());
			outParam.setReturnMessage(ReturnInfoEnums.PROCESS_INPARAM_ERROR.getMessage());
            return outParam;
        }
        String cacheKey = KeyInfoConstants.CACHEKEY.CO_REALACC_PREFIX + reqstSrcCode;
        Map<String, String> bean = cacheFatctoryUtil.getJVMMap(cacheKey);
        log.info("RealityAccountController cacheKey= " + cacheKey);
     
        if (bean != null) {
        	log.info("RealityAccountController bean = " + bean.toString());
            outParam.setBean(bean);
            outParam.setReturnCode(ReturnInfoEnums.PROCESS_SUCCESS.getCode());
            outParam.setReturnMessage(ReturnInfoEnums.PROCESS_SUCCESS.getMessage());
        }
        return outParam;
    }
}
