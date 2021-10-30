
package com.artcweb.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.artcweb.bean.NailDetailConfig;
import com.artcweb.dto.NailDetailConfigDto;

@Repository
public interface NailDetailConfigDao extends BaseDao<NailDetailConfig, Integer> {

	List<NailDetailConfigDto> selectByPage(Map<String, Object> paramMap);

	Integer deleteByBatch(String array);
	
	NailDetailConfigDto selectByMap(Map<String, Object> paramMap);
	

}