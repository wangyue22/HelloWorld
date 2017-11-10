package com.cmos.edccommon.web.controller.hlrinfo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cmos.common.bean.JsonFormatException;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.hlrinfo.HlrInfoDTO;
import com.cmos.edccommon.iservice.hlrinfo.IHlrInfoSV;
import com.cmos.edccommon.utils.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 根据电话号码获取省编码以及根据电话号码得到所属运营商
 * 
 * @author: zhaohu
 *
 * @created: 2017年10月27日
 */
@RestController
public class HlrInfoController {
	Logger log = LoggerFactory.getActionLog(HlrInfoController.class);
	@Reference(group = "edcco")
	private IHlrInfoSV hlrInfoSV;

	/**
	 * http://localhost:18080/getProvCodeByPhoneNum?phoneNum=1529305
	 * 根据电话号码返回该电话号码对应的省端编码
	 * 
	 * @param:phoneNum
	 * @return:HlrInfoDTO
	 */
	@RequestMapping(value = "/getProvCodeByPhoneNum", method = RequestMethod.POST)
	public HlrInfoDTO getProvCodeByPhoneNum(@RequestParam String phoneNum) {
		HlrInfoDTO hlrInfoDTO = new HlrInfoDTO();
		try {
			log.info("getProvCodeByPhoneNum方法入参为:" + phoneNum);
			String provCode = null;

			try {
				provCode = hlrInfoSV.getProvCodeByPhoneNum(phoneNum);
				log.info("省端编码provCode:" + provCode);
				if (StringUtils.isBlank(provCode)) {
					hlrInfoDTO.setReturnCode("2999");
					hlrInfoDTO.setReturnMessage("调用手机号获取省份返回值为空");
					log.info("getProvCodeByPhoneNum方法回参为:" + hlrInfoDTO.toJSONString());
				}
			} catch (Exception e) {
				hlrInfoDTO.setReturnCode("9999");
				hlrInfoDTO.setReturnMessage("调用手机号获取省份通用能力发生异常");
				log.info("getProvCodeByPhoneNum方法回参为:" + hlrInfoDTO.toJSONString());
			}
			hlrInfoDTO.getBean().put("provCode", provCode);
			log.info("getProvCodeByPhoneNum方法回参为:" + hlrInfoDTO.toJSONString());
		} catch (Exception e) {
			hlrInfoDTO.setReturnCode("2999");
			hlrInfoDTO.setReturnMessage("调用手机号获取省份通用能力发生异常");
			log.info("调用手机号获取省份通用能力发生异常", e);
		}
		return hlrInfoDTO;
	}

	/**
	 * 根据电话号码得到所属运营商
	 * 
	 * @param:
	 * @return:
	 */
	@RequestMapping(value = "/getHlrTypeByPhoneNum", method = RequestMethod.POST)
	public HlrInfoDTO getHlrTypeByPhoneNum(@RequestParam String phoneNum) {
		log.info("getHlrTypeByPhoneNum方法入参为:" + phoneNum);
		String hlrType = null;
		HlrInfoDTO hlrInfoDTO = new HlrInfoDTO();
		if(StringUtil.isBlank(phoneNum)){
			hlrInfoDTO.setReturnCode("2999");
			hlrInfoDTO.setReturnMessage("调用手机号获取运营商入参为空");
			log.info("调用手机号获取运营商入参为空");
			return hlrInfoDTO;
		}
		try {
			String phoneNumCut;
			if (phoneNum.length() == 13) {
				phoneNumCut = phoneNum.substring(0, 8);
			} else {
				phoneNumCut = phoneNum.substring(0, 7);
			}
			
			hlrType = hlrInfoSV.getHlrTypeByPhoneNum(phoneNumCut);
			log.info("手机所属hlrType:" + hlrType);
			if(StringUtils.isBlank(hlrType)){
				hlrInfoDTO.setReturnCode("2999");
				hlrInfoDTO.setReturnMessage("调用手机号获取运营商返回值为空");
				log.info("getHlrTypeByPhoneNum方法回参为:" + hlrInfoDTO.toJSONString());
			}
		} catch (Exception e) {
			hlrInfoDTO.setReturnCode("9999");
			hlrInfoDTO.setReturnMessage("调用手机号获取所属运营商通用能力发生异常");
			log.info("调用手机号获取所属运营商通用能力发生异常", e);
		}
		hlrInfoDTO.getBean().put("hlrType", hlrType);
		return hlrInfoDTO;
	}
}
