
package com.artcweb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.NailWhile;
import com.artcweb.bean.While;
import com.artcweb.dao.NailWhileDao;
import com.artcweb.dao.WhileDao;
import com.artcweb.service.NailWhileService;
import com.artcweb.service.WhileService;
import com.artcweb.vo.NailWhileVo;
import com.artcweb.vo.WhileVo;

@Service
public class NailWhileServiceImpl extends BaseServiceImpl<NailWhile, Integer> implements NailWhileService {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(NailWhileServiceImpl.class);
	
	@Autowired
	private  NailWhileDao nailWhileDao;

	@Override
	public LayUiResult findByPage(NailWhileVo entity, LayUiResult result) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("entity", entity);
		paramMap.put("page", result);
		Integer count = nailWhileDao.searchByPageCount(paramMap);
		if (null != count && count > 0) {
			List<NailWhile> dataList = nailWhileDao.searchByPage(paramMap);
			result.setData(dataList);
			result.setCount(count);
		}
		return result;
	}

	@Override
	public boolean deleteByBatch(String array) {
		Integer delete  = nailWhileDao.deleteByBatch(array);
		if(null != delete && delete > 0){
			return true;
		}
		return false;
	}

	
}
