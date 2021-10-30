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
import com.artcweb.bean.NailWeightStockHistory;
import com.artcweb.service.NailConfigService;
import com.artcweb.service.NailWeightStockHistoryService;
import com.artcweb.service.NailWeightStockService;
import com.artcweb.vo.NailWeightStockHistoryVo;


/**
 * @ClassName: NailWeightStockHistoryController
 * @Description: 图钉重量库存记录
 * @author zhuzq
 * @date 2021年03月09日 14:16:31
 */
@Controller
@RequestMapping("/admin/center/nailWeightStockHistory")
public class NailWeightStockHistoryController {

	@Autowired
	private NailWeightStockHistoryService nailWeightStockHistoryService;
	/**
	 * @Title: toList
	 * @Description: 到列表UI
	 * @return
	 */
	@RequestMapping(value = "/list/ui")
	public String toList() {

		return "/nailWeightStockHistory/list";
	}

	/**
	 * @Title: toAdd
	 * @Description: 到新增UI
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String toAdd(HttpServletRequest request) {

		return "/nailWeightStockHistory/edit";
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
		NailWeightStockHistory entity = nailWeightStockHistoryService.get(id);
		request.setAttribute("entity", entity);
		return "/nailWeightStockHistory/edit";
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
	public LayUiResult save(NailWeightStockHistoryVo entity, HttpServletRequest request) {
		LayUiResult layUiResult = new LayUiResult();

		// 参数验证
		Integer nailWeightStockId = entity.getNailWeightStockId();
		if (null == nailWeightStockId) {
			layUiResult.failure("重量库存id不能为空");
			return layUiResult;
		}
	    String stock = entity.getStock();
		if (StringUtils.isBlank(stock)) {
			layUiResult.failure("库存量不能为空");
			return layUiResult;
		}
	    String price = entity.getPrice();
		if (StringUtils.isBlank(price)) {
			layUiResult.failure("单价不能为空");
			return layUiResult;
		}
	    String total = entity.getTotal();
		if (StringUtils.isBlank(total)) {
			layUiResult.failure("总价不能为空");
			return layUiResult;
		}
		

		// 保存秘钥
		Integer result = nailWeightStockHistoryService.saveOrUpdate(entity);
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
	public LayUiResult list(NailWeightStockHistoryVo entity, HttpServletRequest request) {

		// 获取参数
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer limit = Integer.valueOf(request.getParameter("limit"));
		LayUiResult result = new LayUiResult(page, limit);
		result = nailWeightStockHistoryService.findByPage(entity, result);
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
	public LayUiResult delete(NailWeightStockHistory entity, HttpServletRequest request) {

		LayUiResult result = new LayUiResult();
		// 获取参数
		Integer id = entity.getId();
		if (null == id) {
			result.failure("参数[id]不能为空!");
			return result;
		}

		Integer delResult = nailWeightStockHistoryService.delete(id);
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

		boolean deleteResult = nailWeightStockHistoryService.deleteByBatch(array);
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
		NailWeightStockHistory entity = nailWeightStockHistoryService.get(id);
		result.success(entity);
		return result;
	}
	
	

}
