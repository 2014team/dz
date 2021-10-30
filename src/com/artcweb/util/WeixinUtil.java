
package com.artcweb.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import com.artcweb.bean.Weixin;
import com.artcweb.service.impl.WeixinServiceImpl;

public class WeixinUtil {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(WeixinUtil.class);
	
	private static String APPID = "wx824d009cd8316457";
	private static String SECRET = "75b49f7e659a9cc54ee323ea9a80aadb";
	private static String URL_CODE = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
	
	static{
		URL_CODE = URL_CODE.replace("APPID", APPID);
		URL_CODE = URL_CODE.replace("SECRET", SECRET);
	}
	
	public static Weixin getOpenid(String js_code){
		String url = URL_CODE.replace("JSCODE", js_code);
		String jonStr = HttpUtil.httpRequest(url);
		logger.info("jonStr = "+jonStr);
		Weixin weixin = null;
		if(StringUtils.isNotEmpty(jonStr)){
			weixin  =( Weixin ) GsonUtil.jsonToBean(jonStr, Weixin.class);
		}
		return weixin;
	}


}
