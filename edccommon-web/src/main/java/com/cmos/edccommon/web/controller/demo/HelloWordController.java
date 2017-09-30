/** 
 * Project Name:itframe-web 
 * File Name:HelloWordController.java 
 * Package Name:com.cmos.edccommon.web.controller
 * Date:2016年11月16日上午9:03:20 
 * Copyright (c) 2016, likerui All Rights Reserved. 
 * 
 */
package com.cmos.edccommon.web.controller.demo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cmos.edccommon.beans.emp.Emp;
import com.cmos.edccommon.iservice.emp.IEmpSV;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
public class HelloWordController {

	@Reference(group = "edccommon")
	private IEmpSV empSV = null;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hellWord() {
		List<Emp> emps = empSV.getByBasicInfo("JONES", "经理", 7839L);
		StringBuilder buf = new StringBuilder("\r\n");
		for(Emp emp : emps) {
			buf.append("empno=" + emp.getEmpno() + ", ename=" + emp.getEname() + "\r\n");
		}
		return "hello :" + buf.toString();
	}

}
