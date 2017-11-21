package com.cmos.edccommon.web.controller.test;

import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.facelive.FaceLiveInDTO;
import com.cmos.edccommon.utils.JsonUtil;
import com.cmos.edccommon.web.BaseUnitTest;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * 静默活体检测单元测试
 * @author xdx
 *
 */
public class FaceLiveControllerTest extends BaseUnitTest {

    Logger logger = LoggerFactory.getActionLog(FaceLiveControllerTest.class);
 
    
    /**
     * http://localhost:18080/edccommon/co/facelive
     * @throws Exception
     */
    @Test
    public void getFaceLiveTest() throws Exception{
    	FaceLiveInDTO faceLiveInfo = new FaceLiveInDTO();
    	faceLiveInfo.setReqstSrcCode("371");
    	faceLiveInfo.setBizTypeCode("DEFAULT");
    	faceLiveInfo.setFaceLiveScore("98.075");
    	faceLiveInfo.setPicRPath("bFTP/10085custPicCompare/371/20171115/10852017111503710201_R.jpg");
    	faceLiveInfo.setCrkey("QXNLD");
    	faceLiveInfo.setSwftno("1234567890");
        logger.info("入参："+JsonUtil.convertObject2Json(faceLiveInfo));
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/co/facelive").accept("application/json")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(JsonUtil.convertObject2Json(faceLiveInfo)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        logger.info("" + result.getResponse().getContentAsString());
    }


}
