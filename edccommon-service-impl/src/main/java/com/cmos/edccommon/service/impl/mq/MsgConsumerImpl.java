package com.cmos.edccommon.service.impl.mq;

import com.cmos.msg.common.IConsumerProcessor;
import com.cmos.msg.common.MsgFMessage;
import com.cmos.msg.exception.MsgException;

public class MsgConsumerImpl implements IConsumerProcessor {
	@Override
	public void process(MsgFMessage msg) throws MsgException {
		System.out.println("消费到的消息内容为:"+msg);
		/**在此把短信内容发送给用户*/
		
	}
}

