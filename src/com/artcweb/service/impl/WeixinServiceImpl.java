
package com.artcweb.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artcweb.bean.Weixin;
import com.artcweb.dao.WeixinDao;
import com.artcweb.service.WeixinService;
import com.artcweb.util.WeixinUtil;

@Service
public class WeixinServiceImpl extends BaseServiceImpl<Weixin, Integer> implements WeixinService {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(WeixinServiceImpl.class);

	@Autowired
	private WeixinDao weixinDao;

	@Override
	public Weixin saveOpenid(String js_code) {
		Weixin weixin = WeixinUtil.getOpenid(js_code);
		
		if(null != weixin && StringUtils.isNotEmpty(weixin.getOpenid())){
			String openid= weixin.getOpenid();
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("openid", openid);
			
			Weixin entity = weixinDao.getByMap(paramMap);
			// 不存在保存
			if(null == entity){
				
				weixinDao.save(weixin);
			}else{
				weixin = entity;
			}
			
		}
		
		return weixin;
	}


}
