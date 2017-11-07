package com.cmos.edccommon.web.controller.crkey;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmos.common.exception.GeneralException;
import com.cmos.edccommon.beans.common.EdcCoOutDTO;
//import com.cmos.edccommon.iservice.crkey.IRealityAccountSV;
import com.cmos.edccommon.utils.StringUtil;
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

    @Autowired
    private CacheFatctoryUtil cacheFatctoryUtil;

    @ApiOperation(value = "根据请求源编码和请求源名称获取密钥")
    @RequestMapping(value = "/getRealityAccount", method = RequestMethod.POST)
    public EdcCoOutDTO getBySourceCode(@RequestParam String reqstSrcCode) throws GeneralException {
        EdcCoOutDTO outParam = new EdcCoOutDTO();
        outParam.setReturnCode("2999");
        outParam.setReturnMessage("未能查到该用户名");
        if (StringUtil.isEmpty(reqstSrcCode)) {
            throw new GeneralException("2999", "参数异常");
        }
        String cacheKey = "CO_REALACC:" + reqstSrcCode;
        Map<String, String> bean = cacheFatctoryUtil.getJVMMap(cacheKey);
        if (bean != null) {
            outParam.setBean(bean);
            outParam.setReturnCode("0000");
            outParam.setReturnMessage("success");
        }
        return outParam;
    }
}
