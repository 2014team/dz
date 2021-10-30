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

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.NailConfig;
import com.artcweb.bean.NailWeightStock;
import com.artcweb.service.NailConfigService;
import com.artcweb.service.NailWeightStockService;
import com.artcweb.vo.NailWeightStockHistoryVo;
import com.artcweb.vo.NailWeightStockVo;


/**
 * @ClassName: NailWeightStockController
 * @Description: 图钉重量库存
 * @author zhuzq
 * @date 2021年03月09日 10:04:19
 */
@Controller
@RequestMapping("/admin/center/nailWeightStock")
public class NailWeightStockController {

	@Autowired
	private NailWeightStockService nailWeightStockService;
	
	@Autowired
	private NailConfigService nailconfigService;

	/**
	 * @Title: toList
	 * @Description: 到列表UI
	 * @return
	 */
	@RequestMapping(value = "/list/ui")
	public String toList() {

		return "/nailWeightStock/list";
	}

	/**
	 * @Title: toAdd
	 * @Description: 到新增UI
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String toAdd(HttpServletRequest request) {

		return "/nailWeightStock/edit";
	}
	
	/**
	* @Title: addStock
	* @Description: 添加库存
	* @author zhuzq
	* @date  2021年3月9日 下午2:35:08
	* @param request
	* @return
	*/
	@RequestMapping(value = "/addStock/{id}")
	public String addStock(HttpServletRequest request,@PathVariable Integer id) {
		
		
		// 获取图钉类型
		Map<String ,Object> paramMap = null;
		List<NailConfig> nailconfigList = nailconfigService.select(paramMap);
		request.setAttribute("nailconfigList", nailconfigList);
		request.setAttribute("id", id);
		return "/nailWeightStock/add_stock";
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
		NailWeightStock entity = nailWeightStockService.get(id);
		request.setAttribute("entity", entity);
		return "/nailWeightStock/edit";
	}
	
	
	/**
	* @Title: addStockSave
	* @Description: 库存保存
	* @author zhuzq
	* @date  2021年3月9日 下午2:43:32
	* @param entity
	* @param request
	* @return
	*/
	@ResponseBody
	@RequestMapping(value = "/addStock/save")
	public LayUiResult addStockSave(NailWeightStockHistoryVo entity, HttpServletRequest request) {
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
		

		// 保存
		Integer result = nailWeightStockService.saveStock(entity);
		if (null != result && result > 0) {
			layUiResult.success();
		}
		else {
			layUiResult.failure();
		}
		return layUiResult;
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
	public LayUiResult save(NailWeightStockVo entity, HttpServletRequest request) {
		LayUiResult layUiResult = new LayUiResult();

		// 参数验证
	    String rgb = entity.getRgb();
		if (StringUtils.isBlank(rgb)) {
			layUiResult.failure("RGB值不能为空");
			return layUiResult;
		}
	    String newSerialNumber = entity.getNewSerialNumber();
		if (StringUtils.isBlank(newSerialNumber)) {
			layUiResult.failure("新编号不能为空");
			return layUiResult;
		}
	    String oldSerialNumber = entity.getOldSerialNumber();
		if (StringUtils.isBlank(oldSerialNumber)) {
			layUiResult.failure("旧编号不能为空");
			return layUiResult;
		}
	    String stock_1 = entity.getStock_1();
		if (StringUtils.isBlank(stock_1)) {
			layUiResult.failure("小钉库存(单位千克)不能为空");
			return layUiResult;
		}
		String stock_2 = entity.getStock_2();
		if (StringUtils.isBlank(stock_2)) {
			layUiResult.failure("玫瑰库存(单位千克)不能为空");
			return layUiResult;
		}
		String stock_3 = entity.getStock_3();
		if (StringUtils.isBlank(stock_3)) {
			layUiResult.failure("钻石库存(单位千克)不能为空");
			return layUiResult;
		}
		String stock_4 = entity.getStock_4();
		if (StringUtils.isBlank(stock_4)) {
			layUiResult.failure("大钉库存(单位千克)不能为空");
			return layUiResult;
		}
		Integer sort = entity.getSort();
		if (null == sort) {
			layUiResult.failure("排序不能为空");
			return layUiResult;
		}
		

		// 保存秘钥
		Integer result = nailWeightStockService.saveOrUpdate(entity);
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
	public LayUiResult list(NailWeightStockVo entity, HttpServletRequest request) {

		// 获取参数
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer limit = Integer.valueOf(request.getParameter("limit"));
		LayUiResult result = new LayUiResult(page, limit);
		result = nailWeightStockService.findByPage(entity, result);
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
	public LayUiResult delete(NailWeightStock entity, HttpServletRequest request) {

		LayUiResult result = new LayUiResult();
		// 获取参数
		Integer id = entity.getId();
		if (null == id) {
			result.failure("参数[id]不能为空!");
			return result;
		}

		Integer delResult = nailWeightStockService.delete(id);
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

		boolean deleteResult = nailWeightStockService.deleteByBatch(array);
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
		NailWeightStock entity = nailWeightStockService.get(id);
		result.success(entity);
		return result;
	}
	
	

}
