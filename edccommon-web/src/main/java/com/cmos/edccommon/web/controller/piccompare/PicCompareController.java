package com.cmos.edccommon.web.controller.piccompare;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.core.env.Environment;

import com.cmos.common.bean.JsonFormatException;
import com.cmos.common.exception.GeneralException;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.common.EdcCoOutDTO;
import com.cmos.edccommon.beans.piccompare.CoPicCompareInfoDO;
import com.cmos.edccommon.beans.piccompare.PicCompareInDTO;
import com.cmos.edccommon.beans.piccompare.PicDoubleCompareInDTO;
import com.cmos.edccommon.utils.Base64;
import com.cmos.edccommon.utils.HttpUtil;
import com.cmos.edccommon.utils.JsonUtil;
import com.cmos.edccommon.utils.StringUtil;
import com.cmos.edccommon.utils.consts.CacheConsts;
import com.cmos.edccommon.utils.consts.CoConstants;
import com.cmos.edccommon.utils.consts.MqConstants;
import com.cmos.edccommon.utils.des.MsDesPlus;
import com.cmos.edccommon.web.cache.CacheFatctoryUtil;
import com.cmos.edccommon.web.fileupdown.FileUpDownUtil;
import com.cmos.msg.exception.MsgException;
import com.cmos.producer.client.MsgProducerClient;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value = "/co")
public class PicCompareController {
//	@Autowired
//    private Environment env;
	
	@Autowired
	private CacheFatctoryUtil cacheFactory;
	
	@Autowired
	private FileUpDownUtil fileUpDownUtil;
	
	private static Logger log=LoggerFactory.getActionLog(PicCompareController.class);
	
	@RequestMapping(value = "/picdoublecompare", method = RequestMethod.POST)
	public EdcCoOutDTO getPicCompare(@RequestBody PicDoubleCompareInDTO inParam ) {
		log.info("****************************"+inParam);
	
		return picDoubleCompare(inParam);
	}
	
	@RequestMapping(value = "/picCheck", method = RequestMethod.POST)
	public EdcCoOutDTO getPicCheck(@RequestBody PicCompareInDTO inParam ) {
		log.info("****************************"+inParam);
	
		return picCheck(inParam);
	}
	
	@RequestMapping(value = "/picCompare", method = RequestMethod.POST)
	public EdcCoOutDTO getPicCompare(@RequestBody PicCompareInDTO inParam ) {
		log.info("****************************"+inParam);
		return picCompare(inParam);
	}
	
