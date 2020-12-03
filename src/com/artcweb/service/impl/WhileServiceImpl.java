
package com.artcweb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.While;
import com.artcweb.dao.WhileDao;
import com.artcweb.service.WhileService;
import com.artcweb.vo.WhileVo;

@Service
public class WhileServiceImpl extends BaseServiceImpl<While, Integer> implements WhileService {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(WhileServiceImpl.class);
	
	@Autowired
	private  WhileDao whileDao;

	@Override
	public LayUiResult findByPage(WhileVo entity, LayUiResult result) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("entity", entity);
		paramMap.put("page", result);
		Integer count = whileDao.searchByPageCount(paramMap);
		if (null != count && count > 0) {
			List<While> dataList = whileDao.searchByPage(paramMap);
			result.setData(dataList);
			result.setCount(count);
		}
		return result;
	}

	@Override
	public boolean deleteByBatch(String array) {
		Integer delete  = whileDao.deleteByBatch(array);
		if(null != delete && delete > 0){
			return true;
		}
		return false;
	}

	
}
