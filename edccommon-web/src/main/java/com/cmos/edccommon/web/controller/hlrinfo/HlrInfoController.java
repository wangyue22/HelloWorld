package com.cmos.edccommon.web.controller.hlrinfo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cmos.common.bean.JsonFormatException;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.hlrinfo.HlrInfoDTO;
import com.cmos.edccommon.iservice.hlrinfo.IHlrInfoSV;
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
	public HlrInfoDTO getProvCodeByPhoneNum(@RequestParam String phoneNum) throws JsonFormatException {
		log.info("getProvCodeByPhoneNum方法入参为:" + phoneNum);
		String provCode = null;
		HlrInfoDTO hlrInfoDTO = new HlrInfoDTO();
		try {
			provCode = hlrInfoSV.getProvCodeByPhoneNum(phoneNum);
			log.info("省端编码provCode:" + provCode);
		} catch (Exception e) {
			hlrInfoDTO.setReturnCode("9999");
			hlrInfoDTO.setReturnMessage("调用手机号获取省份通用能力发生异常");
			log.info("getProvCodeByPhoneNum方法回参为:" + hlrInfoDTO.toJSONString());
			return hlrInfoDTO;
		}
		hlrInfoDTO.getBean().put("provCode", provCode);
		log.info("getProvCodeByPhoneNum方法回参为:" + hlrInfoDTO.toJSONString());
		return hlrInfoDTO;
	}

	/**
	 * 根据电话号码得到所属运营商
	 * 
	 * @param:
	 * @return:
	 */
	@RequestMapping(value = "/getHlrTypeByPhoneNum", method = RequestMethod.POST)
	public HlrInfoDTO getHlrTypeByPhoneNum(@RequestParam String phoneNum) throws JsonFormatException {
		log.info("getHlrTypeByPhoneNum方法入参为:" + phoneNum);
		String hlrType = null;
		HlrInfoDTO hlrInfoDTO = new HlrInfoDTO();
		try {
			hlrType = hlrInfoSV.getHlrTypeByPhoneNum(phoneNum);
			log.info("手机所属hlrType:" + hlrType);
		} catch (Exception e) {
			hlrInfoDTO.setReturnCode("9999");
			hlrInfoDTO.setReturnMessage("调用手机号获取所属运营商通用能力发生异常");
			log.info("getProvCodeByPhoneNum方法回参为:" + hlrInfoDTO.toJSONString());
			return hlrInfoDTO;
		}
		hlrInfoDTO.getBean().put("hlrType", hlrType);
		log.info("getHlrTypeByPhoneNum方法回参为:" + hlrInfoDTO.toJSONString());
		return hlrInfoDTO;
	}
}
