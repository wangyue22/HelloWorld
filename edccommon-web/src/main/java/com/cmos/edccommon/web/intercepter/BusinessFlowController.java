package com.cmos.edccommon.web.intercepter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmos.cache.service.ICacheService;
import com.cmos.common.exception.GeneralException;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.utils.StringUtil;
import com.cmos.edccommon.utils.consts.AppCodeConsts;
import com.cmos.edccommon.utils.consts.CacheConsts;

/**
 * Created by guozong on 2017/10/30.
 */
@Aspect
@Component
public class BusinessFlowController {
    private static final Logger logger = LoggerFactory.getLogger(BusinessFlowController.class);

    /**
     * 定义切面范围
     */
    @Pointcut("@annotation(com.cmos.edccommon.web.aop.AopChecker)")
    public void pointcut() {
    }
    @Autowired
    private ICacheService cacheService;

    @Around("pointcut()")
    public Object process(ProceedingJoinPoint joinPoint) throws GeneralException {
        boolean isServiceTotalDecr = false;
        boolean isServiceSystemTotalDecr = false;
        Object r = null;
        String funtionName = joinPoint.getSignature().getName();
        String sourceSystem;
        try {
            Object argarg = joinPoint.getArgs()[0];
            String json = JSON.toJSONString(argarg);
            JSONObject jsonOb = JSON.parseObject(json);
            sourceSystem = jsonOb.getString(CacheConsts.FLOW_CONTROLLER_PARAM_KEY);
			if (StringUtil.isBlank(sourceSystem)) {
				logger.error("AopChecker 入参 sourceSystem 为空：" + sourceSystem);
				sourceSystem = AppCodeConsts.APP_SYS_ID.UNDEFINED;
			}
        } catch (Exception e) {
            logger.error("获取参数异常异常", e);
            try {
                r = joinPoint.proceed();
            } catch (Throwable e2) {
                logger.error("BusinessFlowController error:",e2);
                throw new GeneralException("FLOW805");
            }

            return r;
        }

        String serviceTotalKey = CacheConsts.CACHE_SWITCH_PREFIX + funtionName.toUpperCase() + "_ALL";// 阀值key
        String serviceSystemTotalKey = CacheConsts.CACHE_SWITCH_PREFIX + funtionName.toUpperCase() + "_" + sourceSystem.toUpperCase();// 阀值key
        String currServiceTotalKey = serviceTotalKey + "_REALTIME";
        String currServiceSystemTotalKey = serviceSystemTotalKey + "_REALTIME";

        //接口阀值
        long serviceTotalCount;
        //分接口分来源系统阀值
        long serviceSystemTotalCount;

        try {
            try {
                logger.info("当前缓存key值为:"+serviceTotalKey);
                serviceTotalCount = Long.parseLong(cacheService.getString(serviceTotalKey));
                logger.info("当前接口" + serviceTotalKey + "阀值为：" + serviceTotalCount);
                logger.info("当前接口" + serviceTotalKey + "阀值为：" + serviceTotalCount);
                serviceSystemTotalCount = Long.parseLong(cacheService.getString(serviceSystemTotalKey));
                logger.info("当前接口" + serviceSystemTotalKey + "分来源系统阀值为：" + serviceSystemTotalCount);
            } catch (Exception e) {
                logger.error("redis异常", e);
                r = joinPoint.proceed();
                return r;
            }
            //当前分接口分来源系统总量
            long currServiceSystemTotalCount = cacheService.incr(currServiceSystemTotalKey);
            logger.info("当前分接口分来源系统"+currServiceSystemTotalKey+"总量为:"+currServiceSystemTotalCount);
            isServiceSystemTotalDecr = true;
            if (currServiceSystemTotalCount > serviceSystemTotalCount) {
                logger.error("aopOverLimitInterface_params");
                throw new GeneralException("FLOW705");
            }
            //当前分接口总量
            long currServiceTotalCount = cacheService.incr(currServiceTotalKey);
            logger.info("当前分接口"+currServiceTotalKey+"总量为:"+currServiceTotalCount);
            isServiceTotalDecr = true;
            if (currServiceTotalCount > serviceTotalCount) {
                logger.error("aopOverLimitInterface");
                throw new GeneralException("FLOW505");
            }
            r = joinPoint.proceed();
        } catch (GeneralException e) {
            logger.error("流控校验超过阀值",e);
            throw e;
        }catch (Throwable e1) {
            logger.error("BusinessFlowController error:",e1);
            throw new GeneralException("FLOW805");
        } finally {
            if (isServiceTotalDecr) {
                cacheService.decr(currServiceTotalKey);
            }
            if (isServiceSystemTotalDecr) {
                cacheService.decr(currServiceSystemTotalKey);
            }
        }
        return r;

    }
}
