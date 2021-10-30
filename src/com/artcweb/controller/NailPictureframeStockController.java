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
import com.artcweb.bean.NailPictureframeStock;
import com.artcweb.service.NailPictureframeStockHistoryService;
import com.artcweb.service.NailPictureframeStockService;
import com.artcweb.vo.NailDrawingStockHistoryVo;
import com.artcweb.vo.NailPictureframeStockHistoryVo;
import com.artcweb.vo.NailPictureframeStockVo;


/**
 * @ClassName: NailPictureframeStockController
 * @Description: 画框库存
 * @author zhuzq
 * @date 2021年07月30日 09:06:25
 */
@Controller
@RequestMapping("/admin/center/nailPictureframeStock")
public class NailPictureframeStockController {

	@Autowired
	private NailPictureframeStockService nailPictureframeStockService;
	/**
	 * @Title: toList
	 * @Description: 到列表UI
	 * @return
	 */
	@RequestMapping(value = "/list/ui")
	public String toList() {

		return "/nailPictureframeStock/list";
	}

	/**
	 * @Title: toAdd
	 * @Description: 到新增UI
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String toAdd(HttpServletRequest request) {

		return "/nailPictureframeStock/edit";
	}
	
	/**
	 * 
	* @Title: addStock
	* @Description: 库存页面
	* @author zhuzq
	* @date  2021年7月30日 下午2:04:47
	* @param request
	* @param id
	* @return
	 */
	@RequestMapping(value = "/addStock/{id}")
	public String addStock(HttpServletRequest request,@PathVariable Integer id) {
		request.setAttribute("id", id);
		return "/nailPictureframeStock/add_stock";
	}
	
	/**
	 * 
	* @Title: addStockSave
	* @Description: 库存保存
	* @author zhuzq
	* @date  2021年7月30日 下午2:05:01
	* @param entity
	* @param request
	* @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addStock/save")
	public LayUiResult addStockSave(NailPictureframeStockHistoryVo entity, HttpServletRequest request) {
		LayUiResult layUiResult = new LayUiResult();

		// 参数验证
		Integer nailPictureframeId = entity.getNailPictureframeId();
		if (null == nailPictureframeId) {
			layUiResult.failure("库存id不能为空");
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
		Integer stockNumber = entity.getStockNumber();
		if (null == stockNumber) {
			layUiResult.failure("库存量不能为空");
			return layUiResult;
		}
		

		// 保存
		Integer result = nailPictureframeStockService.saveNailPictureframeStock(entity);
		if (null != result && result > 0) {
			layUiResult.success();
		}
		else {
			layUiResult.failure();
		}
		return layUiResult;
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
		NailPictureframeStock entity = nailPictureframeStockService.get(id);
		request.setAttribute("entity", entity);
		return "/nailPictureframeStock/edit";
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
	public LayUiResult save(NailPictureframeStockVo entity, HttpServletRequest request) {
		LayUiResult layUiResult = new LayUiResult();

		// 参数验证
	    String colorName = entity.getColorName();
		if (StringUtils.isBlank(colorName)) {
			layUiResult.failure("颜色名称不能为空");
			return layUiResult;
		}
		Integer stockNumber = entity.getStockNumber();
		if (null == stockNumber) {
			layUiResult.failure("库存数量不能为空");
			return layUiResult;
		}
		Integer sort = entity.getSort();
		if (null == sort) {
			layUiResult.failure("序号不能为空");
			return layUiResult;
		}
		

		// 保存
		Integer result = nailPictureframeStockService.saveOrUpdate(entity);
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
	public LayUiResult list(NailPictureframeStockVo entity, HttpServletRequest request) {

		// 获取参数
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer limit = Integer.valueOf(request.getParameter("limit"));
		LayUiResult result = new LayUiResult(page, limit);
		result = nailPictureframeStockService.findByPage(entity, result);
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
	public LayUiResult delete(NailPictureframeStock entity, HttpServletRequest request) {

		LayUiResult result = new LayUiResult();
		// 获取参数
		Integer id = entity.getId();
		if (null == id) {
			result.failure("参数[id]不能为空!");
			return result;
		}

		Integer delResult = nailPictureframeStockService.delete(id);
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

		boolean deleteResult = nailPictureframeStockService.deleteByBatch(array);
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
		NailPictureframeStock entity = nailPictureframeStockService.get(id);
		result.success(entity);
		return result;
	}
	
	

}
