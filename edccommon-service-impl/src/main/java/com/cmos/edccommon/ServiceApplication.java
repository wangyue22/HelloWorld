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

@Configuration
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableAutoConfiguration
@EnableDataSource
@ComponentScan(basePackages = {"com.cmos.edccommon"})
public class ServiceApplication {

	public static void main(String[] args) throws Exception
	{
		IPasswordCrypto crypto = new DataSourcePasswordCrypto();
    	System.out.println(   crypto.encrypt("A$^5S%sc0U"));
		ApplicationStarter.startApplication(ServiceApplication.class, args);
	}
}
