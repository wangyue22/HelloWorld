package com.cmos.edccommon.mqconsumer.service;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.piccompare.CoPicCompareInfoDO;
import com.cmos.edccommon.iservice.piccompare.IPicCompareSV;
import com.cmos.edccommon.utils.JsonUtil;
import com.cmos.msg.common.IConsumerProcessor;
import com.cmos.msg.common.MsgFMessage;
import com.cmos.msg.exception.MsgException;

/**
 * 人像比对
 *
 * @author xdx
 * @since 1.0
 */
@Component
public class PicCompareConsumer implements IConsumerProcessor {
	
	@Reference(group = "edcco")
	private IPicCompareSV msgPicComSV;
	
	
	private Logger log=LoggerFactory.getActionLog(PicCompareConsumer.class);
	@Override
	public void process(MsgFMessage msg) throws MsgException {
		String msgContent = (String) msg.getMsg();
		log.info("**************************MQ消费进程开始执行"+msgContent);
		CoPicCompareInfoDO logBean = (CoPicCompareInfoDO) JsonUtil.convertJson2Object(msgContent,CoPicCompareInfoDO.class);
		msgPicComSV.savePicCompareLog(logBean);
	}

}
