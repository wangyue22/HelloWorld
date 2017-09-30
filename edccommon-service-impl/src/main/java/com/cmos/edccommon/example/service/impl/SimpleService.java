package com.cmos.edccommon.example.service.impl;

/**
 * Created by alen on 17-2-16.
 */
import com.cmos.edccommon.example.service.ISimpleService;
import org.springframework.stereotype.Component;

@Component
public class SimpleService implements ISimpleService {

    public String talk(String name) {
        System.out.println("Talk with " + name);
        return "Welcome, " + name;
    }

}
