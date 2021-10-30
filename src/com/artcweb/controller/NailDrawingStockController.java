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
import com.artcweb.bean.NailDrawingStock;
import com.artcweb.service.NailDrawingStockService;
import com.artcweb.service.NailPictureFrameService;
import com.artcweb.vo.NailDrawingStockHistoryVo;
import com.artcweb.vo.NailDrawingStockVo;
import com.artcweb.vo.NailWeightStockHistoryVo;


/**
 * @ClassName: NailDrawingStockController
 * @Description: 图纸库存
 * @author zhuzq
 * @date 2021年03月09日 11:49:59
 */
@Controller
@RequestMapping("/admin/center/nailDrawingStock")
public class NailDrawingStockController {

	@Autowired
	private NailDrawingStockService nailDrawingStockService;
	@Autowired
	private NailPictureFrameService nailPictureFrameService;

	/**
	 * @Title: toList
	 * @Description: 到列表UI
	 * @return
	 */
	@RequestMapping(value = "/list/ui")
	public String toList() {

		return "/nailDrawingStock/list";
	}

	/**
	 * @Title: toAdd
	 * @Description: 到新增UI
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String toAdd(HttpServletRequest request) {

		return "/nailDrawingStock/edit";
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
		request.setAttribute("id", id);
		return "/nailDrawingStock/add_stock";
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
		NailDrawingStock entity = nailDrawingStockService.get(id);
		request.setAttribute("entity", entity);
		return "/nailDrawingStock/edit";
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
	public LayUiResult save(NailDrawingStockVo entity, HttpServletRequest request) {
		LayUiResult layUiResult = new LayUiResult();

		// 参数验证
	    String style = entity.getStyle();
		if (StringUtils.isBlank(style)) {
			layUiResult.failure("款式不能为空");
			return layUiResult;
		}
	    String printSize = entity.getPrintSize();
		if (StringUtils.isBlank(printSize)) {
			layUiResult.failure("打印尺寸（小）不能为空");
			return layUiResult;
		}
	    String frameSize = entity.getFrameSize();
		if (StringUtils.isBlank(frameSize)) {
			layUiResult.failure("画框尺寸不能为空");
			return layUiResult;
		}
		Integer status = entity.getStatus();
		if (null == status) {
			layUiResult.failure("是否可用0:可以1：不可以不能为空");
			return layUiResult;
		}
		Integer number = entity.getNumber();
		if (null == number) {
			layUiResult.failure("数量不能为空");
			return layUiResult;
		}
		

		// 保存秘钥
		Integer result = nailDrawingStockService.saveOrUpdate(entity);
		if (null != result && result > 0) {
			layUiResult.success();
		}
		else {
			layUiResult.failure();
		}
		return layUiResult;
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
	public LayUiResult addStockSave(NailDrawingStockHistoryVo entity, HttpServletRequest request) {
		LayUiResult layUiResult = new LayUiResult();
		

		// 参数验证
		Integer nailDrawingStockId = entity.getNailDrawingStockId();
		if (null == nailDrawingStockId) {
			layUiResult.failure("图纸库存ID不能为空");
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
		Integer result = nailDrawingStockService.saveStock(entity);
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
	public LayUiResult list(NailDrawingStockVo entity, HttpServletRequest request) {

		// 获取参数
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer limit = Integer.valueOf(request.getParameter("limit"));
		LayUiResult result = new LayUiResult(page, limit);
		result = nailDrawingStockService.findByPage(entity, result);
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
	public LayUiResult delete(NailDrawingStock entity, HttpServletRequest request) {

		LayUiResult result = new LayUiResult();
		// 获取参数
		Integer id = entity.getId();
		if (null == id) {
			result.failure("参数[id]不能为空!");
			return result;
		}

		Integer delResult = nailDrawingStockService.delete(id);
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

		boolean deleteResult = nailDrawingStockService.deleteByBatch(array);
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
		NailDrawingStock entity = nailDrawingStockService.get(id);
		result.success(entity);
		return result;
	}
	
	

}
