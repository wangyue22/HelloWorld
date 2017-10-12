package com.cmos.edccommon.web.controller.phoneprovcode;
import com.alibaba.dubbo.config.annotation.Reference;
import com.cmos.common.bean.JsonFormatException;
import com.cmos.commons.exception.BizException;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.phoneprovcode.PhoneProvCodeOutputObject;
import com.cmos.edccommon.iservice.phoneprovcode.IPhoneProvCodeSV;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author 赵虎
 *
 */
@RestController
@RequestMapping(value = "/co",  method = RequestMethod.GET)
public class PhoneProvCodeController {
    Logger log=LoggerFactory.getActionLog(PhoneProvCodeController.class);
	@Reference(group = "edcco")
	private IPhoneProvCodeSV phoneProvCodeSV;
	/**
	 * http://localhost:18080/co/getProvCodeByPhoneNum?phoneNum=1529305
	 * 根据电话号码返回该电话号码对应的省端编码
	 * @param phoneNum
	 * @return
	 * @throws JsonFormatException 
	 */
		@RequestMapping(value = "/getProvCodeByPhoneNum", method = RequestMethod.GET)
		public PhoneProvCodeOutputObject getProvCodeByPhoneNum(@RequestParam String phoneNum ) throws JsonFormatException {
			System.out.println("****************************"+phoneNum);
			log.info("getProvCodeByPhoneNum方法入参为:", phoneNum);
			String provCode=null;
			PhoneProvCodeOutputObject phoneProvCodeOB=new PhoneProvCodeOutputObject();
			try{
				provCode=phoneProvCodeSV.getProvCodeByPhoneNum(phoneNum);
				log.info("省端编码provCode:", provCode);
			}catch(Exception e){
				phoneProvCodeOB.setReturnCode("9999");
				phoneProvCodeOB.setReturnMessage("调用手机号获取省份通用能力发生异常");
				log.info("getProvCodeByPhoneNum方法回参为:", phoneProvCodeOB.toJSONString());
				return phoneProvCodeOB;
			}
			phoneProvCodeOB.getBean().put("provCode", provCode);
			log.info("getProvCodeByPhoneNum方法回参为:", phoneProvCodeOB.toJSONString());
			return phoneProvCodeOB;
		}
}
