package com.cmos.edccommon.web.config;

import com.cmos.common.annotation.EnableFileUpload;
import com.cmos.common.annotation.EnableResponseWrapper;
import com.cmos.common.exception.autoconfig.EnableGeneralException;
import com.cmos.common.web.config.DefaultWebMvcConfig;
import com.cmos.common.web.http.ServletContainerCustomizer;
import com.cmos.edccommon.web.controller.serviceswitch.ServiceSwitchController;
import com.cmos.edccommon.web.intercepter.PrivateParamInterceptor;


import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * Created by zhuzh on 2016/11/18.
 */

@Configuration
@EnableWebMvc
@EnableFileUpload
@ComponentScan(
        basePackages = {
                "com.cmos.edccommon.web",
                "com.cmos.edccommon.web.controller.**"
        },
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)})
@EnableGeneralException
@EnableResponseWrapper
public class WebConfig extends DefaultWebMvcConfig {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PrivateParamInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/login/**")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/v2/**")
                .excludePathPatterns("/error/**");
    }
    
    @Bean
    public ServletRegistrationBean myServletRegistrationBean() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new ServiceSwitchController());
//        registration.addInitParameter("name", "hello world");
        registration.addUrlMappings("/edccommon-web/src/main/java/com/cmos/edccommon/web/controller/ServiceSwitchController");
        registration.setLoadOnStartup(1);
        return registration;
    }

}
