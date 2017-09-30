package com.cmos.edccommon.web.intercepter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * Created by zhuzh on 2016/11/18.
 */
@Aspect
@Component
public class ControllerValidatorInterceptor {
    @Around("execution(* com.cmos.edccommon.web.controller.*.*(..)) && args(..,bindingResult)")
    public Object doAround(ProceedingJoinPoint pjp, BindingResult bindingResult) throws Throwable {
        Object returnVal = null;
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                sb.append(fieldError.getDefaultMessage() + ",");
            }
            returnVal = sb.toString();
        } else {
            returnVal = pjp.proceed();
        }
        return returnVal;
    }
}