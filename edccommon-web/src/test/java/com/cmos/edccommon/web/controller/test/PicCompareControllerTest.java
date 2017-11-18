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
     * http://localhost:18080/edccommon/co/picdoublecompare
     * @throws Exception
     */
    public void picdoublecompareTest() throws Exception{
    	PicDoubleCompareInDTO picDoubleCompareInfo = new PicDoubleCompareInDTO();
    	picDoubleCompareInfo.setReqstSrcCode("371");
    	picDoubleCompareInfo.setSwftno("1234567890");
    	picDoubleCompareInfo.setBizTypeCode("DEFAULT");
    	picDoubleCompareInfo.setBothCompFlag("true");
    	picDoubleCompareInfo.setGztAvtrPath("certfile/41/10/81/411081199009115952.jpg");
    	picDoubleCompareInfo.setPhotoPath("bFTP/10085custPicCompare/371/20171115/10852017111503710201_R.jpg");
    	picDoubleCompareInfo.setPicStoinPath("bFTP/10085custPicCompare/371/20171115/10852017111503710201_R.jpg");
    	picDoubleCompareInfo.setGztAvtrScore("0-50|80-100");
    	picDoubleCompareInfo.setPicStoinScore("0-50|80-100");
    	picDoubleCompareInfo.setPhotoType("r");
    	picDoubleCompareInfo.setCrkey("QXNLD");
        logger.info("入参："+JsonUtil.convertObject2Json(picDoubleCompareInfo));
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/co/picdoublecompare").accept("application/json")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(JsonUtil.convertObject2Json(picDoubleCompareInfo)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		logger.info("" + result.getResponse().getContentAsString());
    }
    
    @Test
    /**
     * http://localhost:18080/edccommon/co/picCompare
     * @throws Exception
     */
    public void picCompareTest() throws Exception{
    	PicCompareInDTO picCompareInfo = new PicCompareInDTO();
    	picCompareInfo.setReqstSrcCode("371");
    	picCompareInfo.setSwftno("1234567890");
    	picCompareInfo.setBizTypeCode("DEFAULT");

    	picCompareInfo.setPhotoType("r");
    	picCompareInfo.setPhotoPath("bFTP/10085custPicCompare/371/20171115/10852017111503710201_R.jpg");
    	picCompareInfo.setPicTPath("bFTP/10085custPicCompare/371/20171115/10852017111503710201_R.jpg");
    	picCompareInfo.setPicTType("x");
    	picCompareInfo.setCrkey("QXNLD");
    	picCompareInfo.setConfidenceScore("0-50|80-100");

        logger.info("入参："+JsonUtil.convertObject2Json(picCompareInfo));
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/co/picCompare").accept("application/json")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(JsonUtil.convertObject2Json(picCompareInfo)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		logger.info("" + result.getResponse().getContentAsString());
    }
    
    @Test
    /**
     * http://localhost:18080/edccommon/co/picCheck
     * @throws Exception
     */
    public void picCheckTest() throws Exception{
    	PicCompareInDTO picCompareInfo = new PicCompareInDTO();
    	picCompareInfo.setReqstSrcCode("371");
    	picCompareInfo.setSwftno("1234567890");
    	picCompareInfo.setBizTypeCode("DEFAULT");

    	picCompareInfo.setPhotoType("r");
    	picCompareInfo.setPhotoPath("bFTP/10085custPicCompare/371/20171115/10852017111503710201_R.jpg");
    	picCompareInfo.setPicTPath("bFTP/10085custPicCompare/371/20171115/10852017111503710201_R.jpg");
    	picCompareInfo.setPicTType("x");
    	picCompareInfo.setCrkey("QXNLD");

        logger.info("入参："+JsonUtil.convertObject2Json(picCompareInfo));
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/co/picCheck").accept("application/json")
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(JsonUtil.convertObject2Json(picCompareInfo)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		logger.info("" + result.getResponse().getContentAsString());
    }
}
