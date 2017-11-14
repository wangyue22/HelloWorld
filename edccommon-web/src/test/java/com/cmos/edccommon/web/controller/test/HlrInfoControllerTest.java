package com.cmos.edccommon.web.controller.test;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.crkey.KeyInfoDTO;
import com.cmos.edccommon.utils.JsonUtil;
import com.cmos.edccommon.web.BaseUnitTest;

/**
 * 秘钥管理单元测试
 * 
 * @author xdx
 *
 */
public class HlrInfoControllerTest extends BaseUnitTest {

	Logger logger = LoggerFactory.getActionLog(HlrInfoControllerTest.class);

	@Test
	/**
	 * http://localhost:18080/edccommon/getProvCodeByPhoneNum
	 * 
	 * @throws Exception
	 */
	public void getProvCodeByPhoneNum() throws Exception {
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/getProvCodeByPhoneNum").accept("application/json")
						.contentType(MediaType.APPLICATION_JSON_VALUE).param("phoneNum", "15293051111")
				/* content("371") */).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		logger.info("" + result.getResponse().getContentAsString());
	}

	@Test
	/**
	 * http://localhost:18080/edccommon/getHlrTypeByPhoneNum
	 * 
	 * @throws Exception
	 */
	public void getHlrTypeByPhoneNum() throws Exception {
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/getHlrTypeByPhoneNum").accept("application/json")
						.contentType(MediaType.APPLICATION_JSON_VALUE).param("phoneNum", "15293051111")
				/* content("371") */).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		logger.info("" + result.getResponse().getContentAsString());

	}

}
