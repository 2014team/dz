
package com.artcweb.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.artcweb.baen.NailOrder;
import com.artcweb.dto.NailOrderDto;

@Repository
public interface NailOrderDao extends BaseDao<NailOrder, Integer> {

	List<NailOrderDto> selectByPage(Map<String, Object> paramMap);

	Integer deleteByBatch(String array);
	
	NailOrder getById(Integer id);
	

}