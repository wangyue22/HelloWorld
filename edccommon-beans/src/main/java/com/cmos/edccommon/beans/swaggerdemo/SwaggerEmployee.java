package com.cmos.edccommon.beans.swaggerdemo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.cmos.common.bean.GenericBean;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.cmos.common.validator.constraints.First;
import com.cmos.common.validator.constraints.Second;

/**
 * 
 * 请描述该类的功能描述
 * @author likerui
 * @date2016年11月22日下午9:20:27
 */
@ApiModel(value = "员工信息", description = "员工信息")
public class SwaggerEmployee extends GenericBean {

	private static final long serialVersionUID = -1499392957182444732L;

	@ApiModelProperty(value = "员工id：新增时为null，修改时不能为null")
    @NotNull(message = "员工id不能为空", groups = Second.class)
	private Long id;

	@ApiModelProperty(value = "名", required = false)
    @NotBlank(message = "员工姓名不能为空", groups = {First.class, Second.class})
    @Length(min = 2, max = 10, message = "员工姓名必须是{min}到{max}个字符", groups = {First.class, Second.class})
	private String name;

	@ApiModelProperty(value = "手机号码", required = true)
	private String mobile;

	@ApiModelProperty(value = "电子邮件",required = false)
	private String email;
	
	@ApiModelProperty(value = "加入系统时间",required = false)
	private Date createDate;

	public SwaggerEmployee() {
	}

	public SwaggerEmployee(Long id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
	}
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}