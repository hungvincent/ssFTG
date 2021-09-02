package com.ftg;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class SessionInterceptor implements HandlerInterceptor { 
	

	@Override 
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception { 
		//session中獲取使用者名稱資訊 
		String user = "";
//		Sysadmin user = (Sysadmin)request.getSession(true).getAttribute("sysuser");
		if (user == null || "".equals(user.toString())) { 
			response.sendRedirect(request.getSession().getServletContext().getContextPath()+"/");
			return false;
		}
		return true;
	}
	
	
	@Override 
	public void postHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler, ModelAndView modelAndView) throws Exception { 
	} 
	
	
	@Override 
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception { 
	} 
}

