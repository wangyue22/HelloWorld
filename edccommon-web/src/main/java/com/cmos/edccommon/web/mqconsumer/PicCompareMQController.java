package com.cmos.edccommon.web.mqconsumer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping(value = "/co")
public class PicCompareMQController {
	
	@Reference(group = "edcco")
	private IPicCompareSV msgConsumerSV;
	
	private Logger log = LoggerFactory.getActionLog(PicCompareMQController.class);
	
	@RequestMapping(value = "/picCompareMQ", method = RequestMethod.POST)
	public void picCompareMQ(@RequestParam String inParam) throws MsgException {
		log.info(inParam);
		MsgConsumerClient.getRocketMQConsumer().subscribe("EDCCO_PICCOMPARE", msgConsumerSV);
	}
}
