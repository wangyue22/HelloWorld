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
import com.cmos.edccommon.utils.KafkaUtil;
import com.cmos.edccommon.utils.StringUtil;
import com.cmos.edccommon.utils.consts.AppCodeConsts;
import com.cmos.edccommon.utils.consts.CacheConsts;
import com.cmos.edccommon.utils.consts.CoConstants;
import com.cmos.edccommon.utils.consts.KafkaConsts;
import com.cmos.edccommon.utils.des.MsDesPlus;
import com.cmos.edccommon.utils.enums.ReturnInfoEnums;
import com.cmos.edccommon.web.aop.AopChecker;
import com.cmos.edccommon.web.cache.BasicUtil;
import com.cmos.edccommon.web.cache.CacheFatctoryUtil;
import com.cmos.edccommon.web.fileupdown.BusiFileUpDownUtil;

import io.swagger.annotations.Api;

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
@Api(description = "通用能力静默活体")
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
	private final static String FACELIVE_DEFAULT_SCORE = "0.9848"; //静默活体默认比对分值
	private final static int FACELIVE_DEFAULT_TIME_OUT = 20; //静默活体默认超时时间
	
	/**
	 * 静默活体
	 * @param inParam
	 * @return
	 */
	@AopChecker
	@RequestMapping(value = "/facelive", method = RequestMethod.POST)
	public EdcCoOutDTO faceLive(@RequestBody FaceLiveInDTO inParam){
		long startTime = System.currentTimeMillis();
		EdcCoOutDTO out= new EdcCoOutDTO();
		if (inParam != null&&StringUtil.isNotBlank(inParam.getAppSysID())) {
			log.info("****************************" + inParam.getPicRPath());
		} else {
			out.setReturnCode(ReturnInfoEnums.PROCESS_INPARAM_ERROR.getCode());
			out.setReturnMessage(ReturnInfoEnums.PROCESS_INPARAM_ERROR.getMessage());
			return out;
		}
		
		out.setReturnCode(ReturnInfoEnums.PROCESS_FAILED.getCode());//默认调用不成功
		out.setReturnMessage(ReturnInfoEnums.PROCESS_FAILED.getMessage());//默认调用不成功		
		String picRStr;
		String picRStrBase64 = null;
		String reqstSrcCode = inParam.getReqstSrcCode();
		String crkey = inParam.getCrkey();
		String picRPath = inParam.getPicRPath();
		String swftno = inParam.getSwftno();
		String bizTypeCode = inParam.getBizTypeCode();
		String faceliveScore = inParam.getFaceLiveScore();
		String appSysID = inParam.getAppSysID();// 来源系统
		String appUserID = inParam.getAppUserID();//来源用户
		
		Map<String, String> logMap = new HashMap<String, String>();
		logMap.put("picRPath", picRPath);
		logMap.put("crkey", crkey);
		logMap.put("confScore", faceliveScore);
		logMap.put("idntifResult", CoConstants.RESULT_TYPE.INIT_CODE);
		try {
			// 活体检测服务器地址
			String sendUrl = cacheFactory.getJVMString(CacheConsts.JVM.WEB_FETCH_FACE_LIVE_URL);
			if (StringUtil.isBlank(sendUrl)) {
				// 环境变量配置活体检测服务器地址
				log.error("缓存中未配置静默活体服务的调用地址，流水号：" + swftno);
				sendUrl = env.getProperty("facelive.url");
			}
			HashMap<String, String> paraMap = new HashMap<String, String>();

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
				log.error("人像照片解密异常，流水号：" + swftno, e);
			}

			if (StringUtil.isBlank(picRStrBase64)) {
				out.setReturnCode(ReturnInfoEnums.FACELIVE_PICR_DOWN_FAILED.getCode());
				out.setReturnMessage(ReturnInfoEnums.FACELIVE_PICR_DOWN_FAILED.getMessage());
				log.error("人像照片为空");
				// 获取图片为空，直接存表返回
				logMap.put("rspCode", ReturnInfoEnums.FACELIVE_PICR_DOWN_FAILED.getCode());
				logMap.put("rspInfoCntt", ReturnInfoEnums.FACELIVE_PICR_DOWN_FAILED.getMessage());
				return out;
			}
			if (picRStrBase64 != null) {
				log.info("    ##########  静默服务调用 获取人像图片大小为：" + picRStrBase64.length() + "，流水号：" + swftno);
			}
			// 2 调用静默活体检测服务 并比对分值
			paraMap.put("image", picRStrBase64);
			paraMap.put("face_fields", ",faceliveness");
			log.info("    ##########  静默服务调用 ， URL：" + sendUrl);

			String rtnJson = null;
			String timeOutConf = cacheFactory.getJVMString(CacheConsts.JVM.WEB_FETCH_FACE_LIVE_TIMEOUT);
			long requestTime = 0;
			long startRequestTime = System.currentTimeMillis();
			// 调用静默活体服务
			try {
				rtnJson = requestFaceLive(timeOutConf, sendUrl, paraMap);
				long endRequestTime = System.currentTimeMillis();
				requestTime = endRequestTime - startRequestTime;
				log.info("=============facelive调用时长为：" + requestTime + " ms=================");
			} catch (Exception e) {
				long endRequestTime = System.currentTimeMillis();
				requestTime = endRequestTime - startRequestTime;
				log.info("=============facelive调用时长为：" + requestTime + " ms=================");
				log.error("reqFaceLiveFailedCode 静默活体调用服务异常", e);
			}

			// 调用成功，将调用响应报文存表
			logMap.put("backtoMsgCntt", rtnJson);
			logMap.put("requestTime", Long.toString(requestTime));
			log.info("    ##########  静默服务返回  rtnjson：" + rtnJson);

			// 静默活体服务返回信息为空，则认为调用服务发生了异常
			if (StringUtil.isNotBlank(rtnJson)) {
				Map<String, String> returnMap = null;
				try {
					returnMap = judgeFaceLiveResult(rtnJson, faceliveScore, swftno);
				} catch (Exception e) {
					log.error("静默活体分值判定发生异常，流水号为：" + swftno, e);
				}
				// 将调用信息保存在logMap 中
				if (returnMap != null) {
					logMap.put("rspCode", ReturnInfoEnums.PROCESS_SUCCESS.getCode());
					logMap.put("rspInfoCntt", ReturnInfoEnums.PROCESS_SUCCESS.getMessage());
					out.setReturnCode(ReturnInfoEnums.PROCESS_SUCCESS.getCode());// 默认调用成功
					out.setReturnMessage(ReturnInfoEnums.PROCESS_SUCCESS.getMessage());// 默认调用成功
					out.setBean(returnMap);
					logMap.putAll(returnMap);
				} else {
					logMap.put("rspCode", ReturnInfoEnums.PROCESS_FAILED.getCode());
					logMap.put("rspInfoCntt", ReturnInfoEnums.PROCESS_FAILED.getMessage());
					out.setReturnCode(ReturnInfoEnums.PROCESS_FAILED.getCode());
					out.setReturnMessage(ReturnInfoEnums.PROCESS_FAILED.getMessage());
				}
			} else {
				// 静默活体返回信息为空，保存mq
				logMap.put("idntifResult", CoConstants.RESULT_TYPE.EXCEPTION_CODE);
				logMap.put("rspCode", ReturnInfoEnums.PROCESS_FAILED.getCode());
				logMap.put("rspInfoCntt", ReturnInfoEnums.PROCESS_FAILED.getMessage());
				out.setReturnCode(ReturnInfoEnums.PROCESS_FAILED.getCode());
				out.setReturnMessage(ReturnInfoEnums.PROCESS_FAILED.getMessage());
				log.error("reqFaceLiveFailedCode 静默活体调用服务返回为空");
			}
		} catch (Exception e) {
			log.info("静默活体执行失败", e);
			out.setReturnCode(ReturnInfoEnums.PROCESS_FAILED.getCode());
			out.setReturnMessage(ReturnInfoEnums.PROCESS_FAILED.getMessage());
		} finally {
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			log.info("=============facelive调用时长为：" + totalTime + " ms=================");
			logMap.put("totalTime", Long.toString(totalTime));
			// 3 保存调用记录
			try {
				sendMQ(appSysID, appUserID, reqstSrcCode, bizTypeCode, swftno, logMap);
			} catch (Exception e) {
				log.error("静默活体发送消息队列异常", e);
			}
		}
		return out;
	}

	/**
	 * 静默活体判定返回结果分值
	 * @param rtnJson
	 * @param faceliveScore
	 * @param swftno
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Map<String, String> judgeFaceLiveResult(String rtnJson, String faceliveScoreIn, String swftno){
		String resultNum = null;
		String faceliveScore = faceliveScoreIn;
		Map<String, String> returnMap = new HashMap<String, String>();
		String idntifResult;//识别结果
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
					log.info("静默活体检测使用入参分值为空，使用配置分值为：" + faceliveScore);
					if (StringUtil.isEmpty(faceliveScore)) {
						log.info("静默活体检测使用默认分值，原配置分值为：" + faceliveScore + ",默认分值为" + FACELIVE_DEFAULT_SCORE);
						faceliveScore = FACELIVE_DEFAULT_SCORE;
					}
				}
				//进行静默活体分值判定
				if (faceScore < Float.parseFloat(faceliveScore)) {
					log.info("真人检测不通过：流水号：" + swftno + ",阈值=" + faceliveScore + "，实际比分=" + faceScore);
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
		return returnMap;
	}
	
	/**
	 * 发送http请求到静默活体服务器
	 * @param timeOutConf
	 * @param sendUrl
	 * @param paraMap
	 * @return
	 * @throws GeneralException
	 */
	@SuppressWarnings("rawtypes")
	private String requestFaceLive(String timeOutConf, String sendUrl, HashMap paraMap) throws GeneralException{
		String rtnJson = null;
		if (StringUtil.isBlank(timeOutConf)) {
			rtnJson = HttpUtil.sendHttpNameValuePair(sendUrl, paraMap, FACELIVE_DEFAULT_TIME_OUT);
			log.info("    ##########  静默服务调用，超时时间为空，使用默认超时时间：" + FACELIVE_DEFAULT_TIME_OUT);
		} else {
			String[] timeOutCfg = timeOutConf.split(",");
			if (timeOutCfg.length == 1) {
				
				int connectTime = Integer.parseInt(timeOutCfg[0]);
				rtnJson = HttpUtil.sendHttpNameValuePair(sendUrl, paraMap, connectTime);
			} else if (timeOutCfg.length == 2) {
				int connectTime = Integer.parseInt(timeOutCfg[0]);
				int readTime = Integer.parseInt(timeOutCfg[1]);
				rtnJson = HttpUtil.sendHttpNameValuePair(sendUrl, paraMap, connectTime, readTime);
			}
		}
		return rtnJson;
	}
	
	
	
	/**
	 * 发送消息队列 后期修改为发送到vertica
	 * @param appSysID
	 * @param appUserID
	 * @param requestSource
	 * @param busiType
	 * @param transactionId
	 * @param logMap
	 */
	private void sendMQ(String appSysID, String appUserID, String requestSource, String busiType, String transactionId,
			Map<String, String> logMap){
		long startTime = System.currentTimeMillis();
		try {
			String Msg = saveFaceLiveInfo(appSysID, appUserID, requestSource, busiType, transactionId, logMap);
			log.info("生成的业务日志信息成功，流水号：" + transactionId);
			KafkaUtil.transToVertica(Msg, KafkaConsts.TOPIC.CO_FACE_LIVE_INFO);
		} catch (Exception e) {
			log.error("发送日志异常", e);
		}
		long endTime = System.currentTimeMillis();
		log.info("=============保存日志时长为：" + (endTime - startTime) + " ms=================");
	}
	
	/**
	 * 将保存信息转化为消息
	 * @param appSysID
	 * @param appUserID
	 * @param requestSource
	 * @param busiType
	 * @param transactionId
	 * @param logMap
	 * @return
	 * @throws JsonFormatException
	 */
	private String saveFaceLiveInfo(String appSysID, String appUserID, String requestSource, String busiType,
			String transactionId, Map<String, String> logMap) throws JsonFormatException {
		String crtSysID = appSysID;
		String crtUserID = appUserID;
		CoFaceLiveInfoDO infoBean = new CoFaceLiveInfoDO();
		if (StringUtil.isBlank(crtSysID)) {
			crtSysID = AppCodeConsts.APP_SYS_ID.UNDEFINED;
		}
		if (StringUtil.isBlank(crtUserID)) {
			crtUserID =  AppCodeConsts.APP_USER_ID.UNDEFINED;
		}
		infoBean.setCrtUserId(crtUserID);
		infoBean.setCrtAppSysId(crtSysID);
		String uniqueSequence = null;
		try {
			uniqueSequence = basicUtil.getSequence(CoConstants.DB_NAME.FACE_LIVE);
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
		
		String requestTime = logMap.get("requestTime");
		String totalTime = logMap.get("totalTime");
		String photoPath = logMap.get("picRPath");
		String crkey = logMap.get("crkey");
		String confScore = logMap.get("confScore");
		String idntifRslt = logMap.get("idntifResult");

		infoBean.setBacktoMsgCntt(backtoMsgCntt);// 返回报文内容
		infoBean.setRspCode(rspCode);//返回码
		infoBean.setRspInfoCntt(rspInfoCntt);//返回信息
		infoBean.setIdntifFaceCnt(idntifFaceCnt);//识别人脸数
		infoBean.setIdntifScore(idntifScore);//分数
		infoBean.setBacktoMsgCntt(backtoMsgCntt);//静默活体服务返回报文
		infoBean.setRequestTime(requestTime);
		infoBean.setTotalTime(totalTime);
		infoBean.setPhotoPath(photoPath);
		infoBean.setConfScore(confScore);
		infoBean.setIdntifRslt(idntifRslt);
		infoBean.setCrkey(crkey);
		
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
		long startDownTime = System.currentTimeMillis();
		try {
			picStr = busiFileUpDownUtil.downloadBusiFileStr(picPath);
		} catch (Exception e) {
			picStr = null;
			log.error("静默活体服务下载图片异常", e);
		} 
		long endDownTime = System.currentTimeMillis();
		log.info("=============下载图片时长为：" + (endDownTime - startDownTime) + " ms=================");
		return picStr;
	}
}
