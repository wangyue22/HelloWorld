package com.cmos.edccommon.service.impl.mq;

import com.cmos.msg.common.IExceptionProcessor;

public class ConsumerProcessorImpl implements IExceptionProcessor{
	@Override
	public void processException(String msg, String topic, Exception e) {
		System.out.println("消息体为"+msg);
		System.out.println("主题为:"+topic);
		System.out.println("异常描述为："+e);
	}
}

