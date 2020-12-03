
package com.artcweb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.NailImageSize;
import com.artcweb.dao.NailImageSizeDao;
import com.artcweb.dto.NailImageSizeDto;
import com.artcweb.service.NailImageSizeService;
import com.artcweb.vo.NailImageSizeVo;

@Service
public class NailImageSizeServiceImpl extends BaseServiceImpl<NailImageSize, Integer> implements NailImageSizeService {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(NailImageSizeServiceImpl.class);
	@Autowired
	private NailImageSizeDao dao;

	/**
	 * @Title: findByPage
	 * @Description: 分页查询
	 * @param entity
	 * @param result
	 * @return
	 */
	@Override
	public LayUiResult findByPage(NailImageSizeVo entity, LayUiResult result) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("entity", entity);
		paramMap.put("page", result);
		Integer count = dao.findByPageCount(paramMap);
		if (null != count && count > 0) {
			List<NailImageSizeDto> dataList = dao.selectByPage(paramMap);
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
		Integer delete = dao.deleteByBatch(array);
		if (null != delete && delete > 0) {
			return true;
		}
		return false;
	}
}
