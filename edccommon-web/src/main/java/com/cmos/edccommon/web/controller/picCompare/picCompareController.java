/** 
 * Project Name:itframe-web 
 * File Name:picCompareController.java 
 * Package Name:com.cmos.edccommon.web.controller
 * Date:2016年11月16日上午9:03:20 
 * Copyright (c) 2016, likerui All Rights Reserved. 
 * 
 */
package com.cmos.edccommon.web.controller.picCompare;



import com.alibaba.dubbo.config.annotation.Reference;
import com.cmos.edccommon.beans.common.InputObject;
import com.cmos.edccommon.iservice.piccompare.IPicCompareSV;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value = "/co",  method = RequestMethod.GET)
public class picCompareController {

	@Reference(group = "edcco")
	private IPicCompareSV picCompareSV;

/**
 * http://localhost:18080/co/piccompare?inParam=1234 
 * 
 * @param inParam
 * @return
 */
	@RequestMapping(value = "/piccompare", method = RequestMethod.GET)
//	public String getPicCompare(@RequestBody InputObject inParam ) {
	public String getPicCompare(@RequestParam String inParam ) {
		System.out.println("****************************"+inParam);
		InputObject inObject=new InputObject();
		return picCompareSV.picCompare(inObject);
	}

}
