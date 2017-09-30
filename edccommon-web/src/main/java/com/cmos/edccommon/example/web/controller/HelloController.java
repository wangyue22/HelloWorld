package com.cmos.edccommon.example.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cmos.edccommon.iservice.IExampleService;
import org.springframework.web.bind.annotation.*;

/**
 * Created by alen on 17-2-23.
 */
@RestController
public class HelloController {

    @Reference(group = "edccommon")
    private IExampleService exampleService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(@RequestParam String name) {
        return exampleService.sayHello("Alen");
    }

}
