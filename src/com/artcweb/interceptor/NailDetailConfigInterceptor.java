
package com.artcweb.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.artcweb.bean.NailDetailConfig;
import com.artcweb.cache.DateMap;
import com.artcweb.service.NailDetailConfigService;
import com.artcweb.util.ToolUtil;

public class NailDetailConfigInterceptor implements HandlerInterceptor {

	private static Logger logger = LoggerFactory.getLogger(NailDetailConfigInterceptor.class);
	
	@Autowired
	private NailDetailConfigService nailDetailConfigService;
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
					throws Exception {
		
		return true;

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
					throws Exception {
		

		String url = ToolUtil.getRequestURL(request);
		
		logger.info("url = "+ url);
		logger.info("同步缓存数据开始----------");
		
		Map<String,Object> paramMap = null;
		// 初始化
		List<NailDetailConfig> nailDetailConfigList = nailDetailConfigService.select(paramMap);
		DateMap.synChroNailDetailConfigMap(nailDetailConfigList);
	
		logger.info("同步缓存数据接受----------");

	}

	public void postHandle(	HttpServletRequest request, HttpServletResponse response, Object handler,
							ModelAndView modelAndView)
					throws Exception {

	}

}
