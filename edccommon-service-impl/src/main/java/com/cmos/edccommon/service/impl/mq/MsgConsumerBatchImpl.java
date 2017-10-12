package com.cmos.edccommon.service.impl.mq;

import java.util.List;

import com.cmos.msg.common.IConsumerBatchProcessor;
import com.cmos.msg.common.IConsumerProcessor;
import com.cmos.msg.common.MsgFMessage;
import com.cmos.msg.exception.MsgException;

public class MsgConsumerBatchImpl implements IConsumerBatchProcessor{
	@Override
	public void process(List< MsgFMessage> msgs) throws MsgException {
		System.out.println(msgs.size());
		for (MsgFMessage msg : msgs) {
			System.out.println("我是消息体："+msg);
		}
	}
}

