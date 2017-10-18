package com.cmos.edccommon.web.mqconsumer;

import org.junit.Test;

import com.cmos.consumer.client.MsgConsumerClient;
import com.cmos.msg.exception.MsgException;
import com.cmos.producer.client.MsgProducerClient;

public class MsgSendController {
	
	public static void main(String[] args) throws MsgException {
		
		boolean result = MsgProducerClient.getRocketMQProducer().send("EDCTopic","您已成功充值100元");
		System.out.println(result);
	}
	
	@Test
	public void main1() throws MsgException {
		MsgProducerClient.getRocketMQProducer().send("EDCTopic", "您已成功充值100111元");
	}
}
