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
 * @author zhaohu
 *
 */
@RestController
@RequestMapping(value = "/co",  method = RequestMethod.GET)
public class HlrInfoController {
    Logger log=LoggerFactory.getActionLog(HlrInfoController.class);
	@Reference(group = "edcco")
	private IHlrInfoSV hlrInfoSV;
	/**
	 * http://localhost:18080/co/getProvCodeByPhoneNum?phoneNum=1529305
	 * 根据电话号码返回该电话号码对应的省端编码
	 * @param phoneNum
	 * @return
	 * @throws JsonFormatException 
	 */
		@RequestMapping(value = "/getProvCodeByPhoneNum", method = RequestMethod.GET)
		public HlrInfoDTO getProvCodeByPhoneNum(@RequestParam String phoneNum ) throws JsonFormatException {
			log.info("getProvCodeByPhoneNum方法入参为:"+phoneNum);
			String provCode=null;
			HlrInfoDTO hlrInfoDTO=new HlrInfoDTO();
			try{
				provCode=hlrInfoSV.getProvCodeByPhoneNum(phoneNum);
				log.info("省端编码provCode:"+ provCode);
			}catch(Exception e){
				hlrInfoDTO.setReturnCode("9999");
				hlrInfoDTO.setReturnMessage("调用手机号获取省份通用能力发生异常");
				log.info("getProvCodeByPhoneNum方法回参为:"+hlrInfoDTO.toJSONString());
				return hlrInfoDTO;
			}
			hlrInfoDTO.getBean().put("provCode", provCode);
			log.info("getProvCodeByPhoneNum方法回参为:"+ hlrInfoDTO.toJSONString());
			return hlrInfoDTO;
		}
}
