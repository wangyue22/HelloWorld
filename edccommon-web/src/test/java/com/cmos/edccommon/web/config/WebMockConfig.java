package com.cmos.edccommon.web.config;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.cmos.cache.config.CachingConfig;
import com.cmos.common.web.http.JSONObjectHttpMessageConverter;
import com.cmos.common.web.upload.config.StorageConfig;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.cmos.edccommon.web","com.cmos.cache"})
@Import({StorageConfig.class,CachingConfig.class})
public class WebMockConfig extends WebMvcConfigurerAdapter {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(new JSONObjectHttpMessageConverter());
    }
}
