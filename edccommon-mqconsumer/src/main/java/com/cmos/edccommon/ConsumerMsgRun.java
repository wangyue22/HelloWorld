package com.cmos.edccommon;

import com.cmos.consumer.client.MsgConsumerClient;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;

import com.cmos.edccommon.mqconsumer.service.PicCompareConsumer;
import com.cmos.msg.exception.MsgException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



/**
 * MQ消息消费类，在项目启动时直接订阅
 * xdx
 */
@Component
public class ConsumerMsgRun implements CommandLineRunner {
	@Autowired
	private PicCompareConsumer picCompareConsumer;

	private Logger log = LoggerFactory.getActionLog(ConsumerMsgRun.class);
	@Override
	public void run(String... args) throws Exception {
		
		try {
			log.info("*************ConsumerMsgRun is running********************");
			MsgConsumerClient.getRocketMQConsumer().subscribe("EDCCO_PICCOMPARE", picCompareConsumer);
//			MsgConsumerClient.getRocketMQConsumer().subscribe("EDCCO_FACELIVE", msgFaceLiveSV);
		} catch (MsgException e) {
			log.error("ConsumerMsgRun exception:", e);
		}	
	}
}



