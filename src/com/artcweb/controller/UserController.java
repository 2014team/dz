
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

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.Order;
import com.artcweb.bean.PicPackage;
import com.artcweb.bean.User;
import com.artcweb.constant.ComeFromConstant;
import com.artcweb.service.OrderService;
import com.artcweb.service.PicPackageService;
import com.artcweb.service.UserService;

@Controller
@RequestMapping("/admin/center/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private PicPackageService picPackageService;
	
	@Autowired
	private OrderService orderService;

	/**
	 * @Title: toList
	 * @Description: 到列表UI
	 * @return
	 */
	@RequestMapping(value = "/list/ui")
	public String toList() {

		return "/user/user";
	}

	/**
	 * @Title: toAdd
	 * @Description: 到新增UI
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String toAdd() {

		return "/user/user_edit";
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

		User entity = userService.get(id);
		request.setAttribute("entity", entity);
		return "/user/user_edit";
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
	public LayUiResult save(User entity) {

		LayUiResult result = new LayUiResult();

		// 参数验证
		String checkResult = userService.checkSaveParam(entity);
		if (StringUtils.isNotBlank(checkResult)) {
			result.failure(checkResult);
			return result;
		}
		
		String userName = entity.getUserName();
		entity.setUserName(StringUtils.trim(userName));
		
		
		Integer operator = null;
		Integer id = entity.getId();

		// 修改
		if (null != id) {
			// 验证唯一性
			String checkUpdateUnique = userService.checkUpdateUnique(entity);
			if (StringUtils.isNotBlank(checkUpdateUnique)) {
				result.failure(checkUpdateUnique);
				return result;
			}
			operator = userService.updateUser(entity);
			
		}
		else {// 保存
			// 验证唯一性
			String checkAddUnique = userService.checkAddUnique(entity);
			if (StringUtils.isNotBlank(checkAddUnique)) {
				result.failure(checkAddUnique);
				return result;
			}
			operator = userService.save(entity);
		}

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
	public LayUiResult list(User entity, HttpServletRequest request) {

		// 获取参数
		String pageStr = StringUtils.defaultIfBlank(request.getParameter("page"), "1");
		Integer page = Integer.valueOf(pageStr);
		String limitStr = StringUtils.defaultIfBlank(request.getParameter("limit"), "1");
		Integer limit = Integer.valueOf(limitStr);
		LayUiResult result = new LayUiResult(page, limit);
		result = userService.findByPage(entity, result);
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
	public LayUiResult delete(PicPackage entity,HttpServletRequest request) {

		LayUiResult result = new LayUiResult();
		// 获取参数
		Integer id = entity.getId();
		if (null == id) {
			result.failure("参数[id]不能为空!");
			return result;
		}
		
		return  userService.deleteUser(id,request);
		
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
	public LayUiResult deleteBatch(String array,HttpServletRequest request) {

		LayUiResult result = new LayUiResult();
		if (StringUtils.isBlank(array)) {
			result.failure();
			return result;
		}

		array = array.replace("[", "").replace("]", "");

		boolean deleteResult = userService.deleteByBatch(array, request);
		if (deleteResult) {
			result.success();
			return result;
		}
		result.failure();
		return result;
	}

	/**
	* @Title: userOrderEdit
	* @Description: 用户列表添加套餐到列表
	* @param id
	* @param request
	* @return
	*/
	@RequestMapping(value = "/add/order/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public String userOrderEdit(@PathVariable Integer id, HttpServletRequest request) {

		// 获取用户信息
		User user = userService.get(id);
		request.setAttribute("user", user);
		
		// 获取套餐信息
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("comeFrom", ComeFromConstant.TEMPLATE);
		List<PicPackage> packageList = picPackageService.selectByMap(paramMap);
		request.setAttribute("packageList", packageList);
		
		
		//获取图片名称
		String packageName = userService.getPackageName(id,user);
		request.setAttribute("packageName", packageName);
		
		return "/user/user_order_edit";
	}

	
	/**
	* @Title: orderList
	* @Description: 用户列表编辑套餐
	* @param id
	* @param request
	* @return
	*/
	@RequestMapping(value = "/order/edit/{id}", method = { RequestMethod.POST,
					RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public String orderList(@PathVariable Integer id,HttpServletRequest request) {
		
		
		// 获取订单信息
		Map<String,Object> paramMap = new HashMap<String, Object>();
		Order order = null;
		if(null != id && id > 0){
			paramMap.put("id", id);
			order = orderService.getByMap(paramMap);
			if(null != order){
				// 获取用户信息
				paramMap.clear();
				paramMap.put("userName", order.getUserName());
				User user = userService.getByMap(paramMap);
				request.setAttribute("user", user);
			}
		}
		
		//获取字典
		paramMap.clear();
		paramMap.put("comeFrom", ComeFromConstant.TEMPLATE);
		List<PicPackage>  packageList = picPackageService.selectByMap(paramMap);
				
		
						
		request.setAttribute("packageList", packageList);
		request.setAttribute("order", order);
		//为了区别点击增加买家
		request.setAttribute("idParam", id);
		return "/user/user_order_edit";
	}

}
