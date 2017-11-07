package com.cmos.edccommon.web.controller.test;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
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
     * http://localhost:18080/co/getDesKey
     * @throws Exception
     */
    public void getDesKeyTest() throws Exception{
        RsaKeyDO keyInfoDTO = new RsaKeyDO();

        logger.info("入参："+JsonUtil.convertObject2Json(keyInfoDTO));
        MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.post("/co/getDesKey").accept("application/json")
            .contentType(MediaType.APPLICATION_JSON_VALUE).param("reqstSrcCode", "371")
                /* content("371")*/)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        logger.info(""+result);
    }


    @Test
    /**
     * http://localhost:18080/co/getRsaKey
     * @throws Exception
     */
    public void getRsaKeyTest() throws Exception{
        RsaKeyDO keyInfoDTO = new RsaKeyDO();
        keyInfoDTO.setReqstSrcCode("371");
        keyInfoDTO.setBizTypeCd("DEFAULT");
        logger.info("入参："+JsonUtil.convertObject2Json(keyInfoDTO));
        MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.post("/co/getRsaKey").accept("application/json")
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(JsonUtil.convertObject2Json(keyInfoDTO)))
            .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        logger.info(""+result);
    }


}
