package com.cmos.edccommon.mqconsumer;

import com.cmos.common.exception.autoconfig.EnableGeneralException;
import com.cmos.common.spring.ApplicationStarter;
import com.cmos.edccommon.mqconsumer.consumer.PicCompareMQController;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by system on 17-10-25.
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableAutoConfiguration
@EnableGeneralException
@ComponentScan(basePackages = {"com.cmos.edccommon","com.cmos.cache"})


public class MqConsumerApplication {

	public static void main(String[] args) throws Exception{
		ApplicationStarter.startWebApplication(MqConsumerApplication.class, args);
		//依次持久化mq消费
		PicCompareMQController test = new PicCompareMQController();
		test.picCompareMQ();
	}

}



