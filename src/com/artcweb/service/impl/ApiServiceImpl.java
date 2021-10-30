package com.artcweb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.Order;
import com.artcweb.bean.PicPackage;
import com.artcweb.bean.Weixin;
import com.artcweb.dao.CategoryDao;
import com.artcweb.dao.OrderDao;
import com.artcweb.dao.PicPackageDao;
import com.artcweb.dao.WeixinDao;
import com.artcweb.dto.CategoryDto;
import com.artcweb.dto.PicPackageDto;
import com.artcweb.service.ApiService;
import com.artcweb.vo.WeixinVo;

@Service
public class ApiServiceImpl implements  ApiService{
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(ApiServiceImpl.class);
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private PicPackageDao picPackageDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private WeixinDao weixinDao;

	@Override
	public LayUiResult findByPage(PicPackage entity, LayUiResult result) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("page", result);
		Integer count = categoryDao.findByPageCount(paramMap);
		if (null != count && count > 0) {
			List<CategoryDto> dataList = categoryDao.selectByPage(paramMap);
			//获取模板
			getPicPackage(entity,dataList);
			result.setData(dataList);
			result.setCount(count);
		}
		return result;
	}

	private void getPicPackage(PicPackage entity,List<CategoryDto> dataList) {
		if(null == dataList || dataList.size() < 0){
			return;
		}
		
		Integer pins = entity.getPins();
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("pins", pins);
		
		for (CategoryDto categoryDto : dataList) {
			paramMap.put("categoryId", categoryDto.getId());
			List<PicPackageDto> picPackageList = picPackageDao.selectByApiIndex(paramMap);
			categoryDto.setPicPackageList(picPackageList);
		}
	}

	/**
	* @Title: bind
	* @Description: 绑定账号
	* @param weixinVo
	* @return
	*/
	@Override
	public LayUiResult bind(WeixinVo weixinVo) {
		LayUiResult result = new LayUiResult();
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("openid", weixinVo.getOpenid());
		
		Weixin w = weixinDao.getByMap(paramMap);
		if(w == null){
			result.failure("根据openid没有查到微信账号信息");
			return result;
		}
		Integer bindFlag =  w.getBindFlag();
		if(null != bindFlag && 1 == bindFlag){
			result.failure("已经绑定"+w.getMobile());
			return result;
		}
		
		paramMap.put("mobile", weixinVo.getMobile());
		Order order = orderDao.selectOne(paramMap);
		if(null == order){
			result.failure("根据手机号码没有在订单中查到手机号码，无法绑定");
			return result;
		}
		
		
		paramMap.clear();
		
		String m = order.getMobile();
		String userId = order.getUserId();
		paramMap.put("mobile", m);
		paramMap.put("userId", Integer.valueOf(userId));
		paramMap.put("bindFlag", 1);
		paramMap.put("id", w.getId());
		// 更新为绑定
		int update = weixinDao.update(paramMap);
		if(update > 0){
			result.success(weixinVo);
			return result;
		}
		
		result.failure("绑定失败");
		return result;
	}
	
	
}
