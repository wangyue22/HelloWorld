package com.cmos.edccommon.web.mqconsumer;

import com.cmos.consumer.client.MsgConsumerClient;
import com.cmos.msg.exception.MsgException;

public class MsgConsumerController {
	
	public static void main(String[] args) throws MsgException {
//		MsgProducerClient.getRocketMQProducer().send("SMSNotice","您已成功充值100元");
		MsgConsumerClient.getRocketMQConsumer().subscribe("EDCTopic",new MsgConsumerImpl());
	}
}
