
package com.artcweb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artcweb.baen.AdminRight;
import com.artcweb.baen.LayUiResult;
import com.artcweb.dao.AdminRightDao;
import com.artcweb.service.AdminRightService;

@Service
public class AdminRightServiceImpl extends BaseServiceImpl<AdminRight, Integer> implements AdminRightService {

	@Autowired
	private AdminRightDao adminRightDao;

	/**
	 * @Title: checkSaveParam
	 * @Description: 参数验证
	 * @param adminRight
	 * @return
	 */
	@Override
	public String checkSaveParam(AdminRight adminRight) {

		String rightRule = adminRight.getRightRule();
		if (StringUtils.isBlank(rightRule)) {
			return "参数[rightRule]不能为空!";
		}

		String rightName = adminRight.getRightName();
		if (StringUtils.isBlank(rightName)) {
			return "参数[rightName]不能为空!";
		}

		Integer rightCategoryId = adminRight.getRightCategoryId();
		if (null == rightCategoryId) {
			return "参数[rightCategoryId]不能为空!";
		}
		return null;
	}

	/**
	 * @Title: findByPage
	 * @Description: 分页查找
	 * @param adminRight
	 * @param result
	 * @return
	 */
	@Override
	public LayUiResult findByPage(AdminRight adminCate, LayUiResult result) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("entity", adminCate);
		paramMap.put("page", result);
		int count = findByPageCount(paramMap);
		if (count > 0) {
			List<AdminRight> dataList = findByPage(paramMap);
			result.setData(dataList);
			result.setCount(count);
		}
		return result;
	}

	/**
	 * @Title: deleteByBatch
	 * @Description: 批量删除
	 * @param strIds
	 * @return
	 */
	@Override
	public int deleteByBatch(String array) {

		return adminRightDao.deleteByBatch(array);
	}

}
