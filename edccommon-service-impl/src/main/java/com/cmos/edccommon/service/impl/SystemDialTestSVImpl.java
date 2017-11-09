package com.cmos.edccommon.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.iservice.ISystemDialTestSV;

/**
 * 系统拨测测试
 * @author cuishuai
 *
 */
@Service(group = "edcco")
public class SystemDialTestSVImpl implements ISystemDialTestSV{
    
    private static final Logger logger = LoggerFactory.getLogger(SystemDialTestSVImpl.class);
   
    @Override
    public void systemDialTest() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("拨测测试进入core层，当前时间是："+ formatter.format(new Date()));
    }

}
