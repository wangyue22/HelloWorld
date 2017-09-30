package com.cmos.edccommon.beans.jsr303demo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import com.cmos.common.bean.GenericBean;
import com.cmos.common.validator.constraints.First;
import com.cmos.common.validator.constraints.Second;

/**
 * 学生实体类：验证分组校验
 * @author likerui
 * @date2016年11月22日上午9:53:03
 */
public class Student extends GenericBean{
	
	@NotNull(message = "学生id不能为空", groups = Second.class)
	private Long stuId;
	
	@NotBlank
	@Length(min = 2, max = 5, groups = {First.class, Second.class})
	private String name;
	
	private String mobile;

	
	public Long getStuId() {
		return stuId;
	}

	public void setStuId(Long stuId) {
		this.stuId = stuId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	


}
