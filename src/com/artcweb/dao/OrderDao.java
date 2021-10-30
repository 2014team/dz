
package com.artcweb.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.artcweb.bean.Order;

@Repository
public interface OrderDao extends BaseDao<Order, Integer> {

	/**
	 * @Title: deleteByBatch
	 * @Description: 批量删除
	 * @param array
	 * @return
	 */
	public int deleteByBatch(String array);

	/**
	 * @Title: getOrderDetail
	 * @Description: 获取订单详情
	 * @param paramMap
	 * @return
	 */
	public Order getOrderDetail(Map<String, Object> paramMap);

	/**
	 * @Title: findPageByApi
	 * @Description: 分页查找
	 * @param entity
	 * @param result
	 * @return
	 */
	public List<Order> findPageByApi(Map<String, Object> paramMap);

	/**
	 * @Title: findPageByApiCount
	 * @Description: 分页获取数量
	 * @param paramMap
	 * @return
	 */
	public Integer findPageByApiCount(Map<String, Object> paramMap);

	/**
	 * @Title: updateCurrentStep
	 * @Description: 修改订单当前执行步骤
	 * @param paramMap
	 * @return
	 */
	public Integer updateCurrentStep(Map<String, Object> paramMap);

	/**
	 * @Title: getByMap
	 * @Description: 通过id获取订单信息
	 * @param paramMap
	 * @return
	 */
	public Order getByMap(Map<String, Object> paramMap);

	/**
	 * @Title: deleteByMap
	 * @Description: 删除
	 * @param paramMap
	 * @return
	 */
	public Integer deleteByMap(Map<String, Object> paramMap);

	public List<Order> selectByMap(Map<String, Object> paramMap);

	public Integer checkExistOreder(Map<String, Object> paramMap);
	
	public Order getPackageName(Map<String, Object> paramMap);
	
	public Order selectOne(Map<String, Object> paramMap);

}