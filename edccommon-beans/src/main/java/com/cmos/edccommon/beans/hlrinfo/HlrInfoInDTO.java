package com.cmos.edccommon.beans.hlrinfo;

import com.cmos.common.bean.GenericBean;
import com.cmos.common.domain.UserResult;
/**
 * 根据手机号得到省编码以及手机所属运营商统一入参
 * 
 * @author: xdx
 *
 * @created: 2017年12月09日
 */
public class HlrInfoInDTO extends GenericBean implements UserResult {

	private static final long serialVersionUID = -8802993848241508705L;
	/**
	 * 手机号
	 */
	private String phoneNum;

	/**
	 * 流水号
	 */
	private String swftno;

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getSwftno() {
		return swftno;
	}

	public void setSwftno(String swftno) {
		this.swftno = swftno;
	}

}
