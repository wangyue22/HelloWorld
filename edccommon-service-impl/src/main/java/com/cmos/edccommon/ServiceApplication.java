package com.cmos.edccommon;

import com.cmos.common.annotation.EnableDataSource;
import com.cmos.common.datasource.DataSourcePasswordCrypto;
import com.cmos.common.security.IPasswordCrypto;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import com.cmos.common.spring.ApplicationStarter;
import com.cmos.core.logger.interceptor.EnableLog4xComponent;

@Configuration
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableAutoConfiguration
@EnableDataSource
@EnableLog4xComponent
@ComponentScan(basePackages = {"com.cmos.edccommon"})
public class ServiceApplication {

	public static void main(String[] args) throws Exception
	{
		ApplicationStarter.startApplication(ServiceApplication.class, args);
	}
}
