
package com.artcweb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.NailDetailConfig;
import com.artcweb.dao.NailDetailConfigDao;
import com.artcweb.dto.NailDetailConfigDto;
import com.artcweb.service.NailDetailConfigService;
import com.artcweb.vo.NailDetailConfigVo;

@Service
public class NailDetailConfigServiceImpl extends BaseServiceImpl<NailDetailConfig, Integer> implements NailDetailConfigService {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(NailDetailConfigServiceImpl.class);
	@Autowired
	private NailDetailConfigDao nailDetailConfigDao;

	/**
	 * @Title: findByPage
	 * @Description: 分页查询
	 * @param entity
	 * @param result
	 * @return
	 */
	@Override
	public LayUiResult findByPage(NailDetailConfigVo entity, LayUiResult result) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("entity", entity);
		paramMap.put("page", result);
		Integer count = nailDetailConfigDao.findByPageCount(paramMap);
		if (null != count && count > 0) {
			List<NailDetailConfigDto> dataList = nailDetailConfigDao.selectByPage(paramMap);
			result.setData(dataList);
			result.setCount(count);
		}

		return result;
	}

	/**
	 * @Title: deleteByBatch
	 * @Description: 批量删除
	 * @param array
	 * @return
	 */
	@Override
	public boolean deleteByBatch(String array) {
		Integer delete = nailDetailConfigDao.deleteByBatch(array);
		if (null != delete && delete > 0) {
			return true;
		}
		return false;
	}

	@Override
	public NailDetailConfigDto selectByMap(Map<String, Object> paramMap) {
		return nailDetailConfigDao.selectByMap(paramMap);
	}
}
