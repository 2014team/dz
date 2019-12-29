
package com.artcweb.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.artcweb.baen.LayUiResult;
import com.artcweb.baen.User;

public interface UserService extends BaseService<User, Integer> {

	/**
	 * @Title: checkSaveParam
	 * @Description: 参数验证
	 * @param entity
	 * @return
	 */
	public String checkSaveParam(User entity);

	/**
	 * @Title: checkUpdateUnique
	 * @Description: 更新唯一性验证
	 * @param entity
	 * @return
	 */
	public String checkUpdateUnique(User entity);

	/**
	 * @Title: checkAddUnique
	 * @Description: 保存唯一性验证
	 * @param entity
	 * @return
	 */
	public String checkAddUnique(User entity);

	/**
	 * @Title: findByPage
	 * @Description: 分页查找
	 * @param entity
	 * @param result
	 * @return
	 */
	public LayUiResult findByPage(User entity, LayUiResult result);

	/**
	 * @Title: deleteByBatch
	 * @Description: 批量删除
	 * @param array
	 * @return
	 */
	public boolean deleteByBatch(String array,HttpServletRequest request);

	/**
	 * @Title: getByMap
	 * @Description: 根据Map获取用户信息
	 * @param paramMap
	 * @return
	 */
	public User getByMap(Map<String, Object> paramMap);

	/**
	 * @Title: deleteUser
	 * @Description: 删除用户信息
	 */
	public LayUiResult deleteUser(Integer id,HttpServletRequest request);

	
	/**
	* @Title: updateUser
	* @Description: 修改用户信息
	* @param entity
	* @return
	*/
	public Integer updateUser(User entity);
}
