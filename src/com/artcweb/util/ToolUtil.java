
package com.artcweb.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.SortedMap;

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

	public static boolean checkApiSign(SortedMap<String, Object> paramMap, String apikey) {
		if(null == paramMap || paramMap.size() < 1){
			logger.error("请求参数错误[paramMap]="+paramMap);
			return false;
		}
		
		Object apiTimeStampObj = paramMap.get("apiTimeStamp");
		if(null == apiTimeStampObj){
			logger.error("请求参数错误[apiTimeStamp]="+apiTimeStampObj);
			return false;
		}
		Object apiSignObj = paramMap.get("apiSign");
		if(null == apiSignObj){
			logger.error("请求参数错误[apiTimeStampobj]="+apiSignObj);
			return false;
		}
		String apiTimeStamp = (String) apiTimeStampObj;
		String apiSign = (String) apiSignObj;

		String sign= EncryptUtil.Encrypt(apiTimeStamp, apikey, "ECB");
		if(!apiSign.equals(sign)){
			logger.error("签名错误 apiSign="+apiSign+" sign="+sign);
			return false;
		}
		return true;
	}
}
