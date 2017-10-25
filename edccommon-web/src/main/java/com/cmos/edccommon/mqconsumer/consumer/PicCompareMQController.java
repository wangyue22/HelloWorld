package com.cmos.edccommon.mqconsumer.consumer;


import com.alibaba.dubbo.config.annotation.Reference;
import com.cmos.consumer.client.MsgConsumerClient;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.iservice.piccompare.IPicCompareSV;
import com.cmos.msg.exception.MsgException;


/**
 * mq 消费服务暂用方法
 * @author xdx
 *
 */

public class PicCompareMQController {
	
	@Reference(group = "edcco")
	private IPicCompareSV msgConsumerSV;
	
	private Logger log = LoggerFactory.getActionLog(PicCompareMQController.class);
	

	public void picCompareMQ() throws MsgException {
		//对指定主题进行消费
		MsgConsumerClient.getRocketMQConsumer().subscribe("EDCCO_PICCOMPARE", msgConsumerSV);
	}
}
