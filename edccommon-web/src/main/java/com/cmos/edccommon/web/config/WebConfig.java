package com.cmos.edccommon.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import com.cmos.common.annotation.EnableFileUpload;
import com.cmos.common.annotation.EnableResponseWrapper;
import com.cmos.common.exception.autoconfig.EnableGeneralException;
import com.cmos.common.web.config.DefaultWebMvcConfig;
import com.cmos.edccommon.web.intercepter.PrivateParamInterceptor;

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
    

}
