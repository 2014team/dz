
package com.artcweb.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.artcweb.bean.NailPictureFrame;
import com.artcweb.dto.NailPictureFrameDto;

@Repository
public interface NailPictureFrameDao extends BaseDao<NailPictureFrame, Integer> {

	List<NailPictureFrameDto> selectByPage(Map<String, Object> paramMap);

	Integer deleteByBatch(String array);
	

}