package com.cmos.edccommon.web.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.cmos.cache.service.ICacheService;
import com.cmos.common.exception.GeneralException;
import com.cmos.common.spring.AppContext;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.utils.KeysUtil;

@Configuration
public class BasicUtil {
    private static final Logger logger = LoggerFactory.getLogger(BasicUtil.class);
    private static BasicUtil basicUtil = null;
    @Autowired
    private ICacheService cacheService;

    //获取实例对象
    public static BasicUtil getKeysInstant() {
        if (basicUtil == null) {
            basicUtil = AppContext.getBean(BasicUtil.class);
        }
        return basicUtil;
    }

    /**
     * 主键生成策略
     *
     * @param tableName 需要获取主键的表名
     */
    public String getSequence(String tableName) throws GeneralException {
        String id = null;
        try {
            id = KeysUtil.getSequence(cacheService,tableName);
        } catch (Exception e) {
            logger.error("生成主键异常",e);
            throw new GeneralException(e.getMessage());
        }
        return id;
    }
}