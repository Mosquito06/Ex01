package com.dgit.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("postHandle =======================================================");	
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("preHandle =======================================================");
		
		HttpSession session = request.getSession();
		if(session.getAttribute("login") == null){
			saveDest(request);
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false; // 로그인 페이지로 갈 시에는 readPage의 controller가 수행되지 않도록 return false
		}
		 
		
		return true;
	}
	
	
	// 로그인 전에 눌렀던 페이지를 저장하기 위한 함수
	private void saveDest(HttpServletRequest request){
		String uri = request.getRequestURI(); // /ex01/sboard/readPage 반환
		String query = request.getQueryString(); // page=1&perPageNum=10&searchType&keyword&bno=378
		
		if(query == null || query.equals("null")){
			query = "";
		}else{
			query = "?" + query;
		}
		
		if(request.getMethod().equals("GET")){
			logger.info("dest : " + (uri + query) );
			request.getSession().setAttribute("dest", uri + query);
		}
	}

}
