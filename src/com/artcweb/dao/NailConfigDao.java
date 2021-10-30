
package com.artcweb.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.artcweb.bean.NailConfig;
import com.artcweb.dto.NailConfigDto;

@Repository
public interface NailConfigDao extends BaseDao<NailConfig, Integer> {

	List<NailConfigDto> selectByPage(Map<String, Object> paramMap);

	Integer deleteByBatch(String array);
	
	
	NailConfigDto selectByMap(Map<String, Object> paramMap);
	

}