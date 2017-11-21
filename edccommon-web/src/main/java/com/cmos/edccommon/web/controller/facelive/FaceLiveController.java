package com.cmos.edccommon.web.controller.facelive;

import com.cmos.common.bean.JsonFormatException;
import com.cmos.common.exception.GeneralException;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.common.EdcCoOutDTO;
import com.cmos.edccommon.beans.facelive.CoFaceLiveInfoDO;
import com.cmos.edccommon.beans.facelive.FaceLiveInDTO;
import com.cmos.edccommon.utils.Base64;
import com.cmos.edccommon.utils.HttpUtil;
import com.cmos.edccommon.utils.JsonUtil;
import com.cmos.edccommon.utils.StringUtil;
import com.cmos.edccommon.utils.consts.CacheConsts;
import com.cmos.edccommon.utils.consts.MqConstants;
import com.cmos.edccommon.utils.des.MsDesPlus;
import com.cmos.edccommon.utils.enums.ReturnInfoEnums;
import com.cmos.edccommon.web.cache.BasicUtil;
import com.cmos.edccommon.web.cache.CacheFatctoryUtil;
import com.cmos.edccommon.web.fileupdown.BusiFileUpDownUtil;
import com.cmos.msg.exception.MsgException;
import com.cmos.producer.client.MsgProducerClient;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 静默活体服务
 * @author xdx
 */
@RestController
@RequestMapping(value = "/co")
public class FaceLiveController {

	@Autowired
	private CacheFatctoryUtil cacheFactory;
	
	@Autowired
	private BusiFileUpDownUtil busiFileUpDownUtil;
	
	@Autowired
	private BasicUtil basicUtil;
	
	@Autowired
	private Environment env;
	
	private Logger log = LoggerFactory.getActionLog(FaceLiveController.class);

