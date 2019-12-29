
package com.artcweb.service;

import com.artcweb.baen.AdminRight;
import com.artcweb.baen.LayUiResult;

public interface AdminRightService extends BaseService<AdminRight, Integer> {

	/**
	 * @Title: checkSaveParam
	 * @Description: 参数验证
	 * @param adminRight
	 * @return
	 */
	public String checkSaveParam(AdminRight adminRight);

	/**
	 * @Title: findByPage
	 * @Description: 分页查找
	 * @param adminRight
	 * @param result
	 * @return
	 */
	public LayUiResult findByPage(AdminRight adminRight, LayUiResult result);

	/**
	 * @Title: deleteByBatch
	 * @Description: 批量删除
	 * @param strIds
	 * @return
	 */
	public int deleteByBatch(String strIds);

}
