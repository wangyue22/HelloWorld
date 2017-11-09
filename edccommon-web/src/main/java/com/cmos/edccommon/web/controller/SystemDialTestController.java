package com.cmos.edccommon.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.iservice.ISystemDialTestSV;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 系统拨测测试
 * @author cuishuai
 *
 */
@RestController
@Api(description="系统拨测测试")
public class SystemDialTestController {
    
    private static final Logger logger = LoggerFactory.getLogger(SystemDialTestController.class);
    
    @Reference(group = "edcco")
    private ISystemDialTestSV   systemDialTestsv;
    
    @ApiOperation(value="系统拨测测试")
    @RequestMapping(value = "/systemDialTest", method = RequestMethod.POST)
    public void systemDialTest(){
        Date state = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("进入系统拨测测试web层，当前时间是：" + formatter.format(state));
        systemDialTestsv.systemDialTest();
        Date end = new Date();
        logger.info("拨测测试结束core层，重新回到web层，当前时间是：" + formatter.format(end));
        long costTime = end.getTime() - state.getTime();
        logger.info("调用总花费时间是："+ costTime + "毫秒");
    }
}
