package com.cmos.edccommon.dao.hlrinfo;

/**
 * 根据电话号码获取省编码以及根据电话号码得到所属运营商DAO
 * 
 * @author: zhaohu
 *
 * @created: 2017年10月27日
 */
public interface HlrInfoDODAO {
	/**
	 * 根据手机号得到对应的省端编码
	 * 
	 * @param:phoneNum
	 * @return:String
	 */
	public String getProvCodeByPhoneNum(String phoneNum);
	/**
	 * 根据手机号得到对应的手机运营商
	 * 
	 * @param:phoneNum
	 * @return:String
	 */
	public String getHlrTypeByPhoneNum(String phoneNum);
}