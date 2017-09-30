package com.cmos.edccommon.web.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.dubbo.rpc.RpcContext;
import com.cmos.commons.bean.UserInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 参数拦截器
 * 
 * @author likerui
 * @version 1.0
 */
public class PrivateParamInterceptor extends HandlerInterceptorAdapter {

	private final  Logger logger = LoggerFactory.getLogger(PrivateParamInterceptor.class);

	/**
	 * This implementation always returns {@code true}.
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		String sessionId = request.getSession().getId();
		
		String serialNumber = "201612061722";
		
		String ip = request.getHeader("X-Real-IP");
		
		//正式环境从session中读取
		String operId = "10002";
		
		UserInfo userInfo = new UserInfo(sessionId ,serialNumber ,ip ,operId);
		
		String userInfoJsonStr = null;

		ObjectMapper mapper = new ObjectMapper();
		try {
			userInfoJsonStr = mapper.writeValueAsString(userInfo);
			
			System.out.println(userInfoJsonStr);
			
			
			UserInfo user2 = mapper.readValue(userInfoJsonStr, UserInfo.class);  
			
			System.out.println(user2);
			
			
		} catch (JsonProcessingException e) {
			logger.error("error convert map: {} to json string", userInfo, e);
		}
		
		RpcContext.getContext().setAttachment("user", userInfoJsonStr);
		return true;

	}

	/**
	 * This implementation is empty.
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/**
	 * This implementation is empty.
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	/**
	 * This implementation is empty.
	 */
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
	}
	/*
	*//**
	 * 获取用户的登录信息
	 * 
	 * @param request
	 * @return
	 */
	/*
	 * private static LoginUser generate(HttpServletRequest request) { LoginUser
	 * user = null;
	 * 
	 * Object object = request.getSession().getAttribute("userInfo");
	 * 
	 * if(object != null){ user = (LoginUser)object; if(user!=null){ // FIXME:
	 * 改为通过工具类获取:每次请求时候Ip信息会变化，故此不能把ip直接写到session中 String ip =
	 * request.getHeader("X-Real-IP"); user.setIp(ip); }
	 * 
	 * } return user; }
	 */

}