	private final static String IDNTIF_SUC = "0";// 识别结果 0为成功，1为失败；
	private final static String IDNTIF_FAILE = "1";// 识别结果 0为成功，1为失败；
	private final static String FACELIVE_DEFAULT_SCORE = "0.984"; //静默活体默认比对分值
	/**
	 * 静默活体
	 * @param inParam
	 * @return
	 */
	@RequestMapping(value = "/facelive", method = RequestMethod.POST)
	public EdcCoOutDTO getFaceLive(@RequestBody FaceLiveInDTO inParam) {
		EdcCoOutDTO outParam = new EdcCoOutDTO();
		if (inParam != null) {
			log.info("****************************" + inParam.getPicRPath());
		} else {
			outParam.setReturnCode(ReturnInfoEnums.PROCESS_INPARAM_ERROR.getCode());
			outParam.setReturnMessage(ReturnInfoEnums.PROCESS_INPARAM_ERROR.getMessage());
			return outParam;
		}

		try {
			outParam = faceLive(inParam);
		} catch (Exception e) {
			log.info("静默活体执行失败", e);
			outParam.setReturnCode(ReturnInfoEnums.PROCESS_FAILED.getCode());
			outParam.setReturnMessage(ReturnInfoEnums.PROCESS_FAILED.getMessage());
		}
		return outParam;
	}	
	/**
	 * 静默调用服务
	 * @param sourceMap
	 * @param param
	 * @return
	 * @throws GeneralException 
	 */
	@SuppressWarnings("rawtypes")
	public EdcCoOutDTO faceLive(FaceLiveInDTO inParam) throws GeneralException{
		EdcCoOutDTO out = new EdcCoOutDTO();
		out.setReturnCode(ReturnInfoEnums.PROCESS_FAILED.getCode());//默认调用不成功
		out.setReturnMessage(ReturnInfoEnums.PROCESS_FAILED.getMessage());//默认调用不成功		
		Map<String, String> returnMap = new HashMap<String, String>();
		
		String picRStr = null;
		String picRStrBase64 = null;
		String resultNum = null;
		String idntifResult;//识别结果
		String reqstSrcCode = inParam.getReqstSrcCode();
		String crkey = inParam.getCrkey();
		String picRPath = inParam.getPicRPath();
		String swftno = inParam.getSwftno();
		String bizTypeCode = inParam.getBizTypeCode();
		String faceliveScore = inParam.getFaceLiveScore();
		
		// 活体检测服务器地址
		String sendUrl = cacheFactory.getJVMString(CacheConsts.JVM.WEB_FETCH_FACE_LIVE_URL);
		if(StringUtil.isBlank(sendUrl)){
			//环境变量配置活体检测服务器地址
			sendUrl = env.getProperty("facelive.url");	
		}
		Map<String, String> paraMap = new HashMap<String, String>();

		// 1 获取人像照片
		try {
			picRStr = downloadPic(picRPath);
			if (StringUtil.isNotEmpty(picRStr)) {
				MsDesPlus Ms = new MsDesPlus(crkey);
				String picRDecStr = Ms.decrypt(picRStr);// 解密后的图片字符串
				picRStrBase64 = Base64.encode(picRDecStr.getBytes("ISO8859-1"));// 解密后的Base64字符串
			}
		} catch (Exception e) {
			picRStrBase64 = null;
			log.error("人像照片解密异常", e);
		}	
		
		Map<String, String> logMap = new HashMap<String, String>();
		if (StringUtil.isBlank(picRStrBase64)) {
			out.setReturnCode(ReturnInfoEnums.FACELIVE_PICR_DOWN_FAILED.getCode());
			out.setReturnMessage(ReturnInfoEnums.FACELIVE_PICR_DOWN_FAILED.getMessage());
			log.error("人像照片为空");
			// 获取图片为空，直接存表返回
			logMap .put("rspCode", ReturnInfoEnums.FACELIVE_PICR_DOWN_FAILED.getCode());
			logMap.put("rspInfoCntt", ReturnInfoEnums.FACELIVE_PICR_DOWN_FAILED.getMessage());
			try {
				sendMQ(reqstSrcCode, bizTypeCode, swftno, logMap);
			} catch (Exception e) {
				log.error("人像比对服务下载人像图片异常", e);
			}
			return out;
		}
		
		// 2 调用静默活体检测服务 并比对分值
		paraMap.put("image", picRStrBase64);
		paraMap.put("face_fields", ",faceliveness");
		String jsonString = JsonUtil.convertObject2Json(paraMap);
		log.info("    ##########  静默服务调用  URL：" + sendUrl);
		log.info("    ##########  静默服务调用  reqjson大小：" + jsonString.length());
		String rtnJson = HttpUtil.sendHttpPostEntity(sendUrl, jsonString);
		
		//调用成功，将调用响应报文存表
		logMap.put("backtoMsgCntt", rtnJson);
		
		log.info("    ##########  静默服务返回  rtnjson：" + rtnJson);
		if (rtnJson != null) {
			Map rtnMap = (Map) JsonUtil.convertJson2Object(rtnJson, Map.class);
			if (rtnMap != null && rtnMap.containsKey("result_num")) {
				resultNum = String.valueOf(rtnMap.get("result_num"));
			}
			returnMap.put("faceQty", resultNum);// 识别出的人脸数

			if (rtnMap != null && StringUtil.isNotBlank(resultNum) && Integer.parseInt(resultNum) > 0) {
				List resultList = (List) rtnMap.get("result");
				if (resultList != null && !resultList.isEmpty()) {
					Map resultMap = (Map) resultList.get(0);
					String faceliveness = String.valueOf(resultMap.get("faceliveness"));
					returnMap.put("faceScore", faceliveness);// 识别出的人脸分值

					float faceScore = Float.parseFloat(faceliveness);
					// 获取默认静默活体检测分值
					if (StringUtil.isEmpty(faceliveScore)) {
						faceliveScore = cacheFactory.getJVMString(CacheConsts.JVM.FACE_LIVE_DEFAULT_SCORE);

						if (StringUtil.isEmpty(faceliveScore)) {
							faceliveScore = FACELIVE_DEFAULT_SCORE;
						}
					}
					if (faceScore < Float.parseFloat(faceliveScore)) {
						log.info("真人检测不通过：阈值="+faceliveScore+"，实际比分="+faceScore);
						idntifResult = IDNTIF_FAILE;// 识别结果 0为成功，1为失败；
					} else {
						idntifResult = IDNTIF_SUC;// 识别结果 0为成功，1为失败；
					}
				} else {
					idntifResult = IDNTIF_FAILE;// 识别结果 0为成功，1为失败；
					returnMap.put("faceScore", "不存在");// 识别出的人脸分值
				}
			} else {
				idntifResult = IDNTIF_FAILE;// 识别结果 0为成功，1为失败；
				log.info("活体检测调用服务超时,流水号为：" + swftno);
			}
			returnMap.put("idntifResult", idntifResult);// 识别出的结果
			out.setReturnCode(ReturnInfoEnums.PROCESS_SUCCESS.getCode());//默认调用成功
			out.setReturnMessage(ReturnInfoEnums.PROCESS_SUCCESS.getMessage());//默认调用成功	
			out.setBean(returnMap);
			// 3 保存调用记录
			try {
				logMap.put("rspCode", ReturnInfoEnums.PROCESS_SUCCESS.getCode());
				logMap.put("rspInfoCntt", ReturnInfoEnums.PROCESS_SUCCESS.getMessage());
				logMap.put("faceliveScore", faceliveScore);
				logMap.putAll(returnMap);
				sendMQ(reqstSrcCode, bizTypeCode, swftno, logMap);
			} catch (Exception e) {
				log.error("静默活体发送消息队列异常", e);
			}
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
		MsgProducerClient.getRocketMQProducer().send(MqConstants.MQ_TOPIC.FACE_LIVE, Msg);
	}
	
	/**
	 * 将保存信息转化为消息
	 */
	private String saveFaceLiveInfo(String requestSource, String busiType, String transactionId, Map<String,String> logMap) throws JsonFormatException {

		CoFaceLiveInfoDO infoBean = new CoFaceLiveInfoDO();
		String uniqueSequence = null;
		try {
			uniqueSequence = basicUtil.getSequence(MqConstants.MQ_TOPIC.FACE_LIVE);
		} catch (Exception e) {
			log.error("静默活体生成主键异常", e);
		}
		
		if (uniqueSequence != null) {
			if (uniqueSequence.length() > 19) {
				uniqueSequence = uniqueSequence.substring(uniqueSequence.length() - 19);
			}
			Long detctnId = Long.parseLong(uniqueSequence);
			infoBean.setDetctnId(detctnId);
		}
		
		infoBean.setCrtTime(new Timestamp(new Date().getTime()));
		infoBean.setReqstSrcCode(requestSource);
		infoBean.setBizTypeCode(busiType);
		infoBean.setSwftno(transactionId);
		String rspCode = logMap.get("rspCode");
		String rspInfoCntt = logMap.get("rspInfoCntt");
		String idntifFaceCnt = logMap.get("faceQty");
		String backtoMsgCntt = logMap.get("backtoMsgCntt");
		String idntifScore = logMap.get("faceScore");
		
		
		infoBean.setBacktoMsgCntt(backtoMsgCntt);//返回报文内容
		infoBean.setRspCode(rspCode);//返回码
		infoBean.setRspInfoCntt(rspInfoCntt);//返回信息
		infoBean.setIdntifFaceCnt(idntifFaceCnt);//识别人脸数
		infoBean.setIdntifScore(idntifScore);//分数

		String resultStr = infoBean.toJSON().toString();
		return resultStr;
	}
	
	/**
	 * 下载业务图片
	 * @param picPath
	 * @return
	 */
	private String downloadPic(String picPath) {
		String picStr = null;
		try {
			picStr = busiFileUpDownUtil.downloadBusiFileStr(picPath);
		} catch (Exception e) {
			picStr = null;
			log.error("静默活体服务下载图片异常", e);
		} 
		return picStr;
	}
}
