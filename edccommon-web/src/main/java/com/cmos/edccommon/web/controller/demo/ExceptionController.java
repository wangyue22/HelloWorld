package com.cmos.edccommon.web.controller.demo;

import com.cmos.common.exception.GeneralException;
import com.cmos.edccommon.web.pojo.UserInfo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by alen on 16-12-6.
 */
@RestController
@RequestMapping(path = "/exception", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ExceptionController {

    @RequestMapping(path = "/throw1")
    public UserInfo throw1(@RequestParam(name = "name", required = false) String name) throws GeneralException {
        if (null == name)
            throw new GeneralException("CSF404");
        return new UserInfo(name, 32);
    }

}
