
package com.artcweb.service;

import java.util.Map;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.Secret;
import com.artcweb.dto.SecretDto;
import com.artcweb.vo.SecretVo;

public interface SecretService extends BaseService<Secret, Integer> {

	/**
	* @Title: findByPage
	* @Description: 查询列表
	* @param Secret
	* @param result
	* @return
	*/
	LayUiResult findByPage(SecretVo Secret, LayUiResult result);

	/**
	* @Title: saveSecret
	* @Description: 保存秘钥
	* @param entity
	* @return
	*/
	boolean saveSecret(SecretVo entity);

	
	/**
	* @Title: deleteByBatch
	* @Description: 批量删除
	* @param array
	* @return
	*/
	boolean deleteByBatch(String array);
	
	Secret getByMap(Map<String,Object> paramMap);
	
	boolean updateByMap(Map<String,Object> paramMap);
	
	SecretDto detail(Map<String, Object> paramMap);;
	

}
