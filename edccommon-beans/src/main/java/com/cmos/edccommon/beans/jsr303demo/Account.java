package com.cmos.edccommon.beans.jsr303demo;

import com.cmos.common.bean.GenericBean;

import javax.validation.GroupSequence;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.cmos.common.validator.constraints.First;
import com.cmos.common.validator.constraints.Second;

/**
 * 账户类：测试分组顺序校验
 * 
 * @author likerui
 * @date2016年11月22日下午8:23:28
 */
@GroupSequence({First.class, Second.class, Account.class}) 
public class Account extends GenericBean {

	private static final long serialVersionUID = 1026478112681397070L;
	
	private Long id;

	@Length(min = 5, max = 20, message = "{user.name.length.illegal}", groups = { First.class })
	@Pattern(regexp = "[a-zA-Z]{5,20}", message = "{user.name.illegal}", groups = { Second.class })
	private String name;

	private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	

}
