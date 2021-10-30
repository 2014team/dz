package com.artcweb.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.Role;
import com.artcweb.service.RoleService;
import com.artcweb.vo.RoleVo;


/**
 * @ClassName: RoleController
 * @Description: 角色
 * @author zhuzq
 * @date 2021年03月08日 17:57:39
 */
@Controller
@RequestMapping("/admin/center/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	/**
	 * @Title: toList
	 * @Description: 到列表UI
	 * @return
	 */
	@RequestMapping(value = "/list/ui")
	public String toList() {

		return "/role/list";
	}

	/**
	 * @Title: toAdd
	 * @Description: 到新增UI
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String toAdd(HttpServletRequest request) {

		return "/role/edit";
	}
	
	/**
	 * @Title: toEdit
	 * @Description: 到编辑UI
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}")
	public String toEdit(@PathVariable Integer id, HttpServletRequest request) {
		Role entity = roleService.get(id);
		request.setAttribute("entity", entity);
		return "/role/edit";
	}

	/**
	 * @Title: save
	 * @Description: 保存
	 * @param entity
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping(value = "/save")
	public LayUiResult save(RoleVo entity, HttpServletRequest request) {
		LayUiResult layUiResult = new LayUiResult();

		// 参数验证
	    String roleName = entity.getRoleName();
		if (StringUtils.isBlank(roleName)) {
			layUiResult.failure("角色名称不能为空");
			return layUiResult;
		}
	    String description = entity.getDescription();
		if (StringUtils.isBlank(description)) {
			layUiResult.failure("描述不能为空");
			return layUiResult;
		}
		

		// 保存秘钥
		Integer result = roleService.saveOrUpdate(entity);
		if (null != result && result > 0) {
			layUiResult.success();
		}
		else {
			layUiResult.failure();
		}
		return layUiResult;
	}

	/**
	 * @Title: list
	 * @Description: 列表
	 * @param adminCate
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = { RequestMethod.POST,
					RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public LayUiResult list(RoleVo entity, HttpServletRequest request) {

		// 获取参数
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer limit = Integer.valueOf(request.getParameter("limit"));
		LayUiResult result = new LayUiResult(page, limit);
		result = roleService.findByPage(entity, result);
		return result;
	}

	/**
	 * @Title: delete
	 * @Description: 删除
	 * @param delete
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = { RequestMethod.POST,
					RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public LayUiResult delete(Role entity, HttpServletRequest request) {

		LayUiResult result = new LayUiResult();
		// 获取参数
		Integer id = entity.getId();
		if (null == id) {
			result.failure("参数[id]不能为空!");
			return result;
		}

		Integer delResult = roleService.delete(id);
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
	@RequestMapping(value = "/delete/batch", method = { RequestMethod.POST,
					RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public LayUiResult deleteBatch(String array, HttpServletRequest request) {

		LayUiResult result = new LayUiResult();
		if (StringUtils.isBlank(array)) {
			result.failure();
			return result;
		}

		array = array.replace("[", "").replace("]", "");

		boolean deleteResult = roleService.deleteByBatch(array);
		if (deleteResult) {
			result.success();
			return result;
		}
		result.failure();
		return result;
	}
	
	/**
	* @Title: get
	* @Description: 获取单个对象
	* @param id
	* @return
	*/
	@ResponseBody
	@RequestMapping(value = "/get", method = { RequestMethod.POST,
					RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public LayUiResult get(Integer id) {
		
		LayUiResult result = new LayUiResult();
		if (null == id || id < 1) {
			result.failure("id不能为空");
			return result;
		}
		Role entity = roleService.get(id);
		result.success(entity);
		return result;
	}
	
	

}
