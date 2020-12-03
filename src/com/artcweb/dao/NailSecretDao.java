
package com.artcweb.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.artcweb.bean.NailSecret;
import com.artcweb.dto.NailSecretDto;

@Repository
public interface NailSecretDao extends BaseDao<NailSecret, Integer> {

	Integer deleteByBatch(String array);
	
	Integer checkExit(Map<String,Object> paramMap);
	
	Integer saveBatch(List<String> secretList);
	
	NailSecret getByMap(Map<String,Object> paramMap);
	
	Integer updateByMap(Map<String,Object> paramMap);
	
	List<NailSecretDto> searchByPage(Map<String, Object> paramMap);
	
	Integer searchByPageCount(Map<String, Object> paramMap);
	
	

	

}