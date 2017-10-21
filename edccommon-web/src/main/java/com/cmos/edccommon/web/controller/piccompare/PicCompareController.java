package com.cmos.edccommon.web.controller.piccompare;

import com.cmos.common.bean.JsonFormatException;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.common.OutputObject;
import com.cmos.edccommon.beans.piccompare.CoPicCompareInfoDO;
import com.cmos.edccommon.beans.piccompare.PicCompareInDTO;
import com.cmos.edccommon.beans.piccompare.PicDoubleCompareInDTO;
import com.cmos.edccommon.utils.Base64;
import com.cmos.edccommon.utils.BsStaticDataUtil;
import com.cmos.edccommon.utils.FileUtil;
import com.cmos.edccommon.utils.HttpUtil;
import com.cmos.edccommon.utils.IOUtils;
import com.cmos.edccommon.utils.JsonUtil;
import com.cmos.edccommon.utils.StringUtil;
import com.cmos.msg.exception.MsgException;
import com.cmos.producer.client.MsgProducerClient;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
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
public class PicCompareController {
	private Logger log=LoggerFactory.getActionLog(PicCompareController.class);
	
	@RequestMapping(value = "/picdoublecompare", method = RequestMethod.POST)
	public OutputObject getPicCompare(@RequestBody PicDoubleCompareInDTO inParam ) {
		System.out.println("****************************"+inParam);
	
		return picDoubleCompare(inParam);
	}
	
	@RequestMapping(value = "/picCheck", method = RequestMethod.POST)
	public OutputObject getPicCheck(@RequestBody PicCompareInDTO inParam ) {
		System.out.println("****************************"+inParam);
	
		return picCheck(inParam);
	}
	
