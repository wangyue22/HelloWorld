package com.cmos.edccommon.web.controller.facelive;

import com.cmos.common.bean.JsonFormatException;
import com.cmos.common.exception.GeneralException;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.common.OutputObject;
import com.cmos.edccommon.beans.facelive.CoFaceLiveInfoDO;
import com.cmos.edccommon.beans.facelive.FaceLiveInDTO;
import com.cmos.edccommon.utils.Base64;
import com.cmos.edccommon.utils.BsStaticDataUtil;
import com.cmos.edccommon.utils.FileUtil;
import com.cmos.edccommon.utils.HttpUtil;
import com.cmos.edccommon.utils.IOUtils;
import com.cmos.edccommon.utils.JsonUtil;
import com.cmos.edccommon.utils.StringUtil;
import com.cmos.msg.exception.MsgException;
import com.cmos.producer.client.MsgProducerClient;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value = "/co")
public class FaceLiveController {
	private Logger log=LoggerFactory.getActionLog(FaceLiveController.class);
	
	@RequestMapping(value = "/facelive", method = RequestMethod.POST)
	public OutputObject getFaceLive(@RequestBody FaceLiveInDTO inParam ) throws GeneralException {
		log.info("****************************"+inParam.toString());
	
		return faceLive(inParam);
	}	
	/**
	 * 静默调用服务
	 * @param sourceMap
	 * @param param
	 * @return
	 * @throws GeneralException 
	 */
	@SuppressWarnings("rawtypes")
	public OutputObject faceLive(FaceLiveInDTO inParam) throws GeneralException {
		OutputObject out = new OutputObject();
		out.setReturnCode("0000");//默认调用成功
		out.setReturnCode("success");//默认调用成功
		
		Map<String, String> returnMap = new HashMap<String, String>();
		Map<String, String> logMap =  new HashMap<String, String>();
		logMap.put("rspCode", "0000");//默认调用成功
		logMap.put("rspInfoCntt", "success");//默认调用成功
		InputStream picR = null;
		String picRStr = null;
		String resultNum = null;
		String idntifResult;//识别结果
		String reqstSrcCode = inParam.getReqstSrcCode();
		String picRPath = inParam.getPicRPath();
		String swftno = inParam.getSwftno();
		String bizTypeCode = inParam.getBizTypeCode();
		String faceliveScore = inParam.getFaceLiveScore();
		
		// 活体检测分省控制开关
		String provCode = BsStaticDataUtil.getCodeValue("OL_WEB_FETCH", "FACE_LIVE_BY_PROVCODE_WORKORDER_NFCNEW", "JVM");
		if (!provCode.contains("," + reqstSrcCode + ",")) {
			log.info("    ##########  请求源：" + reqstSrcCode + " 暂未开启活体检测开关，不进行活体检测 #########");
			out.setReturnCode("2999");
			out.setReturnCode("暂未开启活体检测服务 ");
			return out;
		}
		// 活体检测服务器地址
		String sendUrl = BsStaticDataUtil.getCodeValue("OL_WEB_FETCH", "FACE_LIVE_URL", "JVM");
		Map<String, String> paraMap = new HashMap<String, String>();

		// 1 获取人像照片
		try {
			picR = FileUtil.download(picRPath);
			picRStr = Base64.encode(IOUtils.toByteArray(picR));
		} catch (Exception e) {
			picRStr = null;
			log.error("人像比对服务下载人像图片异常", e);
			if (null != picR) {
				try {
					picR.close();
				} catch (Exception e1) {
				}
			}
		}
		//获取图片为空，直接存表返回
		if (StringUtil.isEmpty(picRStr)) {
			log.error("人像照片下载异常");
			out.setReturnCode("9999");
			out.setReturnCode("人像照片下载异常");
			logMap.put("rspCode", "9999");
			logMap.put("rspInfoCntt", "人像照片下载异常");
			try{
				sendMQ(reqstSrcCode, bizTypeCode, swftno, logMap);
			}catch(Exception e) {
				picRStr = null;
				log.error("人像比对服务下载人像图片异常", e);
			}
			return out;
		}
		// 2 调用静默活体检测服务 并比对分值
		paraMap.put("image", picRStr);
		paraMap.put("face_fields", ",faceliveness");
		String jsonString = JsonUtil.convertObject2Json(paraMap);
		log.info("    ##########  静默服务调用  URL：" + sendUrl);
		log.info("    ##########  静默服务调用  reqjson：" + jsonString);
		String rtnJson = HttpUtil.sendHttpPostEntity(sendUrl, jsonString);
		
		//调用成功，将调用响应报文存表
		logMap.put("backtoMsgCntt", jsonString);
		
		log.info("    ##########  静默服务返回  rtnjson：" + rtnJson);
		Map rtnMap = (Map) JsonUtil.convertJson2Object(rtnJson, Map.class);
		if (rtnMap != null && rtnMap.containsKey("result_num")) {
			resultNum = String.valueOf(rtnMap.get("result_num"));
		}
		returnMap.put("faceQty", resultNum);//识别出的人脸数
		
		if (StringUtil.isNotEmpty(resultNum) && Integer.parseInt(resultNum) > 0){
			List resultList = (List) rtnMap.get("result");
			if (resultList != null && resultList.size() > 0) {
				Map resultMap = (Map) resultList.get(0);
				String faceliveness = String.valueOf(resultMap.get("faceliveness"));
				returnMap.put("faceScore", faceliveness);// 识别出的人脸分值

				float faceScore = Float.parseFloat(faceliveness);
				//获取默认静默活体检测分值
				if (StringUtil.isEmpty(faceliveScore)) {
					faceliveScore = BsStaticDataUtil.getCodeValue("FACE_LIVE", "DEFAULT_SCORE", "JVM");
				}
				if (faceScore < Float.parseFloat(faceliveScore)) {
					log.info("#0000:真人检测失败：未检测到活体");
					idntifResult = "1";// 识别结果 0为成功，1为失败；
				}else{
					idntifResult = "0";// 识别结果 0为成功，1为失败；
				}
			}else{
				idntifResult = "1";// 识别结果 0为成功，1为失败；
				returnMap.put("faceScore", "不存在");// 识别出的人脸分值
			}
		} else {
			idntifResult = "1";// 识别结果 0为成功，1为失败；
			log.info("2999:活体检测调用服务超时");
		}
		returnMap.put("idntifResult", idntifResult);// 识别出的结果
		out.setBean(returnMap);
		// 3 保存调用记录
		try{
			logMap.put("faceliveScore", faceliveScore);
			logMap.putAll(returnMap);
			sendMQ(reqstSrcCode, bizTypeCode, swftno, logMap);
		}catch(Exception e) {
			picRStr = null;
			log.error("静默活体发送消息队列异常", e);
		}
		return out; 
	}
	
