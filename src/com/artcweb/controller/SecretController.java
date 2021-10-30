
package com.artcweb.controller;

import java.util.HashMap;
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
import com.artcweb.bean.Secret;
import com.artcweb.dto.SecretDto;
import com.artcweb.service.SecretService;
import com.artcweb.vo.SecretVo;

/**
 * @ClassName: SecretController
 * @Description: 秘钥controller
 */
@Controller
@RequestMapping("/admin/center/secret")
public class SecretController {

	@Autowired
	private SecretService secretService;

	/**
	 * @Title: toList
	 * @Description: 到列表UI
	 * @return
	 */
	@RequestMapping(value = "/list/ui")
	public String toList() {

		return "/secret/list";
	}

	/**
	 * @Title: toAdd
	 * @Description: 到新增UI
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String toAdd(HttpServletRequest request) {

		return "/secret/edit";
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
	public LayUiResult save(SecretVo entity, HttpServletRequest request) {
		LayUiResult layUiResult = new LayUiResult();

		// 参数验证
		Integer secretNumber = entity.getSecretNumber();
		if (null == secretNumber || secretNumber < 1) {
			layUiResult.failure("生成秘钥数量不能为空");
			return layUiResult;
		}
		Integer secretDigit = entity.getSecretDigit();
		if (null == secretDigit || secretDigit < 1) {
			layUiResult.failure("秘钥长度不能为空");
			return layUiResult;
		}

		// 保存秘钥
		boolean result = secretService.saveSecret(entity);
		if (result) {
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
	public LayUiResult list(SecretVo entity, HttpServletRequest request) {

		// 获取参数
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer limit = Integer.valueOf(request.getParameter("limit"));
		LayUiResult result = new LayUiResult(page, limit);
		result = secretService.findByPage(entity, result);
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
	public LayUiResult delete(Secret entity, HttpServletRequest request) {

		LayUiResult result = new LayUiResult();
		// 获取参数
		Integer id = entity.getId();
		if (null == id) {
			result.failure("参数[id]不能为空!");
			return result;
		}

		Integer delResult = secretService.delete(id);
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

		boolean deleteResult = secretService.deleteByBatch(array);
		if (deleteResult) {
			result.success();
			return result;
		}
		result.failure();
		return result;
	}
	
	/**
	* @Title: toEdit
	* @Description: 获取详情
	* @param siteName
	* @param id
	* @param request
	* @return
	*/
	@RequestMapping(value = "/detail/{siteName}/{id}")
	public String toEdit(@PathVariable("siteName") String siteName,@PathVariable("id") Integer id, HttpServletRequest request) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("siteName", siteName);
		paramMap.put("id", id);
		SecretDto entity = secretService.detail(paramMap);
		request.setAttribute("entity", entity);
		return "/secret/detail";
	}
	

}
