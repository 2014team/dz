package com.artcweb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artcweb.baen.AdminUser;
import com.artcweb.baen.LayUiResult;
import com.artcweb.dao.AdminUserDao;
import com.artcweb.service.AdminUserService;
import com.artcweb.util.SessionUtil;

@Service
public class AdminUserServiceImpl extends BaseServiceImpl<AdminUser, Integer> implements  AdminUserService{
	
	@Autowired
	private AdminUserDao adminUserDao;
	

	/**
	* @Title: checkLoginParam
	* @Description: 验证登录参数
	* @param user
	* @return
	*/
	@Override
	public String checkLoginParam(AdminUser user) {
		String userName = user.getUserName();
		if(StringUtils.isBlank(userName)){
			return "用户名不能为空!";
		}
		String password = user.getPassword();
		if(StringUtils.isBlank(password)){
			return "密码不能为空!";
		}
		return null;
	}

	/**
	* @Title: login
	* @Description: 登录
	* @param user
	* @return
	*/
	@Override
	public AdminUser login(AdminUser user) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("userName", user.getUserName());
		paramMap.put("password", user.getPassword());
		return adminUserDao.get(paramMap);
		 
	}

	@Override
	public LayUiResult findByPage(AdminUser entity, LayUiResult result) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("entity", entity);
		paramMap.put("page", result);
		int count = findByPageCount(paramMap);
		if (count > 0) {
			List<AdminUser> dataList = findByPage(paramMap);
			result.setData(dataList);
			result.setCount(count);
		}
		return result;
	}

	@Override
	public int deleteByBatch(String array) {

		return adminUserDao.deleteByBatch(array);
	}

	@Override
	public AdminUser getById(Integer id) {
		return adminUserDao.getById(id);
	}

	@Override
	public List<AdminUser> checkUnique(Map<String, Object> paramMap) {

		return adminUserDao.checkUnique(paramMap);
	}
	
	@Override
	public String checkAdmin(HttpServletRequest request) {

		AdminUser entity = SessionUtil.getSessionUser(request);
		if (null != entity) {
			if (!"admin".equals(entity.getUserName())) {
				return "您没有权限删除操作!";
			}
		}
		return null;
	}

}
