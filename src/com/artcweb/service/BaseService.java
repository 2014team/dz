
package com.artcweb.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.artcweb.baen.BaseBean;

public abstract interface BaseService<T extends BaseBean, PK extends Serializable> {

	/**
	 * 保存数据
	 * 
	 * @param t
	 * @return
	 */
	public int save(T t);

	/**
	 * 保存或更新数据
	 * 
	 * @param t
	 * @return
	 */
	public int saveOrUpdate(T t);

	/**
	 * 修改记录 根据param条件
	 * 
	 * @param param 用于产生SQL的参数值，包括WHERE条件、目标字段和新值等
	 * @throws Exception
	 */
	public int update(Map<String, Object> param);

	/**
	 * 修改实体
	 */
	public int update(T t);

	/**
	 * 删除实体
	 * 
	 * @param id
	 * @throws Exception
	 */
	public int delete(PK id);

	/**
	 * 删除实体 根据param条件
	 * 
	 * @param param 用于产生SQL的参数值，包括WHERE条件、目标字段和新值等
	 * @throws Exception
	 */
	public int delete(Map<String, Object> param);

	/**
	 * 通过ID获取实体
	 */
	public T get(PK id);

	/**
	 * 查询实体 根据param条件
	 * 
	 * @param param 用于产生SQL的参数值，包括WHERE条件、目标字段和新值等
	 * @throws Exception
	 */
	public T get(Map<String, Object> param);

	/**
	 * 取全部记录
	 * 
	 * @return 全部记录实体对象的List
	 */
	public List<T> list();

	/**
	 * 按条件查询记录
	 * 
	 * @param param 查询条件参数，包括WHERE条件、分页条件、排序条件
	 * @return 符合条件记录的实体对象的List
	 */
	public List<T> select(Map<String, Object> param);

	/**
	 * 按条件查询记录
	 * 
	 * @param entity 实体对象
	 * @return
	 */
	public List<T> select(T t);

	/**
	 * 查询整表总记录数
	 */
	public int count();

	/**
	 * 查询符合条件的记录数
	 * 
	 * @param param 查询条件参数，包括WHERE条件（其他参数内容不起作用）。此参数设置为null，则相当于count()
	 * @return
	 */
	public int count(Map<String, Object> param);

	/**
	 * @Title: findByPage
	 * @Description: 分页查找
	 * @param param
	 * @return
	 */
	public List<T> findByPage(Map<String, Object> param);

	/**
	 * @Title: findByPageCount
	 * @Description: 分页查找总数
	 * @param param
	 * @return
	 */
	public int findByPageCount(Map<String, Object> param);
}