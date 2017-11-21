package com.cmos.edccommon.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cmos.cache.service.ICacheService;
import com.cmos.common.dubbo.DubboRegistryInitializer;
import com.cmos.common.spring.DefaultApplicationContextInitializer;
import com.cmos.common.test.MockAppContextIntializer;
import com.cmos.edccommon.web.config.WebMockConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebMockConfig.class, initializers = {DubboRegistryInitializer.class,
    DefaultApplicationContextInitializer.class, MockAppContextIntializer.class})
/**
 * 单元测试 基类
 * @author xdx
 */
public class BaseUnitTest {
    @Autowired
    protected WebApplicationContext context;
    @Autowired
    protected ICacheService cacheService;
    protected MockMvc mockMvc;
    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
    @Test
	public void test() {
	}
}
