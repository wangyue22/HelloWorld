package com.cmos.edccommon.beans.jsr303demo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import com.cmos.common.bean.GenericBean;
/**
 * 
 * 请描述该类的功能描述
 * @author likerui
 * @date2016年11月22日上午9:53:03
 */
public class UserModel extends GenericBean{

	@NotBlank
	@Length(min = 5, max = 20)
	private String name;

	@NotBlank
	private String pwd;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
