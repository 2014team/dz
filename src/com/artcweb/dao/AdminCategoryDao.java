
package com.artcweb.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.artcweb.baen.AdminCategory;

@Repository
public interface AdminCategoryDao extends BaseDao<AdminCategory, Integer> {

	/**
	 * @Title: deleteByBatch
	 * @Description: 批量删除
	 * @param StrIds
	 * @return
	 */
	int deleteByBatch(String StrIds);

	/**
	 * @Title: checkUnique
	 * @Description:唯一性验证
	 * @param paramMap
	 * @return
	 */
	List<AdminCategory> checkUnique(Map<String, Object> paramMap);

}