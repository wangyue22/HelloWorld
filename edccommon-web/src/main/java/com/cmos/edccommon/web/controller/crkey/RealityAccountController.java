package com.cmos.edccommon.web.controller.crkey;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.common.EdcCoOutDTO;
import com.cmos.edccommon.beans.realityAccount.RealityAccInDTO;
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
@Api(description = "通用功能获取用户名，密码和密钥服务")
public class RealityAccountController {
	
	private static Logger log=LoggerFactory.getActionLog(RealityAccountController.class);
    @Autowired
    private CacheFatctoryUtil cacheFatctoryUtil;

    @SuppressWarnings("unchecked")
	@ApiOperation(value = "根据请求源编码和请求源名称获取密钥")
    @RequestMapping(value = "/getRealityAccount", method = RequestMethod.POST)
    public EdcCoOutDTO getBySourceCode(@RequestBody RealityAccInDTO inParam) {
    	long startTime = System.currentTimeMillis();
        EdcCoOutDTO outParam = new EdcCoOutDTO();
    	outParam.setReturnCode(ReturnInfoEnums.PROCESS_FAILED.getCode());
        outParam.setReturnMessage(ReturnInfoEnums.PROCESS_FAILED.getMessage());
        
        if (inParam==null||StringUtil.isEmpty(inParam.getReqstSrcCode())) {
			outParam.setReturnCode(ReturnInfoEnums.PROCESS_INPARAM_ERROR.getCode());
			outParam.setReturnMessage(ReturnInfoEnums.PROCESS_INPARAM_ERROR.getMessage());
            return outParam;
        }
        String reqstSrcCode = inParam.getReqstSrcCode();
		String swftno = inParam.getSwftno();
		log.info("RealityAccountController reqstSrcCode= " + reqstSrcCode + ",流水号：" + swftno);
        String cacheKey = KeyInfoConstants.CACHEKEY.CO_REALACC_PREFIX + reqstSrcCode;
        Map<String, String> bean = cacheFatctoryUtil.getJVMMap(cacheKey);
        log.info("RealityAccountController cacheKey= " + cacheKey);
     
        if (bean != null && !bean.isEmpty()) {
        	log.info("RealityAccountController bean = " + bean.toString());
            outParam.setBean(bean);
            outParam.setReturnCode(ReturnInfoEnums.PROCESS_SUCCESS.getCode());
            outParam.setReturnMessage(ReturnInfoEnums.PROCESS_SUCCESS.getMessage());
        }
        long endTime = System.currentTimeMillis();
		log.info("=============getRealityAccount，获取账户信息 ，流水号为：" + swftno + "，调用时长为：" + (endTime - startTime) + " ms=================");
//		log.info("=============getRealityAccount，获取账户信息 ，响应结果为："+ JSONObject.toJSONString(outParam));
		return outParam;
    }
}
