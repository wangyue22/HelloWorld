package com.cmos.edccommon.example.web.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by alen on 17-2-23.
 */
@RestController
@RequestMapping(path = "/page")
public class PageController {


    @RequestMapping(path = "/add")
    public String add() {
        return "";
    }


}
