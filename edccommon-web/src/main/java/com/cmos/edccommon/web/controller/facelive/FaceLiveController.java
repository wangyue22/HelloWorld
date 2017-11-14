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
import com.cmos.edccommon.web.cache.BasicUtil;
import com.cmos.edccommon.web.cache.CacheFatctoryUtil;
import com.cmos.edccommon.web.fileupdown.FileUpDownUtil;
import com.cmos.msg.exception.MsgException;
import com.cmos.producer.client.MsgProducerClient;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
	private FileUpDownUtil fileUpDownUtil;
	
	@Autowired
	private BasicUtil basicUtil;
	
	private Logger log = LoggerFactory.getActionLog(FaceLiveController.class);
	
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
			outParam.setReturnCode("2999");
			outParam.setReturnMessage("参数异常");
			return outParam;
		}

		try {
			outParam = faceLive(inParam);
		} catch (Exception e) {
			log.info("静默活体执行失败", e);
			outParam.setReturnCode("2999");
			outParam.setReturnMessage(e.getMessage());
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
		out.setReturnCode("2999");//默认调用不成功
		out.setReturnMessage("调用静默活体失败");//默认调用不成功		
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
		
//		// 活体检测分省控制开关
//		String provCode = cacheFactory.getJVMString(CacheConsts.JVM.FACE_LIVE_BY_PROVCODE);
////		String provCode = BsStaticDataUtil.getCodeValue("OL_WEB_FETCH", "FACE_LIVE_BY_PROVCODE_WORKORDER_NFCNEW", "JVM");
//		if (StringUtil.isBlank(provCode) || !provCode.contains("," + reqstSrcCode + ",")) {
//			log.info("    ##########  请求源：" + reqstSrcCode + " 暂未开启活体检测开关，不进行活体检测 #########");
//			out.setReturnCode("2999");
//			out.setReturnMessage("暂未开启活体检测服务 ");
//			return out;
//		}
		// 活体检测服务器地址
		String sendUrl = cacheFactory.getJVMString(CacheConsts.JVM.WEB_FETCH_FACE_LIVE_URL);
//		String sendUrl = "http://192.168.100.139:8885/api/v2/faceverify/detect";
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
			log.error("人像照片解密异常",e);
		}	
		
		Map<String, String> logMap = new HashMap<String, String>();
		if (StringUtil.isBlank(picRStrBase64)) {
			out.setReturnCode("2999");
			out.setReturnMessage("人像照片获取异常");
			log.error("人像照片为空");
			// 获取图片为空，直接存表返回
			logMap .put("rspCode", "2999");
			logMap.put("rspInfoCntt", "人像照片获取异常");
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

			if (StringUtil.isNotBlank(resultNum) && Integer.parseInt(resultNum) > 0) {
				List resultList = (List) rtnMap.get("result");
				if (resultList != null && resultList.size() > 0) {
					Map resultMap = (Map) resultList.get(0);
					String faceliveness = String.valueOf(resultMap.get("faceliveness"));
					returnMap.put("faceScore", faceliveness);// 识别出的人脸分值

					float faceScore = Float.parseFloat(faceliveness);
					// 获取默认静默活体检测分值
					if (StringUtil.isEmpty(faceliveScore)) {
						faceliveScore = cacheFactory.getJVMString(CacheConsts.JVM.FACE_LIVE_DEFAULT_SCORE);

						if (StringUtil.isEmpty(faceliveScore)) {
							faceliveScore = "0.984";
						}
					}
					if (faceScore < Float.parseFloat(faceliveScore)) {
						log.info("真人检测不通过：阈值="+faceliveScore+"，实际比分="+faceScore);
						idntifResult = "1";// 识别结果 0为成功，1为失败；
					} else {
						idntifResult = "0";// 识别结果 0为成功，1为失败；
					}
				} else {
					idntifResult = "1";// 识别结果 0为成功，1为失败；
					returnMap.put("faceScore", "不存在");// 识别出的人脸分值
				}
			} else {
				idntifResult = "1";// 识别结果 0为成功，1为失败；
				log.info("2999:活体检测调用服务超时");
			}
			returnMap.put("idntifResult", idntifResult);// 识别出的结果
			out.setReturnCode("0000");//默认调用成功
			out.setReturnMessage("success");//默认调用成功	
			out.setBean(returnMap);
			// 3 保存调用记录
			try {
				logMap.put("rspCode", "0000");
				logMap.put("rspInfoCntt", "success");
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
		InputStream picInputStream = null;
		String picStr = null;
		try {
			picStr = fileUpDownUtil.downloadBusiFileStr(picPath);
		} catch (Exception e) {
			picStr = null;
			log.error("静默活体服务下载图片异常", e);
		} finally {
			if (null != picInputStream) {
				try {
					picInputStream.close();
				} catch (IOException e1) {
					log.error("静默活体服务关闭图片流异常", e1);
				}
			}
		}
		return picStr;
	}
}
