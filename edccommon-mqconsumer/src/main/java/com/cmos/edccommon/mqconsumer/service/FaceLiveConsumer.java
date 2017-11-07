package com.cmos.edccommon.mqconsumer.service;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.facelive.CoFaceLiveInfoDO;
import com.cmos.edccommon.beans.piccompare.CoPicCompareInfoDO;
import com.cmos.edccommon.iservice.facelive.IFaceLiveSV;
import com.cmos.edccommon.utils.JsonUtil;
import com.cmos.msg.common.IConsumerProcessor;
import com.cmos.msg.common.MsgFMessage;
import com.cmos.msg.exception.MsgException;

/**
 *  静默活体消费进程
 *
 * @author xdx
 * @since 1.0
 */
@Component
public class FaceLiveConsumer implements IConsumerProcessor {
	
	@Reference(group = "edcco")
	private IFaceLiveSV msgPicComSV;
	
	
	private Logger log=LoggerFactory.getActionLog(FaceLiveConsumer.class);
	@Override
	public void process(MsgFMessage msg) throws MsgException {
		String msgContent = (String) msg.getMsg();
		log.info("**************************MQ消费进程开始执行"+msgContent);
		CoFaceLiveInfoDO logBean = (CoFaceLiveInfoDO) JsonUtil.convertJson2Object(msgContent,CoPicCompareInfoDO.class);
		msgPicComSV.saveFaceLiveLog(logBean);
	}

}
