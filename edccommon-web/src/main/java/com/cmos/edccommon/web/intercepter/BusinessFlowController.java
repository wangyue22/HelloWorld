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
import com.cmos.edccommon.beans.common.EdcCoOutDTO;
import com.cmos.edccommon.utils.StringUtil;
import com.cmos.edccommon.utils.consts.CacheConsts;
import com.cmos.edccommon.utils.enums.ReturnInfoEnums;
import com.cmos.edccommon.web.cache.CacheFatctoryUtil;

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
    @Autowired
    CacheFatctoryUtil cacheFatctoryUtil;

    @Around("pointcut()")
    public Object process(ProceedingJoinPoint joinPoint) throws GeneralException {
        EdcCoOutDTO edcCoOutDTO = new EdcCoOutDTO();
        boolean isServiceTotalDecr = false;
        boolean isServiceSystemTotalDecr = false;
        Object r = null;
        String funtionName = joinPoint.getSignature().getName();
        String sourceSystem;
        //接口阀值
        long serviceTotalCount=0;
        //分接口分来源系统阀值
        long serviceSystemTotalCount=0;

        //接口瞬时并发值
        String currServiceTotalKey="";
        //接口+参数瞬时并发值
        String currServiceSystemTotalKey="";

        //是否做接口总流控
        boolean isNeedAllCheck = true;
        //是否做接口+参数流控
        boolean isNeedParamCheck = true;
        try {
            //解析入参
            Object argarg = joinPoint.getArgs()[0];
            String json = JSON.toJSONString(argarg);
            JSONObject jsonOb = JSON.parseObject(json);
            sourceSystem = jsonOb.getString(CacheConsts.FLOW_CONTROLLER_PARAM_KEY);
            if (StringUtil.isBlank(sourceSystem)) {
                logger.error("AopChecker 入参 sourceSystem 为空：" + sourceSystem);
                edcCoOutDTO.setReturnCode(ReturnInfoEnums.PROCESS_INPARAM_ERROR.getCode());
                edcCoOutDTO.setReturnMessage(ReturnInfoEnums.PROCESS_INPARAM_ERROR.getMessage());
                return edcCoOutDTO ;
            }
            //封装阀值key
            String serviceTotalKey = CacheConsts.CACHE_SWITCH_PREFIX + funtionName.toUpperCase() + "_ALL";// 阀值key
            String serviceSystemTotalKey = CacheConsts.CACHE_SWITCH_PREFIX + funtionName.toUpperCase() + "_" + sourceSystem.toUpperCase();// 阀值key

            //获取接口总控阀值
            if(StringUtil.isBlank(cacheFatctoryUtil.getJVMString(serviceTotalKey))){
                isNeedAllCheck=false;
            }else{
                serviceTotalCount = Long.parseLong(cacheFatctoryUtil.getJVMString(serviceTotalKey));
                logger.info("当前接口" + serviceTotalKey + "阀值配置为：" + serviceTotalCount);
            }
            //获取接口来源系统阀值
            if(StringUtil.isBlank(cacheFatctoryUtil.getJVMString(serviceSystemTotalKey))){
                isNeedParamCheck=false;
            }else{
                serviceSystemTotalCount = Long.parseLong(cacheFatctoryUtil.getJVMString(serviceSystemTotalKey));
                logger.info("当前接口" + serviceSystemTotalKey + "来源系统阀值配置为：" + serviceSystemTotalCount);
            }

            //判断当前并发并发是否超过阀值
            currServiceSystemTotalKey = serviceSystemTotalKey + "_REALTIME";
            long currServiceSystemTotalCount = cacheService.incr(currServiceSystemTotalKey);
            logger.info("当前接口分来源系统"+currServiceSystemTotalKey+"瞬时并发为:"+currServiceSystemTotalCount);
            isServiceSystemTotalDecr = true;
            if (isNeedParamCheck&&currServiceSystemTotalCount > serviceSystemTotalCount) {
                logger.error("aopOverLimitInterface_params" + " 当前系统:" + CacheConsts.STYSTEM_NAME + " 接口:"
                        + funtionName + " 来源系统：" + sourceSystem + " 流控配置阀值:" + serviceSystemTotalCount + " 当前瞬时并发:"
                        + currServiceSystemTotalCount);

                throw new GeneralException("FLOW705");
            }
            //当前分接口总量
            currServiceTotalKey = serviceTotalKey + "_REALTIME";
            long currServiceTotalCount = cacheService.incr(currServiceTotalKey);
            logger.info("当前接口"+currServiceTotalKey+"瞬时并发为:"+currServiceTotalCount);
            isServiceTotalDecr = true;
            if (isNeedAllCheck&&currServiceTotalCount > serviceTotalCount) {
                logger.error("aopOverLimitInterface" + " 当前系统:" + CacheConsts.STYSTEM_NAME + " 接口:"
                        + funtionName + " 流控配置阀值:" + serviceTotalCount + " 当前瞬时并发:" + currServiceTotalCount);

                throw new GeneralException("FLOW505");
            }
            //流控通过，进行业务处理
            r = joinPoint.proceed();
        } catch (GeneralException e) {
            edcCoOutDTO.setReturnCode(ReturnInfoEnums.FLOW_PROCESS_FAILED.getCode());
            edcCoOutDTO.setReturnMessage(ReturnInfoEnums.FLOW_PROCESS_FAILED.getMessage());
            return edcCoOutDTO;
        }catch (Throwable e1) {
            logger.error("BusinessFlowController error:",e1);
            edcCoOutDTO.setReturnCode(ReturnInfoEnums.PROCESS_ERROR.getCode());
            edcCoOutDTO.setReturnMessage(ReturnInfoEnums.PROCESS_ERROR.getMessage());
            return edcCoOutDTO ;

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
