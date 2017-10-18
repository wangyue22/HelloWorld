package com.cmos.edccommon.web.mqconsumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cmos.edccommon.iservice.piccompare.IPicCompareSV;
import com.cmos.msg.exception.MsgException;

public class ConsumerMQ {

	public ConsumerMQ() {
	
	}
	@Reference(group = "edcco")
	private IPicCompareSV picCompareSV;
	
	public void consumerMQ() throws MsgException {
	}
	

}
