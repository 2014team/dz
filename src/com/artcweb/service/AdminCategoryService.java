
package com.artcweb.service;

import com.artcweb.baen.AdminCategory;
import com.artcweb.baen.LayUiResult;

public interface AdminCategoryService extends BaseService<AdminCategory, Integer> {

	/**
	 * @Title: checkSaveParam
	 * @Description: 参数验证
	 * @param operator
	 * @return
	 */
	public String checkSaveParam(AdminCategory adminCate);

	/**
	 * @Title: findByPage
	 * @Description: 分页查找
	 * @param adminCate
	 * @param result
	 * @return
	 */
	public LayUiResult findByPage(AdminCategory adminCate, LayUiResult result);

	/**
	 * @Title: deleteByBatch
	 * @Description: 批量查找
	 * @param StrIds
	 * @return
	 */
	int deleteByBatch(String StrIds);

	/**
	 * @Title: checkAddUnique
	 * @Description: 新增唯一性验证
	 * @param adminCate
	 * @return
	 */
	public String checkAddUnique(AdminCategory adminCate);

	/**
	 * @Title: checkUpdateUnique
	 * @Description: 更新唯一性验证
	 * @param adminCate
	 * @return
	 */
	public String checkUpdateUnique(AdminCategory adminCate);

}
