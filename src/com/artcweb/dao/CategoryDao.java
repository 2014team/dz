
package com.artcweb.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.artcweb.bean.Category;
import com.artcweb.dto.CategoryDto;

@Repository
public interface CategoryDao extends BaseDao<Category, Integer> {
	/**
	 * 
	* @Title: deleteByBatch
	* @Description: 批量删除
	* @param array
	* @return
	 */
	Integer deleteByBatch(String array);
	

	List<CategoryDto> selectByPage(Map<String, Object> paramMap);

}