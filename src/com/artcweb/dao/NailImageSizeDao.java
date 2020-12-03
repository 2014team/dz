
package com.artcweb.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.artcweb.bean.NailImageSize;
import com.artcweb.dto.NailImageSizeDto;

@Repository
public interface NailImageSizeDao extends BaseDao<NailImageSize, Integer> {

	List<NailImageSizeDto> selectByPage(Map<String, Object> paramMap);

	Integer deleteByBatch(String array);
	
	

}