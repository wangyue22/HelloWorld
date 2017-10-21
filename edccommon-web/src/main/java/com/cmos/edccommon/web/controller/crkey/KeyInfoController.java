package com.cmos.edccommon.web.controller.crkey;
import com.alibaba.dubbo.config.annotation.Reference;
import com.cmos.common.exception.GeneralException;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.crkey.CoKeyDO;
import com.cmos.edccommon.beans.crkey.KeyInfoDTO;
import com.cmos.edccommon.iservice.crkey.IKeyInfoSV;
import com.cmos.edccommon.utils.CoConstants;
import com.github.pagehelper.StringUtil;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取秘钥
 * 
 * @author xdx
 *
 */
@RestController
@RequestMapping(value = "/co")
public class KeyInfoController {
    Logger log=LoggerFactory.getActionLog(KeyInfoController.class);
	@Reference(group = "edcco")
	private IKeyInfoSV keyInfoSV;
	
	/**
	 * http://localhost:18080/co/getDesKey?reqstSrcCode=371
	 * 获取默认的DES加密秘钥
	 * @param reqstSrcCode
	 * @return
	 * @throws GeneralException 
	 */
		@RequestMapping(value = "/getDesKey", method = RequestMethod.POST)
		public CoKeyDO getDesKey(@RequestParam String reqstSrcCode ) throws GeneralException{
			CoKeyDO outParam = new CoKeyDO();
			if(StringUtil.isEmpty(reqstSrcCode)){
				throw new GeneralException("2999","参数异常");
			}
			KeyInfoDTO param= new KeyInfoDTO();
			param.setReqstSrcCode(reqstSrcCode);
			param.setCrkeyTypeCd(CoConstants.CR_KEY_TYPE.DES);
			param.setBizTypeCd(CoConstants.BIZ_TYPE.DEFAULT);
			try{
				outParam=keyInfoSV.getKey(param);
			}catch(Exception e){
				log.error("getDesKey方法异常", e);
				throw new GeneralException("9999","系统异常",e);
			}
			return outParam;
		}
		
		
	/**
	 * http://localhost:18080/co/getKey?reqstSrcCode=371
	 * 获取加密秘钥 需要区分业务类型和秘钥类型时
	 * @param inParam
	 * @return
	 * @throws GeneralException 
	 */
		@RequestMapping(value = "/getKey", method = RequestMethod.POST)
		public CoKeyDO getKey(@RequestBody KeyInfoDTO inParam ) throws GeneralException{
			CoKeyDO outParam = new CoKeyDO();
	
			if(inParam==null||StringUtil.isEmpty(inParam.getReqstSrcCode())){
				throw new GeneralException("2999","参数异常");
			}
			try{
				outParam=keyInfoSV.getKey(inParam);
			}catch(Exception e){
				log.error("getDesKey方法异常", e);
				throw new GeneralException("9999","系统异常",e);
			}

			return outParam;
		}
		
		
}
