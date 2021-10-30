
package com.artcweb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.NailConfig;
import com.artcweb.dao.NailConfigDao;
import com.artcweb.dto.NailConfigDto;
import com.artcweb.service.NailConfigService;
import com.artcweb.vo.NailConfigVo;

@Service
public class NailConfigServiceImpl extends BaseServiceImpl<NailConfig, Integer> implements NailConfigService {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(NailConfigServiceImpl.class);
	@Autowired
	private NailConfigDao nailConfigDao;

	/**
	 * @Title: findByPage
	 * @Description: 分页查询
	 * @param entity
	 * @param result
	 * @return
	 */
	@Override
	public LayUiResult findByPage(NailConfigVo entity, LayUiResult result) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("entity", entity);
		paramMap.put("page", result);
		Integer count = nailConfigDao.findByPageCount(paramMap);
		if (null != count && count > 0) {
			List<NailConfigDto> dataList = nailConfigDao.selectByPage(paramMap);
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
		Integer delete = nailConfigDao.deleteByBatch(array);
		if (null != delete && delete > 0) {
			return true;
		}
		return false;
	}

	@Override
	public NailConfigDto selectByMap(Map<String, Object> paramMap) {
		return nailConfigDao.selectByMap(paramMap);
	}
}
