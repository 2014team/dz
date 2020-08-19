
package com.artcweb.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;

public class ToolUtil {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(ToolUtil.class);

	/**
	 * 获取请求的url完整地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestURL(HttpServletRequest request) {

		String queryString = request.getQueryString();
		queryString = queryString == null ? "" : queryString.trim();
		try {
			queryString = URLEncoder.encode(queryString, "utf-8");
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("ToolUtil.getRequestURL()获取请求的url完整地址错误");
		}
		queryString = "".equals(queryString) ? "" : "?" + queryString;
		return request.getRequestURL() + queryString;
	}
}
