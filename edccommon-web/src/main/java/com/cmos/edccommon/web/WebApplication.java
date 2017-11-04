package com.cmos.edccommon.web;

import com.cmos.common.exception.autoconfig.EnableGeneralException;
import com.cmos.common.web.config.DefaultWebMvcConfig;
import com.cmos.core.logger.interceptor.EnableLog4xComponent;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import com.cmos.common.spring.ApplicationStarter;

/**
 * Created by alen on 17-2-16.
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableAutoConfiguration
@EnableGeneralException
@EnableLog4xComponent
@ComponentScan(basePackages = {"com.cmos.edccommon","com.cmos.cache"})
@Import(DefaultWebMvcConfig.class)
public class WebApplication {

	public static void main(String[] args) {
		ApplicationStarter.startWebApplication(WebApplication.class, args);
	}

}



