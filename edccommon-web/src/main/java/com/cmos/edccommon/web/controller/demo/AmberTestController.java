package com.cmos.edccommon.web.controller.demo;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by alen on 16-12-20.
 */
@RestController
@RequestMapping(value = "/amber", produces = { MediaType.APPLICATION_JSON_VALUE })
public class AmberTestController {

    private static final Logger LOGGER = Logger.getLogger(AmberTestController.class);

    @Autowired
    private Environment environment;

    @RequestMapping(value = "/dump_env", method = RequestMethod.GET)
    public String dumpEnv() {
        String test = environment.getProperty("cfg.db.url");
        System.out.println("Amber Config: " + test);
        return "Check env update: " + environment.getProperty("cfg.db.url");
    }

}
