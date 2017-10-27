package com.cmos.edccommon.service.impl.hlrinfo;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.edccommon.dao.hlrinfo.HlrInfoDODAO;
import com.cmos.edccommon.iservice.hlrinfo.IHlrInfoSV;
/**
 * 根据电话号码获取省编码以及根据电话号码得到所属运营商服务实现
 * 
 * @author: zhaohu
 *
 * @created: 2017年10月27日
 */
@Service(group = "edcco")
public  class HlrInfoSVImpl implements  IHlrInfoSV {
	@Autowired
	private HlrInfoDODAO hlrInfoDoDao;
	 /**
	    * 根据手机号得到对应的省端编码
	    * @param:phoneNum
	    * @return:String
	    */
	public String getProvCodeByPhoneNum(String phoneNum){
		String provCode=null;
		provCode=hlrInfoDoDao.getProvCodeByPhoneNum(phoneNum);
		return provCode;
	}
	  /**
	   * 根据手机号得到对应的手机运营商
	   * @param:phoneNum
	   * @return:String
	   */
	public String getHlrTypeByPhoneNum(String phoneNum) {
		String hlrType=null;
		hlrType=hlrInfoDoDao.getHlrTypeByPhoneNum(phoneNum);
		return hlrType;
	}
}
