
package com.artcweb.service;

import com.artcweb.bean.Category;
import com.artcweb.bean.LayUiResult;
import com.artcweb.vo.CategoryVo;

/**
 * @ClassName: CategoryService
 * @Description: 类别
 */
public interface CategoryService extends BaseService<Category, Integer> {

	/**
	 * @Title: findByPage
	 * @Description: 分页查询
	 * @param entity
	 * @param result
	 * @return
	 */
	LayUiResult findByPage(CategoryVo entity, LayUiResult result);

	/**
	 * @Title: deleteByBatch
	 * @Description: 批量删除
	 * @param array
	 * @return
	 */
	boolean deleteByBatch(String array);

}
