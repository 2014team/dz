
package com.artcweb.controller;

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

import com.artcweb.baen.AdminCategory;
import com.artcweb.baen.AdminRight;
import com.artcweb.baen.LayUiResult;
import com.artcweb.service.AdminCategoryService;
import com.artcweb.service.AdminRightService;

/**
* @ClassName: AdminRightController
* @Description: 权限管理
*/
@Controller
@RequestMapping("/admin/center/system/right")
public class AdminRightController {

	@Autowired
	private AdminRightService adminRightService;
	
	@Autowired
	private AdminCategoryService adminCategoryService;
	/**
	 * @Title: toList
	 * @Description: 列表UI
	 * @return
	 */
	@RequestMapping(value = "/list/ui")
	public String toList() {

		return "/system/admin_right";
	}

	/**
	 * @Title: toAdd
	 * @Description: 新增UI
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String toAdd(HttpServletRequest request) {
		
		Map<String,Object> paramMap = null;
		List<AdminCategory> categoryList = adminCategoryService.select(paramMap);
		request.setAttribute("categoryList", categoryList);
		return "/system/admin_right_edit";
	}

	/**
	 * @Title: toEdit
	 * @Description: 编辑UI
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}")
	public String toEdit(@PathVariable Integer id, HttpServletRequest request) {

		
		AdminRight AdminRight = adminRightService.get(id);
		request.setAttribute("entity", AdminRight);
		
		Map<String,Object> paramMap = null;
		List<AdminCategory> categoryList = adminCategoryService.select(paramMap);
		request.setAttribute("categoryList", categoryList);
		
		return "/system/admin_right_edit";
	}

	/**
	 * @Title: save
	 * @Description: 保存
	 * @param adminCate
	 * @param operator
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save")
	public LayUiResult save(AdminRight adminCate,String rightRulexx) {

		LayUiResult result = new LayUiResult();

		// 参数验证
		String checkResult = adminRightService.checkSaveParam(adminCate);
		if (StringUtils.isNotBlank(checkResult)) {
			result.failure(checkResult);
			return result;
		}

		Integer operator = null;
		Integer id = adminCate.getId();

//		// 修改
//		if (null != id) {
//			// 验证唯一性
//			String checkUpdateUnique = adminRightService.checkUpdateUnique(adminCate);
//			if (StringUtils.isNotBlank(checkUpdateUnique)) {
//				result.failure(checkUpdateUnique);
//				return result;
//			}
//			operator = adminRightService.update(adminCate);
//			// 保存
//		}
//		else {
//			// 验证唯一性
//			String checkAddUnique = adminRightService.checkAddUnique(adminCate);
//			if (StringUtils.isNotBlank(checkAddUnique)) {
//				result.failure(checkAddUnique);
//				return result;
//			}
			operator = adminRightService.save(adminCate);
//		}

		if (null != operator && operator > 0) {
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
	@RequestMapping(value = "/list", method = { RequestMethod.POST,
					RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public LayUiResult list(AdminRight adminCate, HttpServletRequest request) {

		// 获取参数
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer limit = Integer.valueOf(request.getParameter("limit"));
		LayUiResult result = new LayUiResult(page, limit);
		result = adminRightService.findByPage(adminCate, result);
		return result;
	}

	/**
	 * @Title: delete
	 * @Description: 删除
	 * @param adminCate
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = { RequestMethod.POST,
					RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public LayUiResult delete(AdminRight adminCate) {

		LayUiResult result = new LayUiResult();
		// 获取参数
		Integer id = adminCate.getId();
		int delResult = adminRightService.delete(id);
		if (delResult > 0) {
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
	public LayUiResult deleteBatch(String array) {

		LayUiResult result = new LayUiResult();
		if (StringUtils.isBlank(array)) {
			result.failure();
			return result;
		}

		array = array.replace("[", "").replace("]", "");

		int deleteResult = adminRightService.deleteByBatch(array);
		if (deleteResult > 0) {
			result.success();
			return result;
		}
		result.failure();
		return result;
	}

}
