package com.cmos.edccommon;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import com.cmos.common.spring.ApplicationStarter;

/**
 * Created by alen on 17-2-16.
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.cmos.edccommon"})
public class  ConsumerApplication {

	public static void main(String[] args) throws Exception {
		ApplicationStarter.startApplication(ConsumerApplication.class, args);
	}

}