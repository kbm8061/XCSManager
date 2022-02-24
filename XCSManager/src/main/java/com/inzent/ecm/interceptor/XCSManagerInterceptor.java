package com.inzent.ecm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.inzent.ecm.vo.UserVO;
 
@Component
public class XCSManagerInterceptor extends HandlerInterceptorAdapter{
	Logger logger = LoggerFactory.getLogger(XCSManagerInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//ID Check
		HttpSession session = request.getSession(); 
		UserVO vo = (UserVO)session.getAttribute("XCSMUser");
		
		if ( vo == null ) {
			response.sendRedirect(request.getContextPath() + "/login");
			//response.sendRedirect(request.getContextPath() + "/static");
			//response.sendRedirect(request.getRequestURL().toString());
		  
			return false; 
		}
		 
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
    }
}