	@RequestMapping(value = "/picCompare", method = RequestMethod.POST)
	public OutputObject getPicCompare(@RequestBody PicCompareInDTO inParam ) {
		System.out.println("****************************"+inParam);
	
		return picCompare(inParam);
	}

	
	/**
	 * 人像比对接口
	 * @param inParam
	 * @return
	 * @date 2017-10-11 13:00:00
	 */
	private OutputObject picCheck(PicCompareInDTO inParam) {

		String swftno = inParam.getSwftno();
		String reqstSrcCode = inParam.getReqstSrcCode();
		String bizTypeCode = inParam.getBizTypeCode();
		String picRPath = inParam.getHndhldCredPhotoPath();
		String picTPath = inParam.getPicTPath();
		String picRType = inParam.getHndhldCredPhotoType();
		String picTType = inParam.getPicTType();
		
		InputStream picR = null;
		String picRStr = null;
		InputStream picT = null;
		String picTStr = null;

		Map<String, String> rtnMap = new HashMap<String, String>();
		OutputObject out = new OutputObject();
		rtnMap.put("compareResult", "false");
		out.setReturnCode("2999");
		out.setReturnMessage("人像比对失败");
		out.setBean(rtnMap);
		try {
		// 1 获取人像照片
	
		try {
			picR = FileUtil.download(picRPath);
			picRStr =Base64.encode(IOUtils.toByteArray(picR));
		} catch (Exception e) {
			picRStr = null;
			log.error("人像比对服务下载人像图片异常",e);
		}

		if (StringUtil.isNotEmpty(picRStr)) {
			rtnMap.put("compareResult", "false");
			out.setReturnMessage("人像照片下载异常");
			log.error("人像照片下载异常");
			return out;
		}
		// RealNameMsDesPlus Ms = new RealNameMsDesPlus(picKey);
		// picr = Ms.decrypt(picr);

		// 2 获取国政通头像or芯片头像

		try {
			picT = FileUtil.download(picTPath);
			picTStr =Base64.encode(IOUtils.toByteArray(picT));
		} catch (Exception e) {
			picTStr = null;
			log.error("人像比对服务下载标准图片异常", e);
		}
		if (StringUtil.isNotEmpty(picRStr)) {
			rtnMap.put("compareResult", "false");
			out.setReturnMessage("标准照片下载异常");
			return out;
		}
		
		// 3 调用人像比对服务
		String compareResult = sendPicCheck(picRStr, picTStr, "0", picRType,  picTType);
		rtnMap.put("compareResult", compareResult);
		out.setReturnCode("0000");
		out.setReturnMessage("人像比对成功");
		out.setBean(rtnMap);

		// 4 调用消息队列，保存调用日志记录到数据库
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> compareMap = (Map<String, Object>) JsonUtil.convertJson2Object(compareResult, Map.class);
			sendMQ(reqstSrcCode, bizTypeCode, swftno, compareMap);
		} catch (Exception e) {
			log.error("MQ保存消费信息出错", e);
		}
	} catch (Exception e1) {
		log.error("MQ保存消费信息出错");
	} finally {
		if (null != picT) {
			try {
				picT.close();
			} catch (IOException e) {

			}
		}
		if (null != picR) {
			try {
				picR.close();
			} catch (IOException e) {
			}
		}
	}
	return out;

	}

	/**
	 * 人像比对判定接口（两张图片）
	 * @param inParam
	 * @return
	 * @date 2017-10-11 13:00:00
	 */
	@SuppressWarnings("unchecked")
	private OutputObject picCompare(PicCompareInDTO inParam) {

		String swftno = inParam.getSwftno();
		String reqstSrcCode = inParam.getReqstSrcCode();
		String bizTypeCode = inParam.getBizTypeCode();

		String picRPath = inParam.getHndhldCredPhotoPath();
		String picTPath = inParam.getPicTPath();
		String picRType = inParam.getHndhldCredPhotoType();
		String picTType = inParam.getPicTType();
		String confidenceScore = inParam.getConfidenceScore();
		Map<String, String> rtnMap = new HashMap<String, String>();
		OutputObject out = new OutputObject();
		rtnMap.put("compareResult", "false");
		out.setReturnCode("2999");
		out.setReturnMessage("人像比对失败");
		out.setBean(rtnMap);
		InputStream picR = null;
		InputStream picT = null;
		try {
			// 1 获取人像照片

			String picRStr = null;
			try {
				picR = FileUtil.download(picRPath);
				picRStr = Base64.encode(IOUtils.toByteArray(picR));
			} catch (Exception e) {
				picRStr = null;
				log.error("人像比对服务下载人像图片异常", e);
			}

			if (StringUtil.isNotEmpty(picRStr)) {
				rtnMap.put("compareResult", "false");
				out.setReturnMessage("人像照片下载异常");
				log.error("人像照片下载异常");
				return out;
			}
			// RealNameMsDesPlus Ms = new RealNameMsDesPlus(picKey);
			// picr = Ms.decrypt(picr);
			
			// 2 获取国政通头像or芯片头像
			String picTStr = null;
			try {
				picT = FileUtil.download(picTPath);
				picTStr = Base64.encode(IOUtils.toByteArray(picT));
			} catch (Exception e) {
				picTStr = null;
				log.error("人像比对服务下载标准图片异常", e);
			}
			if (StringUtil.isNotEmpty(picRStr)) {
				rtnMap.put("compareResult", "false");
				out.setReturnMessage("标准照片下载异常");
				return out;
			}

			// 3 调用人像比对服务
			String compareResult = sendPicCheck(picRStr, picTStr, "0", picRType, picTType);
			rtnMap.put("compareResult", compareResult);
			out.setReturnCode("0000");
			out.setReturnMessage("人像比对成功");
			out.setBean(rtnMap);

			// 4 人像比对分值判定
			rtnMap.put("compareResult", compareResult);
			if (StringUtil.isNotEmpty(compareResult)) {

				Map<String, Object> rtMap = (Map<String, Object>) JsonUtil.convertJson2Object(compareResult, Map.class);
				Map<String, String> compareMap = getCompareResult(rtMap, confidenceScore);

				out.setReturnCode("0000");
				out.setReturnMessage("人像比对成功");
				out.setBean(compareMap);

				// 5调用消息队列，保存调用日志记录到数据库
				try {
					// 发送消息队列的内容
					sendMQ(reqstSrcCode, bizTypeCode, swftno, rtMap);
				} catch (MsgException e1) {
					log.error("MQ保存消费信息出错");
				}
			}

		} catch (Exception e1) {
			log.error("MQ保存消费信息出错");
		} finally {
			if (null != picT) {
				try {
					picT.close();
				} catch (IOException e) {

				}
			}
			if (null != picR) {
				try {
					picR.close();
				} catch (IOException e) {
				}
			}
		}
		return out;

	}


	/**
	 * 人像比对判定接口（三张图片）
	 * 如果国政通获取图片为空,nfc芯片图片不为空 则 优先比对nfc芯片图片 没有二次比对,如果国政通图片不为空 先比对国政通, 国政通比对失败或者比对不一致时,使用nfc芯片图片进行比对
	 * @param inParam
	 * @return
	 * @date 2017-10-14 11:00:00
	 */
	@SuppressWarnings("unchecked")
	private OutputObject picDoubleCompare(PicDoubleCompareInDTO inParam) {
		String swftno = inParam.getSwftno();
		String reqstSrcCode = inParam.getReqstSrcCode();
		String bizTypeCode = inParam.getBizTypeCode();

		String picRPath = inParam.getHndhldCredPhotoPath();
		String picTPath = inParam.getPicStoinPath();
		String picRType = inParam.getHndhldCredPhotoType();
		String picTGPath = inParam.getGztAvtrPath();
		
		String picGztAvtrScore = inParam.getGztAvtrScore();
		String picPicStoinScore = inParam.getPicStoinScore();
		
		
		Map<String, String> rtnMap = new HashMap<String, String>();
		OutputObject out = new OutputObject();
		rtnMap.put("compareResult", "false");
		out.setReturnCode("2999");
		out.setReturnMessage("人像比对失败");
		out.setBean(rtnMap);
		InputStream picR = null;
		InputStream picT = null;
		InputStream picTG = null;
		try {
			// 1 获取人像照片
			String picRStr = null;
			try {
				picR = FileUtil.download(picRPath);
				picRStr = Base64.encode(IOUtils.toByteArray(picR));
			} catch (Exception e) {
				picRStr = null;
				log.error("人像比对服务下载人像图片异常", e);
			}

			if (StringUtil.isEmpty(picRStr)) {
				rtnMap.put("compareResult", "false");
				out.setReturnMessage("人像照片下载异常");
				log.error("人像照片下载异常");
				return out;
			}
			// RealNameMsDesPlus Ms = new RealNameMsDesPlus(picKey);
			// picr = Ms.decrypt(picr);

			// 2.1 获取国政通头像
			String picTGStr = null;
			try {
				picTG = FileUtil.download(picTGPath);
				picTGStr = Base64.encode(IOUtils.toByteArray(picTG));
			} catch (Exception e) {
				picTGStr = null;
				log.error("人像比对服务下载标准图片异常", e);
			}
			
			boolean needCheckTPic = false;
			//如果国政通获取图片为空,nfc芯片图片不为空 则 优先比对nfc芯片图片
			if (StringUtil.isEmpty(picTGStr)) {
				needCheckTPic = true;
			} else {
				// 2.2 调用人像比对服务 比对芯片图片
				String compareResult = sendPicCheck(picRStr, picTGStr, "0", picRType, "g");// 0为同一人
				//如果国政通图片不为空 先比对国政通, 国政通比对失败或者比对不一致时,使用nfc芯片图片进行比对																			// 1：不为同一人
				
				// 2.3 国政通头像比对分值判定
				if (StringUtil.isNotEmpty(compareResult)) {
					Map<String, Object> rtMap =  (Map<String, Object>) JsonUtil.convertJson2Object(compareResult, Map.class);
					Map<String, String> compareMap = getCompareResult(rtMap, picGztAvtrScore);
					String verifyState = compareMap.get("verifyState");
					if ("0".equals(verifyState)) {
						out.setReturnCode("0000");
						out.setReturnMessage("人像比对成功");
						out.setBean(compareMap);
					} else {
						needCheckTPic = true;
					}
					// 2.4 国政通头像对比结果 通过MQ异步保存调用记录
					try {
						sendMQ(reqstSrcCode, bizTypeCode, swftno, rtMap);
					} catch (MsgException e1) {
						log.error("MQ保存消费信息出错");
					}	
				}else{
					needCheckTPic = true;
				}
					
			}
			//比对芯片头像 
			if (needCheckTPic) {
				String picTStr = null;
				// 3.1 获取芯片头像
				try {
					picT = FileUtil.download(picTPath);
					picTStr = Base64.encode(IOUtils.toByteArray(picT));
				} catch (Exception e) {
					picTStr = null;
					log.error("人像比对服务下载芯片图片异常", e);
				}
				if (StringUtil.isEmpty(picTStr)) {
					rtnMap.put("compareResult", "false");
					out.setReturnMessage("标准照片下载异常");
					return out;
				}
				// 3.2 调用人像比对服务 比对芯片头像
				String compareResult = sendPicCheck(picRStr, picTStr, "0", picRType, "x");

				if (StringUtil.isNotEmpty(compareResult)) {
					Map<String, Object> rtMap =  (Map<String, Object>) JsonUtil.convertJson2Object(compareResult, Map.class);
					Map<String, String> compareMap = getCompareResult(rtMap, picPicStoinScore);
					out.setReturnCode("0000");
					out.setReturnMessage("人像比对成功");
					out.setBean(compareMap);
					
					// 3.4 芯片头像对比结果 通过MQ异步保存调用记录
					try {
						sendMQ(reqstSrcCode, bizTypeCode, swftno, rtMap);
					} catch (MsgException e1) {
						log.error("MQ保存消费信息出错");
					}	
				}
			}
		} catch (Exception e1) {
			log.error("人像比对判定接口（三张图片）异常", e1);
		} finally {	
			if (null != picT) {
				try {
					picT.close();
				} catch (IOException e) {

				}
			}
			if (null != picTG) {
				try {
					picTG.close();
				} catch (IOException e) {
				}
			}

			if (null != picR) {
				try {
					picR.close();
				} catch (IOException e) {
				}
			}
		}
		return out;
	}
	
	/**
	 * 
	 * @param picR 头像照片or手持人像 
	 * @param picT 国政通or芯片
	 * @param returnPicRFlag 是否回传头像剪裁图片
	 * @param picRType 第一个参数是头像照片还是手持人像照片  t:头像 r:手持人像 
	 * @param picTType  第二个参数是国政通照片还是芯片照片   g:国政通 x:芯片
	 * @return
	 * @throws BusiException
	 */
	private String sendPicCheck(String base64StrR, String base64StrT,String returnPicRFlag ,String picRType, String picTType) {
		String rt = "";
		
		//1封装报文
		Map<String, Object> reqJsonMap = new HashMap<String, Object>();
		reqJsonMap.put("busiCode", "picCheck");
		reqJsonMap.put("version", "1.0");
		Map<String, Object> reqInfoMap = new HashMap<String, Object>();
		reqInfoMap.put("picRIn", base64StrR);
		reqInfoMap.put("isReturnPicR", returnPicRFlag);// 不回传图片
		reqInfoMap.put("picTIn", base64StrT);

		if ("t".equals(picRType)) {
			// 第一个图片是头像---不传或者0 的时候不进行手持证件照无证件的检测
			reqInfoMap.put("IsCHeckHand", "0");
		} else if ("r".equals(picRType)) {
			// 第一个图片是手持证件照---当为1的时候进行手持证件照无证件的检测
			String isHeadCheck = BsStaticDataUtil.getCodeValue("PIC_CHECK_FETCH", "PIC_ISHEAD", "JVM");
			if (StringUtil.isEmpty(isHeadCheck)) {
				isHeadCheck = "0";
			}
			reqInfoMap.put("IsCHeckHand", isHeadCheck);
		}

		if ("g".equals(picTType)) {
			// 第二个图片是国政通--当不传或者为0的时候需要去网纹
			reqInfoMap.put("IsHasMesh", "0");
		} else if ("x".equals(picTType)) {
			// 第二个图片是芯片--当为1的时候不需要去网纹
			reqInfoMap.put("IsHasMesh", "1");
		}

		// 只有进行手持证件照判断是否含有证件照才传值
		String perScore = BsStaticDataUtil.getCodeValue("PIC_CHECK_FETCH", "PIC_CHECK_PER_SCORE", "JVM");
		if (StringUtil.isEmpty(perScore)) {
			perScore = "0";
		}
		reqInfoMap.put("HandSorce", perScore);

		// 自拍照是否有人像判定分值
		String portraitSorce = BsStaticDataUtil.getCodeValue("PIC_CHECK_FETCH","PIC_CHECK_PORTRAIT_SCORE","JVM");
		if (StringUtil.isEmpty(portraitSorce)) {
			portraitSorce = "0";
		}
		reqInfoMap.put("PortraitSorce", portraitSorce);

		reqJsonMap.put("reqInfo", reqInfoMap);

		String reqJson = JsonUtil.convertObject2Json(reqJsonMap);
		String svUrl = BsStaticDataUtil.getCodeValue("OL_WEB_FETCH", "PIC_CHECK_URL", "JVM");

		String timeOutConf = BsStaticDataUtil.getCodeValue("OL_WEB_FETCH", "PIC_CHECK_TIMEOUT", "JVM");
		int timeOut = Integer.parseInt(timeOutConf);
		try {
			rt =  HttpUtil.sendHttpPostEntityNolog(svUrl, reqJson,timeOut);
			if (StringUtil.isEmpty(rt)) {
				rt = "{\"resultType\":\"-2\",\"resultMsg\":\"接口异常\",\"returnInfo\":{}}";
			}
		} catch (Exception e) {

			rt = "{\"resultType\":\"-3\",\"resultMsg\":\"接口异常\",\"returnInfo\":{}}";
		}
		return rt;

	}
	


	/**
	 * 当resultType=0时,会有resultType,score,verifyState返回
	 * 否则只有resultType返回
	 * @param rtMap 人像对比返回map
	 * @param busiType  业务类型   provcode $ 11
	 * @param custCertNo
	 * @return
	 * @throws BusiException
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> getCompareResult(Map<String, Object> rtMap, String rangeStr) {
	
		Map<String, String> returnMap = new HashMap<String, String>();
	
		//0成功1自拍照无人像 2 自拍照人像模糊 3标准照片无人像  4标准照片人像模糊 5 违规处理自拍照  6非手持证件照  -1其他情况
		String resultType = (String) rtMap.get("resultType");
		
		Map<String,String> returnInfo = ((Map<String, String>) rtMap.get("returnInfo"));
		String verifyState ; // 是否是同一人 0是 1否
		returnMap.put("resultType", resultType);

		if(returnInfo!=null){
			returnMap.put("CheckHandResult", returnInfo.get("CHeckHandResult"));//自拍照手持证件判断比分
			returnMap.put("CheckPortraitResult", returnInfo.get("CheckPortraitResult"));//自拍照人像判断比分
			returnMap.put("ChekHackPic", returnInfo.get("ChekHackPic"));//picZIn判断为Hack照片的比分
		}
		
		if("nohead".equals(resultType)){
			returnMap.put("similarity", "0");
			returnMap.put("verifyState", "1");
			return returnMap;
		}
		

		// 判断是否为手持证件照
		// 自拍照识别分数
		if("5".equals(resultType)){
//			String checkHandResult = ((Map<String, String>) rtMap.get("returnInfo")).get("CHeckHandResult");
//			String personCrdImgScore = BaseDataCodeActionNew.getCodeValue("CHECK_PERSON_CRD_SCORE_RANGE", "personCrdImg");
//			String[] scores = personCrdImgScore.split("-");
//			int min = Integer.parseInt(scores[0]);
//			int max = Integer.parseInt(scores[1]);
//			// 是否为手持证件照 提示语
//			if(min >= Integer.parseInt(checkHandResult) || max <= Integer.parseInt(checkHandResult)){
//				returnMap.put("codeValue", getRemindString(CODE_TYPE, busiType, resultType, null,null));
//				return returnMap;
//			}
			return returnMap;
		}
			
		if ("0".equals(resultType)&&returnInfo!=null) {
			// 显示对比分数
			String score = returnInfo.get("similarity");
			if (StringUtil.isEmpty(rangeStr)) {
				rangeStr = BsStaticDataUtil.getCodeValue("CHECK_PERSON_CRD_SCORE_RANGE", "DEFAULT_VALUE", "JVM");
			}
			verifyState = checkInRange(rangeStr, score,"0","1");// 是否是同一人 0是 1否
			returnMap.put("similarity", score);		
			returnMap.put("verifyState", verifyState);

		} else {
			
		}
		return returnMap;
	}
	

	/**
	 * 检查是否在分数段内 
	 * @param rangeStr 配置的分数区间  0-50|80-100
	 * @param similarity  分数
	 * @param matchValue  符合条件时的返回值
	 * @param notMathchDefaultVal 不符合条件时的返回值
	 * @return
	 */
	private String checkInRange(String rangeStr, String similarity, String matchValue,
			String notMathchDefaultVal) {
		if (StringUtil.isEmpty(rangeStr) || StringUtil.isEmpty(similarity)) {
			return notMathchDefaultVal;
		}
		final double errorValue = -100;
		final double simiDouble = convertStrToDouble(similarity, errorValue);
		if (simiDouble == errorValue) {
			return notMathchDefaultVal;
		}
		double lowerBound;
		double upperBound;
		String[] rangeArr = rangeStr.split("\\|");
		String[] scoreArr;
		for (String str : rangeArr) {
			if (StringUtil.isEmpty(str)) {
				continue;
			}
			scoreArr = str.split("-");
			if (scoreArr.length == 1) {
				lowerBound = convertStrToDouble(scoreArr[0], errorValue);
				upperBound = convertStrToDouble(scoreArr[0], errorValue);
			} else {
				lowerBound = convertStrToDouble(scoreArr[0], errorValue);
				upperBound = convertStrToDouble(scoreArr[1], errorValue);
			}
			if (simiDouble >= lowerBound && simiDouble <= upperBound) {
				return matchValue;
			}
		}
		return notMathchDefaultVal;
	}
	

	/**
	 * 字符串转为double
	 */
	private double convertStrToDouble(String str, double defaultVal) {
		try {
			if (!StringUtil.isEmpty(str)) {
				return Double.parseDouble(str.trim());
			}
		} catch (Exception e) {
			log.error("convertStrToDouble error", e);
		}
		return defaultVal;
	}
	
	/**
	 * requestSource 比对请求源 例如：371
	 * busiType 比对业务类型 例如：22、11
	 * transactionId 业务流水号
	 * compareResult 人像比对结果
	 * @throws JsonFormatException 
	 */
	@SuppressWarnings("unchecked")
	public String saveCompareInfo(String requestSource, String busiType, String transactionId, Map<String,Object> compareResult) throws JsonFormatException {

		CoPicCompareInfoDO infoBean = new CoPicCompareInfoDO();
		infoBean.setCrtTime(new Timestamp(new Date().getTime()));
		infoBean.setRspCode(requestSource);
		infoBean.setBizTypeCode(busiType);
		infoBean.setswftno(transactionId);

		// 如果比对结果不为空 保存比对结果
		if (null != compareResult) {
			String resultType = (String) compareResult.get("resultType");
			infoBean.setCmprRslt(resultType);// 0成功 1手持证件照无人像 3 公安部照片无人像 -1调用失败
			Map<String, String> returnInfo = ((Map<String, String>) compareResult.get("returnInfo"));

			if (null != returnInfo) {
				String score = returnInfo.get("similarity");
				if (StringUtil.isNotEmpty(returnInfo.get("PicTout"))) {
					returnInfo.put("PicTout", "图片已替换");
				}
				infoBean.setCmprScore(score);

				String compareStr = returnInfo.toString();
				if (compareStr.length() > 2000) {
					compareStr = compareStr.substring(0, 2000);
				}
				infoBean.setBacktoMsgCntt(compareStr);
			}
		}
		String resultStr = infoBean.toJSON().toString();
		return resultStr;
	}

	/**
	 * 发送消息队列
	 * @throws MsgException
	 * @throws JsonFormatException 
	 */
	private void sendMQ(String requestSource, String busiType, String transactionId, Map<String,Object> compareResult) throws MsgException, JsonFormatException{
		String Msg = saveCompareInfo(requestSource, busiType, transactionId, compareResult);
		MsgProducerClient.getRocketMQProducer().send("SMSNotice", Msg);
	}

}
