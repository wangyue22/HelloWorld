package com.cmos.edccommon.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.edccommon.example.service.ISimpleService;
import com.cmos.edccommon.iservice.IExampleService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by alen on 17-2-16.
 */
@Service(group = "edccommon")
public class ExampleService implements IExampleService {

    @Autowired
    private ISimpleService simpleService;

    public String sayHello(String name) {
        return simpleService.talk(name);
    }

}
