
package com.artcweb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artcweb.baen.AdminCategory;
import com.artcweb.baen.LayUiResult;
import com.artcweb.dao.AdminCategoryDao;
import com.artcweb.service.AdminCategoryService;

@Service
public class AdminCategoryServiceImpl extends BaseServiceImpl<AdminCategory, Integer> implements AdminCategoryService {

	@Autowired
	private AdminCategoryDao adminCategoryDao;

	/**
	 * @Title: checkSaveParam
	 * @Description: 参数验证
	 * @param adminCate
	 * @return
	 */
	@Override
	public String checkSaveParam(AdminCategory adminCate) {

		Integer categoryId = adminCate.getCategoryId();
		if (null == categoryId) {
			return "参数[categoryId]不能为空!";
		}
		String categoryName = adminCate.getCategoryName();
		if (StringUtils.isBlank(categoryName)) {
			return "参数[categoryName]不能为空!";

		}
		return null;
	}

	/**
	 * @Title: findByPage
	 * @Description: 分页查找
	 * @param adminCate
	 * @param result
	 * @return
	 */
	@Override
	public LayUiResult findByPage(AdminCategory adminCate, LayUiResult result) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("entity", adminCate);
		paramMap.put("page", result);
		int count = findByPageCount(paramMap);
		if (count > 0) {
			List<AdminCategory> dataList = findByPage(paramMap);
			result.setData(dataList);
			result.setCount(count);
		}
		return result;

	}

	@Override
	public int deleteByBatch(String StrIds) {
		return adminCategoryDao.deleteByBatch(StrIds);
	}

	/**
	 * @Title: checkAddUnique
	 * @Description: 新增唯一性验证
	 * @param adminCate
	 * @return
	 */
	@Override
	public String checkAddUnique(AdminCategory adminCate) {

		List<AdminCategory> list = null;
		// 分类ID与分类名唯一性验证
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("categoryId", adminCate.getCategoryId());
		paramMap.put("categoryName", adminCate.getCategoryName());
		list = adminCategoryDao.checkUnique(paramMap);
		if (null != list && list.size() > 0) {
			return "分类ID与分类名已存在!";
		}

		// 分类ID一性验证
		paramMap.remove("categoryName");
		list = adminCategoryDao.checkUnique(paramMap);
		if (null != list && list.size() > 0) {
			return "分类ID已存在!";
		}
		// 分类名唯一性验证
		paramMap.remove("categoryId");
		paramMap.put("categoryName", adminCate.getCategoryName());
		list = adminCategoryDao.checkUnique(paramMap);
		if (null != list && list.size() > 0) {
			return "分类名已存在!";
		}
		return null;
	}

	/**
	 * @Title: checkUpdateUnique
	 * @Description: 更新唯一性验证
	 * @param adminCate
	 * @return
	 */
	@Override
	public String checkUpdateUnique(AdminCategory adminCate) {

		List<AdminCategory> list = null;
		// 分类ID与分类名唯一性验证
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", adminCate.getId());
		paramMap.put("categoryId", adminCate.getCategoryId());
		paramMap.put("categoryName", adminCate.getCategoryName());
		list = adminCategoryDao.checkUnique(paramMap);
		if (null != list && list.size() > 0) {
			return "分类ID与分类名已存在!";
		}

		// 分类ID一性验证
		paramMap.remove("categoryName");
		list = adminCategoryDao.checkUnique(paramMap);
		if (null != list && list.size() > 0) {
			return "分类ID已存在!";
		}
		// 分类名唯一性验证
		paramMap.remove("categoryId");
		paramMap.put("categoryName", adminCate.getCategoryName());
		list = adminCategoryDao.checkUnique(paramMap);
		if (null != list && list.size() > 0) {
			return "分类名已存在!";
		}
		return null;
	}

}
