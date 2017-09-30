package com.cmos.edccommon.example;

import com.cmos.common.annotation.EnableDataSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import com.cmos.common.spring.ApplicationStarter;

@Configuration
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableAutoConfiguration
@EnableDataSource
@ComponentScan(basePackages = {"com.cmos.edccommon.example"})
public class ServiceApplication {

	public static void main(String[] args) throws Exception
	{
		ApplicationStarter.startApplication(ServiceApplication.class, args);
	}

}
