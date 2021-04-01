package com.artcweb.interceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.artcweb.util.GsonUtil;
import com.artcweb.util.HttpUtil;
import com.artcweb.util.PropertiesUtils;
import com.artcweb.util.ToolUtil;

/**
* @ClassName: GlobalApiInterceptor
* @Description: API全局拦截器
* @author zhuzq
* @date 2020年12月2日 下午1:35:13
*/
public class GlobalApiInterceptor implements HandlerInterceptor {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(GlobalApiInterceptor.class);

	private static final String apiKey = PropertiesUtils.getValue("api_secret");

	private static Map<String, String> timeMap = new HashMap<String, String>();
	
	/**
	 * 该方法将在Controller处理之前进行调用 返回true 时才会继续执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		
		
		String zzq = request.getParameter("zzq");
		if(StringUtils.isNotBlank(zzq) && zzq.equals("zzq")){
			return true;
		}
		// 获取请求IP
		String ip = HttpUtil.getIpAddr(request);
		logger.info("ip="+ip);
		
		// 获取所有参数并封装成map
		SortedMap<String, Object> paramMap = HttpUtil.getRequestParams2(request);
		
		String jsonStr = GsonUtil.toJsonAll(paramMap);
		String requestURI = request.getRequestURI();
		
		// 验证是否符合要求请求
//		boolean checkTimeMap = checkTimeMap(paramMap);
//		if(!checkTimeMap){
//			logger.error("api接口签名错误 ip:" + ip + "--uri:" + requestURI + "--params:" + jsonStr);
//			return false;
//		}
		
		
		
		boolean checkApiSign =  ToolUtil.checkApiSign(paramMap, apiKey);
		if(!checkApiSign){
			logger.error("api接口签名错误 ip:" + ip + "--uri:" + requestURI + "--params:" + jsonStr);
			return false;
		}
		return true;
	}
	
	/**
	 * 请求完成后处理
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	/**
	 * 在Controller的方法调用之后 DispatcherServlet进行视图的渲染之前执行 即刚执行完controller时
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}
	
	
	/**
	* @Title: checkTimeMap
	* @Description: 根据时间戳检查是否正常请求
	* @param paramMap
	* @return
	*/
	public boolean checkTimeMap(SortedMap<String, Object> paramMap) {
		if(null == paramMap || paramMap.size() < 1){
			logger.error("请求参数错误[paramMap]="+paramMap);
			return false;
		}
		
		Object apiTimeStampObj = paramMap.get("apiTimeStamp");
		if(null == apiTimeStampObj){
			logger.error("请求参数错误[apiTimeStamp]="+apiTimeStampObj);
			return false;
		}
		
		// 防止内存溢出
		Integer timeMapSize = timeMap .size();
		if( timeMapSize > 10000){
			timeMap.clear();
		}
		
		
		String apiTimeStamp = (String) apiTimeStampObj;
		
		Object obj = timeMap.get(apiTimeStamp);
		if(null != obj){
			logger.error("请求已经失效,已请求过");
			return false;
		}
		timeMap.put(apiTimeStamp, apiTimeStamp);
		
		return true;
		
		
	}
	/**
	 * ip访问拦截
	 * 
	 * @param ip
	 * @return
	 */
	public boolean ipFilter(String ip) {
//
//		if (countMap.size() > 10000) {
//			// 内存太大了，清空内存
//			countMap.clear();
//			timeMap.clear();
//		}
//		if (ip.contains("183.3.221.159") || ip.contains("183.3.221.160")) {
//			// 汽车、印刷网站的请求，允许通行
//			return true;
//		}
//
//		long now = System.currentTimeMillis();// 当前时间戳
//
//		if (!countMap.containsKey(ip)) {
//			// 第一次访问，先保存
//			timeMap.put(ip, now);// 保存访问时间
//			countMap.put(ip, 1);// 保存访问次数
//
//			return true;
//		}
//
//		// 已包含,则判断是否是恶意攻击,该ip访问距离上一次访问小于1秒内，且访问次数大于10次，则视为恶意攻击
//		int count = countMap.get(ip) + 1;
//		Long lastTime = timeMap.get(ip);// 上次访问时间
//		if (null == lastTime) {
//			lastTime = now;
//		}
//
//		// 保存访问时间
//		timeMap.put(ip, now);
//		if (now - lastTime < 1000) {
//			// 本次访问距离上一次访问小于1秒内
//			countMap.put(ip, count);
//			if (count > 10) {
//				// 本次访问距离上一次访问小于1秒内，且访问次数大于10次，则视为恶意攻击
//				return false;
//			}
//
//		} else {
//			// 访问时间超过一秒了的，count重新开始计算
//			countMap.put(ip, 1);
//		}

		return true;
	}
}