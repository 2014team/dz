package com.artcweb.dao;

import java.util.List;
import java.util.Map;
import com.artcweb.dao.BaseDao;
import org.springframework.stereotype.Repository;
import com.artcweb.bean.NailH5Strjson;

import com.artcweb.dto.NailH5StrjsonDto;

/**
 * @ClassName: NailH5StrjsonDao
 * @Description: H5调用
 * @author zhuzq
 * @date 2020年12月07日 16:17:37
 */
@Repository
public interface NailH5StrjsonDao extends BaseDao<NailH5Strjson,Integer>{

	public Integer selectByPageCount(Map<String, Object> paramMap);
	
	public List<NailH5StrjsonDto> selectByPage(Map<String, Object> paramMap);

	public Integer deleteByBatch(String array);

}
