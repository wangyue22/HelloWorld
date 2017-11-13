package com.cmos.edccommon.utils;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.validation.ValidationException;

import org.springframework.util.StringUtils;

import com.cmos.cache.service.ICacheService;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;

/**
 * Created by Administrator on 2017/7/18.
 */
public class KeysUtil {
    private static final Logger logger = LoggerFactory.getLogger(KeysUtil.class);

    /**
     * 主键生成策略
     *
     * @param tableName 需要获取主键的表名
     */
    public static String getSequence(ICacheService cacheService,String tableName) throws ValidationException {
        if (StringUtil.isEmpty(tableName)) {
            throw new ValidationException("获取主键时前缀不能为空!建议传入表名");
        }
        String redisKey = "REDIS_TBL_" + tableName;
        String id = null;
        try {
            logger.info("开始获取主键 " + redisKey);
            id = DateUtil.date2String(new Date(), DateUtil.DATE_PATTERN.YYYYMMDDHHMMSS) + ""
                    + getSequenceOnlyIncr(redisKey, cacheService, 5);
            logger.info("获取主键成功id=" + id);
        } catch (Exception e) {
            id = UUID.randomUUID().toString();
            logger.error("NOT ERROR! 主键获取成功，key=" + redisKey, e);
        }
        return id;
    }

    /**
     * 根据redis键值获取序列唯一标识
     * @param key 缓存键值
     * @param length 给定尾数长度
     * <p><span style="color:red;">警告:自定长度必须大于六位!</span></p>
     * @return 序列唯一标识
     */
    public static String getSequenceOnlyIncr(String key, ICacheService cacheService, int length) throws Exception {
        int baseLength = 4;
        StringBuffer baseNums = new StringBuffer();
        String uniqueNums = null;
        if (length <= baseLength) {
            throw new Exception("序列长度不满足唯一标识生成需求！");
        }
        if (StringUtils.isEmpty(key)) {
            throw new Exception("redis键值不能为空！");
        }
        String id = null;
        for (int i = 0; i < length; i++) {
            baseNums.insert(0, "9");
        }
        try {
            logger.info("开始获取主键 " + "key=" + key);
            Long redisValue = cacheService.incr(key);
            // 如果缓存Incr的键值大于基准数值则进行截取
            if (redisValue - Long.valueOf(baseNums.toString()) > 0) {
                logger.info("缓存Incr的键值大于基准数值 " + "redisValue=" + redisValue);
                String transValue = splits(redisValue.toString(), length);
                id = transValue;
            } else {
                uniqueNums = addPosition(redisValue.toString(), length);
                id = uniqueNums;
            }
        } catch (Exception e) {
            logger.info("使用redis获取主键失败，开始使用:随机字符串" + "key=" + key, e);
            id = getRandomStringByLength(length);
        }
        return id;
    }

    private static String splits(String temp, int length) {
        String value = temp.substring(temp.length() - length);
        return value;
    }

    private static String addPosition(String temp, int length) {
        StringBuffer value = new StringBuffer();
        for (int i = 0; i < length - temp.length(); i++) {
            value.insert(0, "0");
        }
        value.append(temp.toString());

        return value.toString();
    }

    /**
     * 获取一定长度的随机字符串
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    private static String getRandomStringByLength(int length) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    
    
	public static void main(String[] args) {
		StringBuffer str = new StringBuffer("wlf");
		System.out.println(str); // 调用insert方法前结果：wlf
		str.insert(0, "ang");
		System.out.println(str); // 调用insert方法后结果：wanglf
		System.out.println("---------------------------");
		System.out.println(str); // wanglfhaha0

	}
}
