package com.cmos.edccommon.web.mqconsumer;

import com.alibaba.dubbo.config.annotation.Reference;

import com.cmos.consumer.client.MsgConsumerClient;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.iservice.facelive.IFaceLiveSV;
import com.cmos.edccommon.iservice.piccompare.IPicCompareSV;
import com.cmos.msg.exception.MsgException;


import org.springframework.boot.CommandLineRunner;

import org.springframework.stereotype.Component;


/**
 * MQ消息消费类，在项目启动时直接订阅
 * xdx
 */
@Component
public class ConsumerMsgRun implements CommandLineRunner{
	@Reference(group = "edcco")
	private IPicCompareSV msgPicComSV;
	
	@Reference(group = "edcco")
	private IFaceLiveSV msgFaceLiveSV;

	
	private Logger log=LoggerFactory.getActionLog(ConsumerMsgRun.class);
	@Override
	public void run(String... args) throws Exception {
		
		try {
			log.info("*************ConsumerMsgRun is running********************");
			MsgConsumerClient.getRocketMQConsumer().subscribe("EDCCO_PICCOMPARE", msgPicComSV);
			MsgConsumerClient.getRocketMQConsumer().subscribe("EDCCO_FACELIVE", msgFaceLiveSV);
		} catch (MsgException e) {
			log.error("ConsumerMsgRun exception:", e);
		}	
	}
}



