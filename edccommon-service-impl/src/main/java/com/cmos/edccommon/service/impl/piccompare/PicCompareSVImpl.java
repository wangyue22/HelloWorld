package com.cmos.edccommon.service.impl.piccompare;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.common.message.Message;
import com.cmos.consumer.client.MsgConsumerClient;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.common.InputObject;
import com.cmos.edccommon.beans.common.OutputObject;
import com.cmos.edccommon.beans.piccompare.CoPicCompareInfoDO;
import com.cmos.edccommon.dao.piccompare.CoPicCompareInfoDAO;
import com.cmos.edccommon.iservice.piccompare.IPicCompareSV;
import com.cmos.edccommon.service.impl.mq.MsgConsumerBatchImpl;
import com.cmos.edccommon.service.impl.mq.MsgConsumerImpl;
import com.cmos.edccommon.utils.Base64;
import com.cmos.edccommon.utils.FileDeal;
import com.cmos.edccommon.utils.IOUtils;
import com.cmos.msg.exception.MsgException;
import com.cmos.producer.client.MsgProducerClient;

/**
 * 人像比对
 *
 * @author xdx
 * 
 */
@Service(group = "edcco")
public class PicCompareSVImpl implements IPicCompareSV {
	Logger log=LoggerFactory.getActionLog(PicCompareSVImpl.class);
	
//	@Autowired
//	LocalTransactionExecuter excutor;

	@Autowired
	private CoPicCompareInfoDAO dao;

	/**
	 * just test!
	 * 
	 * @param param
	 * @return
	 * @date 2017-10-10 17:00:00
	 */
	private String savePicCompareLog(InputObject param) {
		CoPicCompareInfoDO resultBean = new CoPicCompareInfoDO();
		resultBean.setCmprId(System.currentTimeMillis());
		resultBean.setCrtUserId("test");
		Date nowTime = new Date(System.currentTimeMillis());
		resultBean.setCrtTime(nowTime);
		resultBean.setCrtAppSysId("test");
		resultBean.setSplitName("201710");
		int result = dao.insert(resultBean);
		return result + "";

	}

	/**
	 * just test!
	 * 
	 * @param inParam
	 * @return
	 * @date 2017-10-10 17:00:00
	 */
	public String picCompareTest(InputObject inParam) {
		savePicCompareLog(inParam);
		return savePicCompareLog(inParam);
	}
	
	/**
	 * 人像比对接口
	 * @param inParam
	 * @return
	 * @date 2017-10-11 13:00:00
	 */
	public OutputObject picCheck(InputObject inParam) {
		Map<String, String> params = inParam.getParams();
		String whntwkUniqBizSwftno = params.get("whntwkUniqBizSwftno");
		String reqstSrcCode = params.get("reqstSrcCode");
		String bizTypeCode = params.get("bizTypeCode");
		String codeValue = params.get("codeValue");
		String picRPath = params.get("picRPath");
		String picTPath = params.get("picTPath");
		String returnPicRFlag = params.get("returnPicRFlag");
		String picRType = params.get("picRType");
		String picTType = params.get("picTType");

		Map<String, String> rtnMap = new HashMap<String, String>();
		OutputObject out = new OutputObject();
		rtnMap.put("compareResult", "false");
		out.setReturnCode("2999");
		out.setReturnMessage("人像比对失败");
		out.setBean(rtnMap);

		// 1 获取人像照片
		InputStream picR = null;
		String picRStr = null;
		try {
			picR = FileDeal.download(picRPath);
			picRStr =Base64.encode(IOUtils.toByteArray(picR));
		} catch (Exception e) {
			picRStr = null;
			log.error("人像比对服务下载人像图片异常",e);
		}

		
		if (StringUtils.isNotBlank(picRStr)) {
			rtnMap.put("compareResult", "false");
			out.setReturnMessage("人像照片下载异常");
			log.error("人像照片下载异常");
			return out;
		}
		// RealNameMsDesPlus Ms = new RealNameMsDesPlus(picKey);
		// picr = Ms.decrypt(picr);

		// 2 获取国政通头像or芯片头像
		InputStream picT = null;
		String picTStr = null;
		try {
			picT = FileDeal.download(picTPath);
			picTStr =Base64.encode(IOUtils.toByteArray(picT));
		} catch (Exception e) {
			picTStr = null;
			log.error("人像比对服务下载标准图片异常", e);
		}
		if (StringUtils.isNotBlank(picRStr)) {
			rtnMap.put("compareResult", "false");
			out.setReturnMessage("标准照片下载异常");
			return out;
		}
		
		// 3 调用人像比对服务
		String compareResult = sendPicCheck(picRStr, picTStr,  "0", picRType,  picTType);
		rtnMap.put("compareResult", compareResult);
		out.setReturnCode("0000");
		out.setReturnMessage("人像比对成功");
		out.setBean(rtnMap);

		// 4 调用消息队列，保存调用日志记录到数据库
		try {
			// TODO 填写消息队列的内容
			sendMQ();
		} catch (MsgException e1) {
			log.error("MQ保存消费信息出错");
		}

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
		return out;
	}

	/**
	 * 人像比对判定接口（两张图片）
	 * @param inParam
	 * @return
	 * @date 2017-10-11 13:00:00
	 */
	public OutputObject picCompare(InputObject inParam) {
		Map<String, String> params = inParam.getParams();
		String whntwkUniqBizSwftno = params.get("whntwkUniqBizSwftno");
		String reqstSrcCode = params.get("reqstSrcCode");
		String bizTypeCode = params.get("bizTypeCode");
	
		String picRPath = params.get("picRPath");
		String picTPath = params.get("picTPath");
		String returnPicRFlag = params.get("returnPicRFlag");
		String picRType = params.get("picRType");
		String picTType = params.get("picTType");

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
				picR = FileDeal.download(picRPath);
				picRStr = Base64.encode(IOUtils.toByteArray(picR));
			} catch (Exception e) {
				picRStr = null;
				log.error("人像比对服务下载人像图片异常", e);
			}

