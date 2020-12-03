
package com.artcweb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artcweb.bean.Category;
import com.artcweb.bean.LayUiResult;
import com.artcweb.dao.CategoryDao;
import com.artcweb.dto.CategoryDto;
import com.artcweb.service.CategoryService;
import com.artcweb.vo.CategoryVo;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category, Integer> implements CategoryService {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	private CategoryDao categoryDao;

	/**
	 * @Title: findByPage
	 * @Description: 分页查询
	 * @param entity
	 * @param result
	 * @return
	 */
	@Override
	public LayUiResult findByPage(CategoryVo entity, LayUiResult result) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("entity", entity);
		paramMap.put("page", result);
		Integer count = categoryDao.findByPageCount(paramMap);
		if (null != count && count > 0) {
			List<CategoryDto> dataList = categoryDao.selectByPage(paramMap);
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
		Integer delete = categoryDao.deleteByBatch(array);
		if (null != delete && delete > 0) {
			return true;
		}
		return false;
	}

}
