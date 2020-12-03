
package com.artcweb.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.artcweb.bean.NailWhile;

@Repository
public interface NailWhileDao extends BaseDao<NailWhile, Integer> {

	Integer searchByPageCount(Map<String, Object> paramMap);

	List<NailWhile> searchByPage(Map<String, Object> paramMap);

	Integer deleteByBatch(String array);

	
	
	

	

}