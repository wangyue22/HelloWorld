package com.cmos.edccommon.service.impl.hlrinfo;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.edccommon.dao.hlrinfo.HlrInfoDODAO;
import com.cmos.edccommon.iservice.hlrinfo.IHlrInfoSV;
/**
 * 根据电话号码得到省端编码
 *
 * @author zhaohu
 * 
 */
@Service(group = "edcco")
public  class HlrInfoSVImpl implements  IHlrInfoSV {
	@Autowired
	private HlrInfoDODAO hlrInfoDoDao;
	public String getProvCodeByPhoneNum(String phoneNum) throws Exception{
		String provCode=null;
		provCode=hlrInfoDoDao.getProvCodeByPhoneNum(phoneNum);
		return provCode;
	}
}
