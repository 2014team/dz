
package com.artcweb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artcweb.baen.AdminUser;
import com.artcweb.baen.JsonResult;
import com.artcweb.baen.LayUiResult;
import com.artcweb.service.AdminUserService;
import com.artcweb.util.SessionUtil;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

	@Autowired
	private AdminUserService adminUserService;

	/**
	 * @Title: login
	 * @Description: 登录界面
	 * @return
	 */
	@RequestMapping("/login")
	public String login() {

		return "login";
	}

	/**
	 * @Title: logout
	 * @Description: 退出
	 * @param request
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {

		// 删除用户session信息
		SessionUtil.deleteSessionUser(request);
		return "redirect:/admin/login";
	}

	/**
	 * @Title: loginSubmit
	 * @Description: 登录提交
	 * @param user
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/login/submit")
	public JsonResult loginSubmit(AdminUser user, HttpServletRequest request) {

		JsonResult jsonResult = new JsonResult();
		// 基本参数验证
		String errorMsg = adminUserService.checkLoginParam(user);
		if (StringUtils.isNotBlank(errorMsg)) {
			jsonResult.failure(errorMsg);
			return jsonResult;
		}

		// 登录
		AdminUser userResult = adminUserService.login(user);
		if (null == userResult) {
			jsonResult.failure("请认真核对账号、密码！");
			return jsonResult;
		}

		// 保存用户session信息
		SessionUtil.saveSessionUser(request, userResult);

		jsonResult.success("登录成功！");
		return jsonResult;
	}

	/**
	 * @Title: index
	 * @Description: 后台中心
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping("/center/index")
	public String centerIndex(HttpServletRequest request) {

		AdminUser user = SessionUtil.getSessionUser(request);
		if (null == user) {
			return "redirect:/admin/login.do";
		}
		request.setAttribute("user", user);
		return "index";
	}

	/**
	 * @Title: toList
	 * @Description: 列表UI
	 * @return
	 */
	@RequestMapping(value = "/center/account/list/ui")
	public String toList() {

		return "/admin/user";
	}

	/**
	 * @Title: toAdd
	 * @Description: 新增UI
	 * @return
	 */
	@RequestMapping(value = "/center/account/add")
	public String toAdd() {

		return "/admin/user_edit";
	}

	/**
	 * @Title: toEdit
	 * @Description: 编辑UI
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/center/account/edit/{id}")
	public String toEdit(@PathVariable Integer id, HttpServletRequest request) {

		AdminUser entity = adminUserService.getById(id);
		request.setAttribute("entity", entity);
		return "/admin/user_edit";
	}

	/**
	 * @Title: save
	 * @Description: 保存
	 * @param adminCate
	 * @param operator
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/center/account/save")
	public LayUiResult save(AdminUser adminUser, HttpServletRequest request) {

		LayUiResult result = new LayUiResult();
		String errMsg = adminUserService.checkAdmin(request);
		if (StringUtils.isNotBlank(errMsg)) {
			result.failure(errMsg);
			return result;
		}

		// 参数验证
		String userName = adminUser.getUserName();
		if (StringUtils.isBlank(userName)) {
			result.failure("用户名不能为空!");
			return result;
		}
		String password = adminUser.getPassword();
		if (StringUtils.isBlank(password)) {
			result.failure("密码不能为空!");
			return result;
		}

		Integer id = adminUser.getId();
		Integer operate = 0;
		if (null != id && id > 0) {// 修改
			AdminUser user = adminUserService.getById(id);
			if (null != user) {
				user.setUserName(userName);
				user.setPassword(password);
				operate = adminUserService.update(user);
			}
		}
		else {// 保存
			adminUser.setVaild(0);
			adminUser.setEmail("");

			// 唯一性验证
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userName", userName);
			List<AdminUser> list = adminUserService.checkUnique(paramMap);
			if (null != list && list.size() > 0) {
				result.failure("用户已存在!");
				return result;
			}

			operate = adminUserService.save(adminUser);
		}

		if (null != operate && operate > 0) {
			result.success();
			return result;
		}

		result.failure();
		return result;
	}

	/**
	 * @Title: list
	 * @Description: 列表
	 * @param adminCate
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/center/account/list", method = { RequestMethod.POST,
					RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public LayUiResult list(AdminUser entity, HttpServletRequest request) {

		// 获取参数
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer limit = Integer.valueOf(request.getParameter("limit"));
		LayUiResult result = new LayUiResult(page, limit);
		result = adminUserService.findByPage(entity, result);
		return result;
	}

	/**
	 * @Title: delete
	 * @Description: 删除
	 * @param adminCate
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/center/account/delete", method = { RequestMethod.POST,
					RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public LayUiResult delete(AdminUser entity, HttpServletRequest request) {

		LayUiResult result = new LayUiResult();
		String errMsg = adminUserService.checkAdmin(request);
		if (StringUtils.isNotBlank(errMsg)) {
			result.failure(errMsg);
			return result;
		}

		// 获取参数
		Integer id = entity.getId();
		Integer delResult = adminUserService.delete(id);
		if (null != delResult && delResult > 0) {
			result.success();
			return result;
		}

		result.failure();
		return result;
	}

	/**
	 * @Title: deleteBatch
	 * @Description: 批量删除
	 * @param array
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/center/account/delete/batch", method = { RequestMethod.POST,
					RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public LayUiResult deleteBatch(String array, HttpServletRequest request) {

		LayUiResult result = new LayUiResult();
		String errMsg = adminUserService.checkAdmin(request);
		if (StringUtils.isNotBlank(errMsg)) {
			result.failure(errMsg);
			return result;
		}

		if (StringUtils.isBlank(array)) {
			result.failure();
			return result;
		}

		array = array.replace("[", "").replace("]", "");

		int deleteResult = adminUserService.deleteByBatch(array);
		if (deleteResult > 0) {
			result.success();
			return result;
		}
		result.failure();
		return result;
	}

}