	/**
	 * 人像比对接口
	 * @param inParam
	 * @return
	 * @date 2017-10-11 13:00:00
	 */
	@SuppressWarnings("unchecked")
	private EdcCoOutDTO picCheck(PicCompareInDTO inParam) {

		String swftno = inParam.getSwftno();
		String reqstSrcCode = inParam.getReqstSrcCode();
		String bizTypeCode = inParam.getBizTypeCode();
		String picRPath = inParam.getPhotoPath();
		String picTPath = inParam.getPicTPath();
		String picRType = inParam.getPhotoType();
		String picTType = inParam.getPicTType();
		String crkey = inParam.getCrkey();//人像图片加密秘钥
		String picRStr = null;
		String picTStr = null;
		String picRBase64Str = null;
		String picTBase64Str = null;
		Map<String, String> rtnMap = new HashMap<String, String>();
		EdcCoOutDTO out = new EdcCoOutDTO();
		// 1 获取人像照片
		picRStr = downloadPic(picRPath);

		if (StringUtil.isEmpty(picRStr)) {
			out.setReturnCode("2999");
			out.setReturnMessage("人像照片下载异常");
			log.error("人像照片下载异常");
			return out;
		}
		
		try {
			MsDesPlus Ms = new MsDesPlus(crkey);
			String picRDecStr = Ms.decrypt(picRStr);//解密后的图片字符串
			if(StringUtil.isNotEmpty(picRDecStr)){//图片本身已将转为Base64
				picRBase64Str = Base64.encode(picRDecStr.getBytes("ISO8859-1"));//解密后的Base64字符串
			}
//			picRBase64Str = picRDecStr;
		} catch (Exception e1) {
			out.setReturnCode("2999");
			out.setReturnMessage("人像照片解密异常");
			log.error("人像照片解密异常");
			return out;
		}
		
		
		// 2 获取国政通头像or芯片头像	
		try {
			String picRDecStr = null;
			if (CoConstants.PIC_TYPE.PIC_GZT.equalsIgnoreCase(picTType)) {
				picTStr = downloadGZTPic(picTPath);
				if (StringUtil.isEmpty(picTStr)) {
					out.setReturnCode("2999");
					out.setReturnMessage("国政通照片下载异常");
					return out;
				}
				picTBase64Str = picRStr;// 国政通图片字符串不用解密
			}else if(CoConstants.PIC_TYPE.PIC_STOIN.equalsIgnoreCase(picTType)){
				picTStr = downloadPic(picTPath);
				if (StringUtil.isEmpty(picTStr)) {
					out.setReturnCode("2999");
					out.setReturnMessage("标准照片下载异常");
					return out;
				}
				MsDesPlus Ms = new MsDesPlus(crkey);
				picRDecStr = Ms.decrypt(picRStr);// 解密后的图片字符串   NFC 芯片头像需要解密
				//base64转码
				if (StringUtil.isNotEmpty(picRDecStr)) {
					picTBase64Str = Base64.encode(picRDecStr.getBytes("ISO8859-1"));// 解密后的Base64字符串
				}
			}

			
			
		} catch (Exception e1) {
			out.setReturnCode("2999");
			out.setReturnMessage("人像照片解密异常");
			log.error("人像照片解密异常", e1);
			return out;
		}
		
		// 3 调用人像比对服务
		String compareResult = sendPicCheck(picRBase64Str, picTBase64Str,  picRType,  picTType);
		rtnMap.put("compareResult", compareResult);
		out.setReturnCode("0000");
		out.setReturnMessage("人像比对成功");
		out.setBean(rtnMap);

		// 4 调用消息队列，保存调用日志记录到数据库
		try {
			Map<String, Object> logMap = (Map<String, Object>) JsonUtil.convertJson2Object(compareResult, Map.class);
			logMap.put("picRPath", picRPath);
			logMap.put("picTPath", picTPath);
			logMap.put("picRType", picRType);
			logMap.put("picTPath", picTType);
			sendMQ(reqstSrcCode, bizTypeCode, swftno, logMap, out);
		} catch (Exception e) {
			log.error("MQ保存消费信息出错", e);
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
	private EdcCoOutDTO picCompare(PicCompareInDTO inParam) {

		String swftno = inParam.getSwftno();
		String reqstSrcCode = inParam.getReqstSrcCode();
		String bizTypeCode = inParam.getBizTypeCode();

		String picRPath = inParam.getPhotoPath();
		String picTPath = inParam.getPicTPath();
		String picRType = inParam.getPhotoType();
		String picTType = inParam.getPicTType();
		String confidenceScore = inParam.getConfidenceScore();
		
		String crkey = inParam.getCrkey();//人像图片加密秘钥
		String picRStr = null;
		String picTStr = null;
		String picRBase64Str = null;
		String picTBase64Str = null;
		Map<String, String> rtnMap = new HashMap<String, String>();
		EdcCoOutDTO out = new EdcCoOutDTO();
		// 1 获取人像照片
		picRStr = downloadPic(picRPath);
		if (StringUtil.isEmpty(picRStr)) {
			out.setReturnCode("2999");
			out.setReturnMessage("人像照片下载异常");
			log.error("人像照片下载异常");
			return out;
		}		
		try {
			MsDesPlus Ms = new MsDesPlus(crkey);
			String picRDecStr = Ms.decrypt(picRStr);//解密后的图片字符串
			if(StringUtil.isNotEmpty(picRDecStr)){
				log.info("解密后的人像图片字符串"+picRDecStr.length());
				picRBase64Str = Base64.encode(picRDecStr.getBytes("ISO8859-1"));//解密后的Base64字符串
				log.info("解密后的人像图片Base64字符串"+picRBase64Str.length());
			}
		} catch (Exception e1) {
			out.setReturnCode("2999");
			out.setReturnMessage("人像照片解密异常");
			log.error("人像照片解密异常");
			return out;
		}

		// 2 获取国政通头像or芯片头像

		try {
			String picRDecStr = null;
			if (CoConstants.PIC_TYPE.PIC_GZT.equalsIgnoreCase(picTType)) {
				picRDecStr = picRStr;// 国政通图片字符串不用解密
				picTStr = downloadGZTPic(picTPath);
				if (StringUtil.isEmpty(picTStr)) {
					out.setReturnCode("2999");
					out.setReturnMessage("标准照片下载异常");
					return out;
				}
				picTBase64Str = picTStr;
				log.info("base64转码后的国政通图片字符串长度为：" + picTBase64Str.length());
			}else if(CoConstants.PIC_TYPE.PIC_STOIN.equalsIgnoreCase(picTType)){
				MsDesPlus Ms = new MsDesPlus(crkey);
				picTStr = downloadPic(picTPath);
				if (StringUtil.isEmpty(picTStr)) {
					out.setReturnCode("2999");
					out.setReturnMessage("标准照片下载异常");
					return out;
				}
				picRDecStr = Ms.decrypt(picRStr);// 解密后的图片字符串   NFC 芯片头像需要解密
				//base64转码
				if (StringUtil.isNotEmpty(picRDecStr)) {
					log.info("解密后的标准图片字符串"+picRDecStr.length());
					picTBase64Str = Base64.encode(picRDecStr.getBytes("ISO8859-1"));// 解密后的Base64字符串
					log.info("解密后的标准图片Base64字符串" + picTBase64Str.length());
				}
			}
			
		} catch (Exception e1) {
			out.setReturnCode("2999");
			out.setReturnMessage("人像照片解密异常");
			log.error("人像照片解密异常", e1);
			return out;
		}
		// 3 调用人像比对服务
		String compareResult = sendPicCheck(picRBase64Str, picTBase64Str,  picRType,  picTType);
		// 4 人像比对分值判定
		rtnMap.put("compareResult", compareResult);
		if (StringUtil.isNotEmpty(compareResult)) {
			Map<String, Object> logMap = (Map<String, Object>) JsonUtil.convertJson2Object(compareResult, Map.class);
			Map<String, String> compareMap = getCompareResult(logMap, confidenceScore);
			if (compareMap != null) {
				String verifyState = compareMap.get("verifyState");
				if (StringUtil.isNotEmpty(verifyState) && "0".equals(verifyState)) {
					out.setReturnCode("0000");
					out.setReturnMessage("人像比对成功");
				}else{
					out.setReturnCode("2999");
					out.setReturnMessage("人像比对不通过");
				}
				out.setBean(compareMap);
			}
			// 5调用消息队列，保存调用日志记录到数据库
			try {
				// 发送消息队列的内容
				logMap.put("picRPath", picRPath);
				logMap.put("picTPath", picTPath);
				logMap.put("picRType", picRType);
				logMap.put("picTPath", picTType);
				sendMQ(reqstSrcCode, bizTypeCode, swftno, logMap, out);
			} catch (Exception e) {
				log.error("MQ保存消费信息出错", e );
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
	private EdcCoOutDTO picDoubleCompare(PicDoubleCompareInDTO inParam) {
		String swftno = inParam.getSwftno();// 流水号
		String reqstSrcCode = inParam.getReqstSrcCode();// 请求源
		String bizTypeCode = inParam.getBizTypeCode();// 业务类型
		String picRPath = inParam.getPhotoPath();
		String picTPath = inParam.getPicStoinPath();
		String picRType = inParam.getPhotoType();
		String picTGPath = inParam.getGztAvtrPath();
		String picGztAvtrScore = inParam.getGztAvtrScore();
		String picPicStoinScore = inParam.getPicStoinScore();
		String bothCompFlag = inParam.getBothCompFlag();
		String crkey = inParam.getCrkey();//人像图片加密秘钥
		
		String picRBase64Str = null;//base64转码后的人像图片字符串
		String picGZTBase64Str = null;//base64转码后的国政通图片字符串
		String picStoinBase64Str = null;//base64转码后的国政通图片字符串
		
		boolean needCheckTPic = false;// 需要进行芯片头像比对
		boolean checkGZTPicResult = false;// 国政通对比结果
		boolean needBothComp = false;// 为true时，需要两次比对均成功才算比对成功，为false时，任一比对成功则成功
		if (StringUtil.isNotEmpty(bothCompFlag) && "true".equalsIgnoreCase(bothCompFlag)) {
			needBothComp = true;
		}
		Map<String, String> rtnMap = new HashMap<String, String>();
		EdcCoOutDTO out = new EdcCoOutDTO();
		out.setReturnCode("2999");
		out.setReturnMessage("人像比对失败");
		out.setBean(rtnMap);
		// 1 获取人像照片
		String picRStr = downloadPic(picRPath);
		if (StringUtil.isEmpty(picRStr)) {
			out.setReturnCode("2999");
			out.setReturnMessage("人像照片下载异常");
			log.error("人像照片下载异常");
			return out;
		}
		try {
			MsDesPlus Ms = new MsDesPlus(crkey);
			String picRDecStr = Ms.decrypt(picRStr);//解密后的图片字符串
			if(StringUtil.isNotEmpty(picRDecStr)){
				picRBase64Str = Base64.encode(picRDecStr.getBytes("ISO8859-1"));//解密后的Base64字符串
			}
		} catch (Exception e1) {
			out.setReturnCode("2999");
			out.setReturnMessage("人像照片解密异常");
			log.error("人像照片解密异常");
			return out;
		}

		// 2.1 获取国政通头像
		String picTGStr = downloadGZTPic(picTGPath);
		picGZTBase64Str = picTGStr;
		if (StringUtil.isEmpty(picGZTBase64Str)) {
			log.info("国政通图片获取失败");
			out.setReturnCode("2999");
			out.setReturnMessage("国政通图片获取失败");
			checkGZTPicResult = false;// 国政通图片比对失败
		} else {
			// 2.2 调用人像比对服务 比对芯片图片,如果国政通图片不为空 先比对国政通, 国政通比对失败或者比对不一致时,使用nfc芯片图片进行比对 
			String compareResult = sendPicCheck(picRBase64Str, picGZTBase64Str, picRType, CoConstants.PIC_TYPE.PIC_GZT);// 0为同一人  1：不为同一人
			// 2.3 国政通头像比对分值判定
			if (StringUtil.isNotEmpty(compareResult)) {
				Map<String, Object> logMap = (Map<String, Object>) JsonUtil.convertJson2Object(compareResult, Map.class);
				Map<String, String> compareMap = getCompareResult(logMap, picGztAvtrScore);	
				String verifyState = compareMap.get("verifyState");
				if ("0".equals(verifyState)) {
					out.setReturnCode("0000");
					out.setReturnMessage("人像比对成功");
					checkGZTPicResult = true;
				} else {
					out.setReturnCode("2999");
					out.setReturnMessage("国政通图片比对失败");
					checkGZTPicResult = false;
				}
				rtnMap.put("verifyState", compareMap.get("verifyState"));
				rtnMap.put("gztAvtrResultType", compareMap.get("resultType"));
				rtnMap.put("gztAvtrScore", compareMap.get("similarityScore"));
				out.setBean(rtnMap);
				// 2.4 国政通头像对比结果 通过MQ异步保存调用记录
				try {
					logMap.put("picRPath", picRPath);
					logMap.put("picTPath", picTPath);
					logMap.put("picRType", picRType);
					logMap.put("picTPath", CoConstants.PIC_TYPE.PIC_GZT);
					sendMQ(reqstSrcCode, bizTypeCode, swftno, logMap, out);
				} catch (Exception e) {
					log.error("MQ保存信息出错");
				}
			} else {
				out.setReturnCode("2999");
				out.setReturnMessage("调用人像比对服务失败");
				checkGZTPicResult = false;
			}

		}

		if (checkGZTPicResult) {
			if (needBothComp) {
				needCheckTPic = true;// 如果国政通比对成功，且必须两次比对，则进行第二次比对
			} else {
				return out;// 如果国政通比对成功，且不必须两次比对，则返回
			}
		} else {
			if (needBothComp) {
				return out;// 如果国政通比对失败，且必须两次比对，则返回
			} else {
				needCheckTPic = true;// 如果国政通比对失败，且不必须两次比对，则需要进行第二次比对
			}
		}

		// 比对芯片头像
		if (needCheckTPic) {
			// 3.1 获取芯片头像
			String picTStr = downloadPic(picTPath);
			try {
				MsDesPlus Ms = new MsDesPlus(crkey);
				String picStoinDecStr = Ms.decrypt(picTStr);// 解密后的图片字符串
				if (StringUtil.isNotEmpty(picStoinDecStr)) {
					picStoinBase64Str = Base64.encode(picStoinDecStr.getBytes("ISO8859-1"));// 解密后的Base64字符串
				}
			} catch (Exception e1) {
				picStoinBase64Str = null;
				log.error("芯片照片解密异常", e1);

			}
			if (StringUtil.isEmpty(picStoinBase64Str)) {
				rtnMap.put("verifyState", "1");// 是否是同一人 0是 1否
				rtnMap.put("gztAvtrResultType", "-1");
				rtnMap.put("gztAvtrScore", "0");
				out.setBean(rtnMap);
				out.setReturnCode("2999");
				out.setReturnMessage("芯片照片获取异常");
				return out;
			}
			// 3.2 调用人像比对服务 比对芯片头像
			String compareResult = sendPicCheck(picRBase64Str, picStoinBase64Str, picRType, CoConstants.PIC_TYPE.PIC_STOIN);
			if (StringUtil.isNotEmpty(compareResult)) {
				Map<String, Object> logMap = (Map<String, Object>) JsonUtil.convertJson2Object(compareResult,
						Map.class);
				Map<String, String> compareMap = getCompareResult(logMap, picPicStoinScore);
				String verifyState = compareMap.get("verifyState");
				if ("0".equals(verifyState)) {
					out.setReturnCode("0000");
					out.setReturnMessage("人像比对成功");
				} else {
					out.setReturnCode("2999");
					out.setReturnMessage("芯片图片比对失败");
				}
				rtnMap.put("verifyState", compareMap.get("verifyState"));
				rtnMap.put("picStoinResultType", compareMap.get("resultType"));
				rtnMap.put("picStoinScore", compareMap.get("similarityScore"));
				out.setBean(rtnMap);

				// 3.4 芯片头像对比结果 通过MQ异步保存调用记录
				try {
					logMap.put("picRPath", picRPath);
					logMap.put("picTPath", picTPath);
					logMap.put("picRType", picRType);
					logMap.put("picTPath", CoConstants.PIC_TYPE.PIC_STOIN);
					sendMQ(reqstSrcCode, bizTypeCode, swftno, logMap, out);
				} catch (Exception e) {
					log.error("MQ保存消费信息出错");
				}
			}
		}
		return out;
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
			log.error("人像比对服务下载图片异常", e);
		} finally {
			if (null != picInputStream) {
				try {
					picInputStream.close();
				} catch (IOException e1) {
					log.error("人像比对服务关闭图片流异常", e1);
				}
			}
		}
		return picStr;
	}
	
	/**
	 * 下载国政通图片
	 * @param picPath
	 * @return
	 */
	private String downloadGZTPic(String picPath) {
		InputStream picInputStream = null;
		String picGZTBase64Str = null;
		try {
			picGZTBase64Str = fileUpDownUtil.downloadGztPicBase64Str(picPath);
		} catch (Exception e) {
			picGZTBase64Str = null;
			log.error("人像比对服务下载国政通图片异常", e);
		} finally {
			if (null != picInputStream) {
				try {
					picInputStream.close();
				} catch (IOException e1) {
					log.error("人像比对服务关闭图片流异常", e1);
				}
			}
		}
		return picGZTBase64Str;
	}
	
	
	
	
	
	/**
	 * 调用人像照片质检接口 （不进行图片回传）
	 * @param picR 自拍照 头像照片or手持人像 
	 * @param picT 标准照片 国政通or芯片
	 * @param picRType 第一个参数是头像照片还是手持人像照片  t:头像 r:手持人像 
	 * @param picTType  第二个参数是国政通照片还是芯片照片   g:国政通 x:芯片
	 * @return
	 * @throws BusiException
	 */
	private String sendPicCheck(String base64StrR, String base64StrT, String picRType, String picTType) {
		String result = null;
		try {
			result = sendPicCheck(base64StrR, base64StrT, "false", picRType, picTType);
		} catch (GeneralException e) {
			log.error("调用人像照片质检接口异常 ",e);
		}
		return result;
	}
	
	
	/**
	 * 调用人像照片质检接口
	 * @param base64StrR 自拍照 头像照片or手持人像 
	 * @param base64StrT 标准照片 国政通or芯片
	 * @param returnPicRFlag 是否回传头像剪裁图片  true是false否
	 * @param picRType 第一个参数是头像照片还是手持人像照片  t:头像 r:手持人像 
	 * @param picTType  第二个参数是国政通照片还是芯片照片   g:国政通 x:芯片
	 * @return
	 * @throws GeneralException 
	 * @throws BusiException
	 */
	private String sendPicCheck(String base64StrR, String base64StrT, String returnPicRFlag, String picRType, String picTType) throws GeneralException {
		String rt;
		//1封装报文
		Map<String, Object> reqJsonMap = new HashMap<String, Object>();
		reqJsonMap.put("busiCode", "picCheck");
		reqJsonMap.put("version", "1.0");
		Map<String, Object> reqInfoMap = new HashMap<String, Object>();
		reqInfoMap.put("picRIn", base64StrR);
		reqInfoMap.put("isReturnPicR", returnPicRFlag);//是否回传头像剪裁图片  true是false否
		reqInfoMap.put("picTIn", base64StrT);

		if (CoConstants.PIC_TYPE.PHOTO_T.equals(picRType)) {
			// 第一个图片是头像---不传或者0 的时候不进行手持证件照无证件的检测
			reqInfoMap.put("IsCHeckHand", "0");
		} else if (CoConstants.PIC_TYPE.PHOTO_R.equals(picRType)) {
			// 第一个图片是手持证件照---当为1的时候进行手持证件照无证件的检测
			String isHeadCheck = cacheFactory.getJVMString(CacheConsts.JVM.PIC_CHECK_FETCH_ISHEAD);
			if (StringUtil.isEmpty(isHeadCheck)) {
				isHeadCheck = "0";
			}
			reqInfoMap.put("IsCHeckHand", isHeadCheck);
			
			// 只有进行手持证件照判断是否含有证件照才传值
			String perScore = cacheFactory.getJVMString(CacheConsts.JVM.PIC_CHECK_FETCH__PER_SCORE);
			if (StringUtil.isEmpty(perScore)) {
				perScore = "0";
			}
			reqInfoMap.put("HandSorce", perScore);
			log.info("手持证件照有无证件的检测，检测开关为："+isHeadCheck+"检测分数为："+perScore);
		}
		
		
		if (CoConstants.PIC_TYPE.PIC_GZT.equals(picTType)) {
			// 第二个图片是国政通--当不传或者为0的时候需要去网纹
			reqInfoMap.put("IsHasMesh", "0");
		} else if (CoConstants.PIC_TYPE.PIC_STOIN.equals(picTType)) {
			// 第二个图片是芯片--当为1的时候不需要去网纹
			reqInfoMap.put("IsHasMesh", "1");
		}

		// 自拍照是否有人像判定分值
		String portraitSorce = cacheFactory.getJVMString(CacheConsts.JVM.PIC_CHECK_FETCH__PORTRAIT_SCORE);
		if (StringUtil.isEmpty(portraitSorce)) {
			portraitSorce = "0";
		}
		reqInfoMap.put("PortraitSorce", portraitSorce);
		//是否判断picZIn为HACK攻击照片 当不传或者0 的时候不进行防HACK的检测,当为1的时候进行防HACK的检测
		
		String chekHackPicFlag = cacheFactory.getJVMString(CacheConsts.JVM.PIC_CHECK_FETCH_HACK_SWITCH);
		if (StringUtil.isEmpty(chekHackPicFlag)) {
			chekHackPicFlag = "0";
		}
		reqInfoMap.put("isChekHackPic", chekHackPicFlag);
		//防HACK的检测的判定分值
		String hackSorce = cacheFactory.getJVMString(CacheConsts.JVM.PIC_CHECK_FETCH_HACK_SCORE);
		if (StringUtil.isEmpty(hackSorce)) {
			hackSorce = "0";
		}
		reqInfoMap.put("HackSorce", chekHackPicFlag);	
		
		
		reqJsonMap.put("reqInfo", reqInfoMap);
		String reqJson = JsonUtil.convertObject2Json(reqJsonMap);	
		//环境变量配置是否调用人像比对 地址
//		String svUrl = env.getProperty("cfg.picCheck.url");	
		String svUrl = cacheFactory.getJVMString(CacheConsts.JVM.PIC_CHECK_FETCH_URL);
		log.info("*********************"+svUrl);
		String timeOutConf = cacheFactory.getJVMString(CacheConsts.JVM.PIC_CHECK_FETCH__PORTRAIT_SCORE);
		if (StringUtil.isEmpty(timeOutConf)) {
			timeOutConf = "12000";
		}
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
	 * @param rtMap 人像对比服务 返回的map
	 * @param busiType  业务类型   provcode $ 11
	 * @param custCertNo
	 * @return
	 * @throws BusiException
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> getCompareResult(Map<String, Object> rtMap, String rangeStr) {
		Map<String, String> returnMap = new HashMap<String, String>();
		// 0成功1自拍照无人像 2 自拍照人像模糊 3标准照片无人像 4标准照片人像模糊 5 违规处理自拍照 6非手持证件照 -1其他情况
		String resultType = (String) rtMap.get("resultType");
		Map<String, String> returnInfo = ((Map<String, String>) rtMap.get("returnInfo"));
		String verifyState; // 是否是同一人 0是 1否
		returnMap.put("resultType", resultType);

		if (returnInfo != null) {
			String cHeckHandResult = returnInfo.get("CHeckHandResult");// 自拍照手持证件判断比分
			String checkPortraitResult = returnInfo.get("CheckPortraitResult");// 自拍照人像判断比分
			String chekHackPic = returnInfo.get("ChekHackPic");// picZIn判断为Hack照片的比分
			String portrtOther3Score=cHeckHandResult+"|"+checkPortraitResult+"|"+chekHackPic;
			if ("0".equals(resultType)) {
				// 显示对比分数
				String score = returnInfo.get("similarity");
				if (StringUtil.isEmpty(rangeStr)) {
					rangeStr = cacheFactory.getJVMString(CacheConsts.JVM.PIC_CHECK_DEFAULT_VALUE);
				}
				verifyState = checkInRange(rangeStr, score, "0", "1");// 是否是同一人// 0是 1否
				returnMap.put("similarityScore", score);
				returnMap.put("verifyState", verifyState);
				returnMap.put("portrtOther3Score",portrtOther3Score);
			}	
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
			if (StringUtil.isNotEmpty(str)) {
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
	public String saveCompareInfo(String requestSource, String busiType, String transactionId, Map<String,Object> compareResult, EdcCoOutDTO out) throws JsonFormatException{

		CoPicCompareInfoDO infoBean = new CoPicCompareInfoDO();
		infoBean.setCrtTime(new Timestamp(new Date().getTime()));
		infoBean.setReqstSrcCode(requestSource);
		infoBean.setBizTypeCode(busiType);
		infoBean.setSwftno(transactionId);
		
		
		String returnCode = out.getReturnCode();
		String returnMessage = out.getReturnMessage();
		infoBean.setRspCode(returnCode);
		infoBean.setRspInfoCntt(returnMessage);

		infoBean.setPhotoPath((String) compareResult.get("picRPath"));
		infoBean.setPicTPath((String) compareResult.get("picTPath"));
		infoBean.setPhotoType((String) compareResult.get("picRType"));
		infoBean.setPicTType((String) compareResult.get("picTPath"));

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
				if (StringUtil.isNotEmpty(returnInfo.get("picROut"))) {
					returnInfo.put("picROut", "图片已替换");
				}
				infoBean.setCmprScore(score);
				String compareStr = returnInfo.toString();
				if (compareStr.length() > 2000) {
					compareStr = compareStr.substring(0, 2000);
				}
				
				String cHeckHandResult = returnInfo.get("CHeckHandResult");// 自拍照手持证件判断比分
				String checkPortraitResult = returnInfo.get("CheckPortraitResult");// 自拍照人像判断比分
				String chekHackPic = returnInfo.get("ChekHackPic");// picZIn判断为Hack照片的比分
				String otherScore=cHeckHandResult+"|"+checkPortraitResult+"|"+chekHackPic;
				infoBean.setOtherScore(otherScore);
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
	private void sendMQ(String requestSource, String busiType, String transactionId, Map<String, Object> compareResult,
			EdcCoOutDTO out) throws MsgException, JsonFormatException {
		String Msg = saveCompareInfo(requestSource, busiType, transactionId, compareResult, out);
		MsgProducerClient.getRocketMQProducer().send(MqConstants.MQ_TOPIC.PIC_COMPARE, Msg);
	}
}
