
package com.artcweb.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.artcweb.bean.Secret;
import com.artcweb.dto.SecretDto;

@Repository
public interface SecretDao extends BaseDao<Secret, Integer> {

	Integer deleteByBatch(String array);
	
	Integer checkExit(Map<String,Object> paramMap);
	
	Integer saveBatch(List<String> secretList);
	
	Secret getByMap(Map<String,Object> paramMap);
	
	Integer updateByMap(Map<String,Object> paramMap);
	
	List<SecretDto> searchByPage(Map<String, Object> paramMap);
	
	Integer searchByPageCount(Map<String, Object> paramMap);
	
	
	SecretDto getDetail(Map<String, Object> paramMap);
	
	
	
	

	

}