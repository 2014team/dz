package com.artcweb.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.PicPackage;
import com.artcweb.bean.Weixin;
import com.artcweb.service.ApiService;
import com.artcweb.service.WeixinService;
import com.artcweb.vo.WeixinVo;

/**
* @ClassName: ApiIndexController
* @Description: 首页
*/

@Controller
public class ApiIndexController {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(ApiIndexController.class);
	@Autowired
	private ApiService apiService;
	
	@Autowired
	private WeixinService weixinService;
	
	
	/**
	* @Title: index
	* @Description: 首页
	* @author zhuzq
	* @date  2020年11月7日 下午1:44:20
	* @param entity
	* @param result
	* @return
	*/
	@ResponseBody
	@RequestMapping("/api/index/list")
	public LayUiResult index(PicPackage entity, LayUiResult result){
		
		Integer pins = entity.getPins();
		if(null == pins){
			result.failure("参数[pins]不能为空");
			return result;
			
		}
		result = apiService.findByPage(entity, result);
		return result;
		
	}
	
	/**
	* @Title: login
	* @Description: 微信登录
	* @param js_code
	* @return
	*/
	@ResponseBody
	@RequestMapping("/api/index/login")
	public LayUiResult login(String js_code){
		
		logger.info("js_code==========>"+js_code);
		
		LayUiResult result  = new LayUiResult();
		
		//验证
		if(StringUtils.isEmpty(js_code)){
			result.failure("参数[js_code]不能为空");
			return result;
			
		}
		
		Weixin weixin = weixinService.saveOpenid(js_code);
		if(null != weixin){
			if(StringUtils.isNotEmpty(weixin.getOpenid())){
				result.success(weixin);
			}else{
				result.failure(weixin.getErrmsg());
			}
		}
		return result;
		
	}
	
	/**
	* @Title: binld
	* @Description: 账号绑定
	* @author zhuzq
	* @date  2020年11月7日 下午1:59:51
	* @param mobile
	* @return
	*/
	@ResponseBody
	@RequestMapping("/api/user/bind")
	public LayUiResult binld(WeixinVo weixinVo){
		LayUiResult result  = new LayUiResult();
		
		//验证
		String openid  = weixinVo.getOpenid();
		if(StringUtils.isEmpty(openid)){
			result.failure("参数[openid]不能为空");
			return result;
			
		}
		String mobile  = weixinVo.getMobile();
		if(StringUtils.isEmpty(mobile)){
			result.failure("参数[mobile]不能为空");
			return result;
			
		}
		
		return apiService.bind(weixinVo);
		
	}

	
	
}