	/**
	 * 发送消息队列
	 * @throws MsgException
	 * @throws JsonFormatException 
	 */
	private void sendMQ(String requestSource, String busiType, String transactionId, Map<String,String> logMap) throws MsgException, JsonFormatException{
		String Msg = saveFaceLiveInfo(requestSource, busiType, transactionId, logMap);
		MsgProducerClient.getRocketMQProducer().send("EDCCO_FACELIVE", Msg);
	}
	
	/**
	 * 将保存信息转化为消息
	 */
	private String saveFaceLiveInfo(String requestSource, String busiType, String transactionId, Map<String,String> logMap) throws JsonFormatException {

		CoFaceLiveInfoDO infoBean = new CoFaceLiveInfoDO();
		infoBean.setCrtTime(new Timestamp(new Date().getTime()));
		infoBean.setRspCode(requestSource);
		infoBean.setBizTypeCode(busiType);
		infoBean.setSwftno(transactionId);
		String rspCode = logMap.get("rspCode");
//		String idntifResult = logMap.get("idntifResult");
		String rspInfoCntt = logMap.get("rspInfoCntt");
		String idntifFaceCnt = logMap.get("rspCode");
		String idntifScore = logMap.get("faceScore");
		infoBean.setRspCode(rspCode);
		infoBean.setRspInfoCntt(rspInfoCntt);
		infoBean.setIdntifFaceCnt(idntifFaceCnt);
		infoBean.setIdntifScore(idntifScore);
		
//		infoBean.setIdntifResult(idntifResult);
//		infoBean.setFaceliveScore(faceliveScore);
		
		String resultStr = infoBean.toJSON().toString();
		return resultStr;
	}
}
