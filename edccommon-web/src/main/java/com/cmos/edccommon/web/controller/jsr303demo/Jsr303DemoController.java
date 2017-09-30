package com.cmos.edccommon.web.controller.jsr303demo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmos.common.validator.constraints.First;
import com.cmos.common.validator.constraints.Second;
import com.cmos.edccommon.beans.jsr303demo.Account;
import com.cmos.edccommon.beans.jsr303demo.CompModel;
import com.cmos.edccommon.beans.jsr303demo.CompModelTwo;
import com.cmos.edccommon.beans.jsr303demo.MyModel;
import com.cmos.edccommon.beans.jsr303demo.Student;
import com.cmos.edccommon.beans.jsr303demo.UserModel;

import static com.cmos.common.utils.Asserts.*;

@RestController
@RequestMapping(value = "/jsr303", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "jsr303 参数校验参考demo")
public class Jsr303DemoController {

	@ApiOperation(value = "简单javaBean参数校验-从ModelAttribute获取数据，验证多个对象", notes = "@Valid+@ModelAttribute 方式的jsr303校验")
	@RequestMapping(value = "/test01", method = RequestMethod.GET)
	public String test01(@Valid  @ModelAttribute UserModel user,
			@Valid @ModelAttribute MyModel myModel) {
		return "sucess"; 
	}

	@ApiOperation(value = "简单javaBean参数校验-从ModelAttribute和RequestBody和获取数据，验证多个对象", notes = "@Valid+@ModelAttribute+RequestBody 方式的jsr303校验")
	@RequestMapping(value = "/test02", method = RequestMethod.POST)
	public String test02(@Valid @RequestBody MyModel myModel,
			@Valid @ModelAttribute UserModel user) {
		return "sucess";
	}


	@ApiOperation(value = "复合javaBean的校验（对象图级联校验）-从ModelAttribute和RequestBody和获取数据",
			notes = "复合javaBean的jsr303校验")
	@RequestMapping(value = "/test03", method = RequestMethod.POST)
	public String test03(@Valid @ModelAttribute UserModel user,
			@Valid @RequestBody CompModel compModel) {
		return "sucess";
	}

	@ApiOperation(value = "复合javaBean的校验（对象图级联校验）-复合JavaBean对象为数组的校验方式-从RequestBody和获取数据", 
			notes = "复合javaBean参数校验，其中复合javaBean中含有数组类型的JavaBean的校验规范")
	@RequestMapping(value = "/test04", method = RequestMethod.POST)
	public String test04(@Valid @RequestBody CompModelTwo compModelTwo) {
		return "sucess";
	}


	@ApiOperation(value = "基本类型的参数校验--从RequestParam中获取参数值", 
			notes = "针对普通的地址栏的参数get请求，从requst中获取参数值并进行参数校验")
	@RequestMapping(value = "/test05", method = RequestMethod.GET)
	public String test05(@RequestParam long id) {
		assertGreaterThan("id", id, 1);
		return "sucess";
	}

	@ApiOperation(value = "基本类型的参数校验--从PathVariable(uri)中获取参数值", 
			notes = "针对restful风格的uri请求，从uri中获取基本变量值并进行参数校验！")
	@RequestMapping(value = "/test06/{id}", method = RequestMethod.GET)
	public String test06(@PathVariable long id) {
	    assertGreaterThan("id", id, 1);
		return "sucess";
	}
	

	@ApiOperation(value = "JSR03分组验证--添加一个学生", notes = "添加一个学生")
	@RequestMapping(value = "/stu",method = RequestMethod.POST)
	public void addStu(
			@Validated(First.class) @RequestBody Student stu) {

	}

	@ApiOperation(value = "JSR03分组验证--修改一个学生", notes = "修改一个学生")
	@RequestMapping(value = "/stu",method = RequestMethod.PUT)
	public void updateStu(
			 @Validated({Second.class}) @RequestBody Student stu) {

	}
	
	@ApiOperation(value = "JSR03分组顺序验证--添加一个账户", notes = "添加一个账户")
	@RequestMapping(value = "/account",method = RequestMethod.POST)
	public void saveAccount(
			 @Validated @RequestBody  Account account) {

	}

}