			if (StringUtils.isNotBlank(picRStr)) {
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
				picT = FileDeal.download(picTPath);
				picTStr = Base64.encode(IOUtils.toByteArray(picT));
			} catch (Exception e) {
				picTStr = null;
				log.error("人像比对服务下载标准图片异常", e);
			}
			if (StringUtils.isNotBlank(picRStr)) {
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

			if (StringUtils.isNotBlank(compareResult)) {

				Map<String, Object> rtMap = JSON.parseObject(compareResult, Map.class);
				Map<String, String> compareMap = getCompareResult(rtMap, bizTypeCode);

				out.setReturnCode("0000");
				out.setReturnMessage("人像比对成功");
				out.setBean(compareMap);
			}

			// 5调用消息队列，保存调用日志记录到数据库
			try {
				// TODO 填写消息队列的内容
				sendMQ();
			} catch (MsgException e1) {
				log.error("MQ保存消费信息出错");
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
			String isHeadCheck = "";// BaseDataCodeActionNew.getCodeValue("OL_WEB_FETCH",
									// "PIC_CHECK_URL_ISHEAD");
			if (StringUtils.isBlank(isHeadCheck)) {
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
		String perScore = "";// BaseDataCodeActionNew.getCodeValue("OL_WEB_FETCH",
								// "PIC_CHECK_URL_PER_SCORE");
		if (StringUtils.isBlank(perScore)) {
			perScore = "0";
		}
		reqInfoMap.put("HandSorce", perScore);

		// 自拍照是否有人像判定分值
		String portraitSorce = "";// BaseDataCodeActionNew.getCodeValue("OL_WEB_FETCH",
									// "PIC_CHECK_URL_PORTRAIT_SCORE");
		if (StringUtils.isBlank(portraitSorce)) {
			portraitSorce = "0";
		}
		reqInfoMap.put("PortraitSorce", portraitSorce);

		reqJsonMap.put("reqInfo", reqInfoMap);

		String reqJson = JSON.toJSONString(reqJsonMap);
		String svUrl = "";// BaseDataCodeActionNew.getDataByTypeAndName("OL_WEB_FETCH",
							// "PIC_CHECK_URL");

		String timeOutConf = "";// BaseDataCodeActionNew.getCodeValue("OL_WEB_FETCH",
								// "PIC_CHECK_TIMEOUT");
		int timeOut = Integer.parseInt(timeOutConf);
		try {
			// TODO
			rt = "";// HttpUtil.sendHttpPostEntityNolog(svUrl, reqJson,
					// timeOut);
			if (StringUtils.isBlank(rt)) {
				rt = "{\"resultType\":\"-2\",\"resultMsg\":\"接口异常\",\"returnInfo\":{}}";
			}
		} catch (Exception e) {

			rt = "{\"resultType\":\"-3\",\"resultMsg\":\"接口异常\",\"returnInfo\":{}}";
		}
		return rt;

	}
	

	/**
	 * 当resultType=0时,会有resultType,score,verifyState返回
	 * 其余则只有resultType返回
	 * @param rtMap 人像对比返回map
	 * @param busiType  业务类型   provcode $ 11
	 * @param custCertNo
	 * @return
	 * @throws BusiException
	 */
	private Map<String, String> getCompareResult(Map<String, Object> rtMap, String rangeStr) {
	
		Map<String, String> returnMap = new HashMap<String, String>();
	
		//0成功1自拍照无人像 2 自拍照人像模糊 3标准照片无人像  4标准照片人像模糊 5 违规处理自拍照  6非手持证件照  -1其他情况
		String resultType=(String) rtMap.get("resultType");
		
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
			if (StringUtils.isBlank(rangeStr)) {
				rangeStr ="";//TODO 默认分值置信区间
//				rangeStr = BaseDataCodeActionNew.getCodeValue("CHECK_PERSON_CRD_SCORE_RANGE", "personCrdImg");
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
	 * 
	 * @param rangeStr 配置的分数区间  0-50|80-100
	 * @param similarity  分数
	 * @param matchValue  符合条件时的返回值
	 * @param notMathchDefaultVal 不符合条件时的返回值
	 * @return
	 */
	private String checkInRange(String rangeStr, String similarity, String matchValue,
			String notMathchDefaultVal) {
		if (StringUtils.isBlank(rangeStr) || StringUtils.isBlank(similarity)) {
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
			if (StringUtils.isBlank(str)) {
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
			if (!StringUtils.isBlank(str)) {
				return Double.parseDouble(str.trim());
			}
		} catch (Exception e) {
			log.error("convertStrToDouble error", e);
		}
		return defaultVal;
	}

	@Test
	public void sendMQ() throws MsgException{
		MsgProducerClient.getRocketMQProducer().send("SMSNotice","您已成功充值100元");
//		MsgProducerClient.getRocketMQProducer().sendTransMsg("SMSNotice","13951818259" ,"您已成功充值100元", excutor);
		consumerMQ();
	}
	
	public void consumerMQ() throws MsgException {
		MsgConsumerClient.getRocketMQConsumer().subscribe("SMSNotice", new MsgConsumerImpl());
//		MsgConsumerClient.getRocketMQConsumer().pullSchedule("SMSNotice", 5, 20, new MsgConsumerBatchImpl());
	}
	
	
	public static void main(String[] args) throws MsgException {
		MsgProducerClient.getRocketMQProducer().send("SMSNotice","您已成功充值100元");
	}
	
}
