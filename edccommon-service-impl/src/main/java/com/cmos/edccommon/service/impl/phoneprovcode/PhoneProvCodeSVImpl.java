package com.cmos.edccommon.service.impl.phoneprovcode;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.edccommon.dao.phoneprovcode.HlrInfoDODAO;
import com.cmos.edccommon.iservice.phoneprovcode.IPhoneProvCodeSV;

/**
 * 根据电话号码得到省端编码
 *
 * @author 赵虎
 * 
 */
@Service(group = "edcco")
public  class PhoneProvCodeSVImpl implements  IPhoneProvCodeSV {
	@Autowired
	private HlrInfoDODAO hlrInfoDoDao;
	@Override
	public String getProvCodeByPhoneNum(String phoneNum) throws Exception{
		String provCode=null;
		provCode=hlrInfoDoDao.getProvCodeByPhoneNum(phoneNum);
		return provCode;
	}
}
