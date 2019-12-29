
package com.artcweb.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.artcweb.baen.AdminUser;
import com.artcweb.baen.LayUiResult;

public interface AdminUserService extends BaseService<AdminUser, Integer> {

	/**
	 * @Title: checkLoginParam
	 * @Description: 验证登录参数
	 * @param user
	 * @return
	 */
	public String checkLoginParam(AdminUser user);

	/**
	 * @Title: login
	 * @Description: 登录
	 * @param user
	 * @return
	 */
	public AdminUser login(AdminUser user);

	public LayUiResult findByPage(AdminUser entity, LayUiResult result);

	public int deleteByBatch(String array);

	public AdminUser getById(Integer id);

	public List<AdminUser> checkUnique(Map<String, Object> paramMap);

	public String checkAdmin(HttpServletRequest request);

}
