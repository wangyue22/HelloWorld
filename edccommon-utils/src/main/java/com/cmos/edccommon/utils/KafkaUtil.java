package com.cmos.edccommon.utils;

import java.util.Map;

import com.alibaba.dubbo.common.json.JSON;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;

/**
 *<p>Title:KaFuKaUtil </p>
 *<p>Description: 日志保存工具类</p>
 *@author dell
 *@date 下午2:35:04
 */
public class KafkaUtil {
    private static final Logger logger = LoggerFactory.getActionLog(KafkaUtil.class);

    /**
     *
     * @Title: transToVertica
     * @Description: 发送日志信息到vertica
     * @param Object object
     * @param String topic
     * @return void    返回类型
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static void transToVertica(String object, String topic) {
        try {
            // 发送日志信息到vertica
            String logJson = JSON.json(object);
            Map<String, Object> logMap = JSON.parse(logJson, Map.class);
            logger.commonLog(topic, logMap);
        } catch (Exception e) {
            logger.error("========发送日志信息发生异常========", e);
        }
    }
}
