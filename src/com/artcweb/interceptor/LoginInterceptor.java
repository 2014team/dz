
package com.artcweb.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.artcweb.baen.AdminUser;
import com.artcweb.util.SessionUtil;
import com.artcweb.util.ToolUtil;

public class LoginInterceptor implements HandlerInterceptor {

	private static Logger logger = Logger.getLogger(LoginInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
					throws Exception {
		

		// 获取请求你url
		String url = ToolUtil.getRequestURL(request);

		// 获取用户session信息
		AdminUser user = SessionUtil.getSessionUser(request);
		if (null == user) {
			// 跳转登录
			String loginUrl = "/admin/login.do?url=" + url;
			response.sendRedirect(loginUrl);
			logger.error("LoginInterceptor.preHandle()未登录!");
			return false;
		}
		return true;

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
					throws Exception {

	}

	public void postHandle(	HttpServletRequest request, HttpServletResponse response, Object handler,
							ModelAndView modelAndView)
					throws Exception {

	}

}
