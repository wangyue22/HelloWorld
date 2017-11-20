package com.cmos.edccommon.web.controller.test;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.crkey.KeyInfoDTO;
import com.cmos.edccommon.beans.crkey.RsaKeyDO;
import com.cmos.edccommon.utils.JsonUtil;
import com.cmos.edccommon.web.BaseUnitTest;

/**
 * 秘钥管理单元测试
 * @author xdx
 *
 */
public class KeyInfoControllerTest extends BaseUnitTest {

    Logger logger = LoggerFactory.getActionLog(KeyInfoControllerTest.class);

    @Test
    /**
     * http://localhost:18080/edccommon/co/getDesKey
     * @throws Exception
     */
    public void getDesKeyTest() throws Exception{
    	KeyInfoDTO keyInfoDTO = new KeyInfoDTO();
  
        keyInfoDTO.setReqstSrcCode("371");
        logger.info("入参："+JsonUtil.convertObject2Json(keyInfoDTO));
        MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.post("/getDesKey").accept("application/json")
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(JsonUtil.convertObject2Json(keyInfoDTO))
                /* content("371")*/)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		logger.info("" + result.getResponse().getContentAsString());
    }


    @Test
    /**
     * http://localhost:18080/edccommon/co/getRsaKey
     * @throws Exception
     */
    public void getRsaKeyTest() throws Exception{
        KeyInfoDTO keyInfoDTO = new KeyInfoDTO();
        keyInfoDTO.setReqstSrcCode("371");
        logger.info("入参："+JsonUtil.convertObject2Json(keyInfoDTO));
        MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.post("/getRsaKey").accept("application/json")
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(JsonUtil.convertObject2Json(keyInfoDTO)))
            .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		logger.info("" + result.getResponse().getContentAsString());
    }

    @Test
    /**
     * http://localhost:18080/edccommon/realityAccount/getRealityAccount
     * @throws Exception
     */
    public void getRealityAccountTest() throws Exception{
        MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.post("/realityAccount/getRealityAccount").accept("application/json")
            .contentType(MediaType.APPLICATION_JSON_VALUE).param("reqstSrcCode", "371")
                /* content("371")*/)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		logger.info("" + result.getResponse().getContentAsString());
    }

}
