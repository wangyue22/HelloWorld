package com.cmos.edccommon;

import com.cmos.consumer.client.MsgConsumerClient;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.mqconsumer.service.FaceLiveConsumer;
import com.cmos.edccommon.mqconsumer.service.PicCompareConsumer;
import com.cmos.edccommon.utils.consts.MqConstants;
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
	@Autowired
	private FaceLiveConsumer faceLiveConsumer;
	
	private Logger log = LoggerFactory.getActionLog(ConsumerMsgRun.class);
	@Override
	public void run(String... args) throws Exception {
		
		try {
			log.info("*************ConsumerMsgRun is running********************");
			MsgConsumerClient.getRocketMQConsumer().subscribe(MqConstants.MQ_TOPIC.PIC_COMPARE, picCompareConsumer);
			MsgConsumerClient.getRocketMQConsumer().subscribe(MqConstants.MQ_TOPIC.FACE_LIVE, faceLiveConsumer);
		} catch (MsgException e) {
			log.error("ConsumerMsgRun exception:", e);
		}	
	}
}



