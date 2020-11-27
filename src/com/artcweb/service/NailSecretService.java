
package com.artcweb.service;

import java.util.Map;

import com.artcweb.baen.LayUiResult;
import com.artcweb.baen.NailSecret;
import com.artcweb.vo.NailSecretVo;

public interface NailSecretService extends BaseService<NailSecret, Integer> {

	/**
	* @Title: findByPage
	* @Description: 查询列表
	* @param NailSecret
	* @param result
	* @return
	*/
	LayUiResult findByPage(NailSecretVo NailSecret, LayUiResult result);

	/**
	* @Title: saveSecret
	* @Description: 保存秘钥
	* @param entity
	* @return
	*/
	boolean saveSecret(NailSecretVo entity);

	
	/**
	* @Title: deleteByBatch
	* @Description: 批量删除
	* @param array
	* @return
	*/
	boolean deleteByBatch(String array);
	
	NailSecret getByMap(Map<String,Object> paramMap);
	
	boolean updateByMap(Map<String,Object> paramMap);
	

}
