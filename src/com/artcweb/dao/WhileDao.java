
package com.artcweb.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.artcweb.bean.While;

@Repository
public interface WhileDao extends BaseDao<While, Integer> {

	Integer searchByPageCount(Map<String, Object> paramMap);

	List<While> searchByPage(Map<String, Object> paramMap);

	Integer deleteByBatch(String array);

	
	
	

	

}