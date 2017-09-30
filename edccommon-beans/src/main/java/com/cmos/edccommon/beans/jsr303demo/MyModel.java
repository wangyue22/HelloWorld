package com.cmos.edccommon.beans.jsr303demo;

import javax.validation.GroupSequence;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import com.cmos.common.bean.GenericBean;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.cmos.common.validator.constraints.First;

/**
 * 请描述该类的功能描述
 * 
 * @author likerui
 * @date2016年11月22日上午9:53:28
 */
//@GroupSequence({ First.class, UserModel.class })
public class MyModel extends GenericBean{

	@NotBlank
	@Length(min = 5, max = 20, message = "用户名长度5-20位")
	@Pattern(regexp = "[a-zA-Z0-9]{5,20}", message = "用户名长度5-20位数字字母组合")
	private String myName;

	@NotBlank
	private String myPwd;
	
	
	private int age;
	

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public String getMyPwd() {
		return myPwd;
	}

	public void setMyPwd(String myPwd) {
		this.myPwd = myPwd;
	}
}
