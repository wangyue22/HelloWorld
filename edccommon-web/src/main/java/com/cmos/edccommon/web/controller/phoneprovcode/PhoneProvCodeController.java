package com.cmos.edccommon.web.controller.phoneprovcode;
import com.alibaba.dubbo.config.annotation.Reference;
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

	@Reference(group = "edcco")
	private IPhoneProvCodeSV phoneProvCodeSV;
	/**
	 * http://localhost:18080/co/getProvCodeByPhoneNum?phoneNum=12345678910 
	 * 根据电话号码返回该电话号码对应的省端编码
	 * @param phoneNum
	 * @return
	 */
		@RequestMapping(value = "/getProvCodeByPhoneNum", method = RequestMethod.GET)
		public String getProvCodeByPhoneNum(@RequestParam String phoneNum ) {
			System.out.println("****************************"+phoneNum);
			return phoneProvCodeSV.getProvCodeByPhoneNum(phoneNum);
		}
		

}
