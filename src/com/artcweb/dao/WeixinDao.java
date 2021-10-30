
package com.artcweb.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.artcweb.bean.Weixin;

@Repository
public interface WeixinDao extends BaseDao<Weixin, Integer> {
	
	public Weixin getByMap(Map<String,Object> paramMap);
	

}