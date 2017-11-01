package com.cmos.edccommon.web.controller.test;

import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.piccompare.PicCompareInDTO;
import com.cmos.edccommon.beans.piccompare.PicDoubleCompareInDTO;
import com.cmos.edccommon.utils.JsonUtil;
import com.cmos.edccommon.web.BaseUnitTest;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * 人像比对单元测试
 * @author xdx
 *
 */
public class PicCompareControllerTest extends BaseUnitTest {

    Logger logger = LoggerFactory.getActionLog(PicCompareControllerTest.class);

    @Test
    /**
     * http://localhost:18080/co/picdoublecompare
     * @throws Exception
     */
    public void picdoublecompareTest() throws Exception{
    	PicDoubleCompareInDTO picDoubleCompareInfo = new PicDoubleCompareInDTO();
    	picDoubleCompareInfo.setReqstSrcCode("371");
    	picDoubleCompareInfo.setSwftno("1234567890");
    	picDoubleCompareInfo.setBizTypeCode("DEFAULT");
    	picDoubleCompareInfo.setBothCompFlag("true");
    	picDoubleCompareInfo.setGztAvtrPath("aFTP://123.jpg");
    	picDoubleCompareInfo.setPhotoPath("aFTP://123.jpg");
    	picDoubleCompareInfo.setPicStoinPath("aFTP://123.jpg");
    	picDoubleCompareInfo.setGztAvtrScore("0-50|80-100");
    	picDoubleCompareInfo.setPicStoinScore("0-50|80-100");
    	picDoubleCompareInfo.setPhotoType("r");
        logger.info("入参："+JsonUtil.convertObject2Json(picDoubleCompareInfo));
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/co/picdoublecompare").accept("application/json")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(JsonUtil.convertObject2Json(picDoubleCompareInfo)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        logger.info(""+result);
    }
    
    @Test
    /**
     * http://localhost:18080/co/piccompare
     * @throws Exception
     */
    public void picCompareTest() throws Exception{
    	PicCompareInDTO picCompareInfo = new PicCompareInDTO();
    	picCompareInfo.setReqstSrcCode("371");
    	picCompareInfo.setSwftno("1234567890");
    	picCompareInfo.setBizTypeCode("DEFAULT");

    	picCompareInfo.setPhotoType("r");
    	picCompareInfo.setPhotoPath("aFTP://123.jpg");
    	picCompareInfo.setPicTPath("aFTP://123.jpg");
    	picCompareInfo.setPicTType("g");
    	picCompareInfo.setCrkey("1234");
    	picCompareInfo.setConfidenceScore("0-50|80-100");

        logger.info("入参："+JsonUtil.convertObject2Json(picCompareInfo));
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/co/picdoublecompare").accept("application/json")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(JsonUtil.convertObject2Json(picCompareInfo)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        logger.info(""+result);
    }
    
    @Test
    /**
     * http://localhost:18080/co/piccheck
     * @throws Exception
     */
    public void picCheckTest() throws Exception{
    	PicCompareInDTO picCompareInfo = new PicCompareInDTO();
    	picCompareInfo.setReqstSrcCode("371");
    	picCompareInfo.setSwftno("1234567890");
    	picCompareInfo.setBizTypeCode("DEFAULT");

    	picCompareInfo.setPhotoType("r");
    	picCompareInfo.setPhotoPath("aFTP://123.jpg");
    	picCompareInfo.setPicTPath("aFTP://123.jpg");
    	picCompareInfo.setPicTType("g");
    	picCompareInfo.setCrkey("1234");
    	picCompareInfo.setConfidenceScore("0-50|80-100");

        logger.info("入参："+JsonUtil.convertObject2Json(picCompareInfo));
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/co/piccheck").accept("application/json")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(JsonUtil.convertObject2Json(picCompareInfo)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        logger.info(""+result);
    }

    @Test
    /**
     * http://localhost:18080/co/mqTest
     * @throws Exception
     */
    public void MqTest() throws Exception{
    	PicDoubleCompareInDTO picDoubleCompareInfo = new PicDoubleCompareInDTO();
    	picDoubleCompareInfo.setReqstSrcCode("371");
    	picDoubleCompareInfo.setSwftno("1234567890");
    	picDoubleCompareInfo.setBizTypeCode("DEFAULT");
    	picDoubleCompareInfo.setBothCompFlag("true");
    	picDoubleCompareInfo.setGztAvtrPath("aFTP://123.jpg");
    	picDoubleCompareInfo.setPhotoPath("aFTP://123.jpg");
    	picDoubleCompareInfo.setPicStoinPath("aFTP://123.jpg");
    	picDoubleCompareInfo.setGztAvtrScore("0-50|80-100");
    	picDoubleCompareInfo.setPicStoinScore("0-50|80-100");
    	picDoubleCompareInfo.setPhotoType("r");
        logger.info("入参："+JsonUtil.convertObject2Json(picDoubleCompareInfo));
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/co/picdoublecompare").accept("application/json")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(JsonUtil.convertObject2Json(picDoubleCompareInfo)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        logger.info(""+result);
    }
}
