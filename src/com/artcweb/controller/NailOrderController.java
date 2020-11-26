
package com.artcweb.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.artcweb.baen.LayUiResult;
import com.artcweb.baen.NailConfig;
import com.artcweb.baen.NailCount;
import com.artcweb.baen.NailOrder;
import com.artcweb.baen.Order;
import com.artcweb.constant.UploadConstant;
import com.artcweb.service.ImageService;
import com.artcweb.service.NailConfigService;
import com.artcweb.service.NailDetailConfigService;
import com.artcweb.service.NailOrderService;
import com.artcweb.vo.NailOrderVo;


@Controller
@RequestMapping("/admin/center/nailorder")
public class NailOrderController {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(NailOrderController.class);
	@Autowired
	private NailOrderService nailOrderService;
	@Autowired
	private NailConfigService nailconfigService;
	@Autowired
	private ImageService imageService;

	/**
	 * @Title: toList
	 * @Description: 到列表UI
	 * @return
	 */
	@RequestMapping(value = "/list/ui")
	public String toList(HttpServletRequest request) {
		return "/nailorder/list";
	}

	/**
	 * @Title: toAdd
	 * @Description: 到新增UI
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String toAdd(HttpServletRequest request) {

		// 获取图片类型
		Map<String ,Object> paramMap = null;
		List<NailConfig> nailconfigList = nailconfigService.select(paramMap);
		request.setAttribute("nailconfigList", nailconfigList);
		
		return "/nailorder/edit";
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
		NailOrder entity = nailOrderService.get(id);
		request.setAttribute("entity", entity);
		return "/nailorder/edit";
	}
	

	/**
	 * @Title: save
	 * @Description: 保存
	 * @param adminCate
	 * @param operator
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "/save")
	public LayUiResult save(NailOrderVo entity, MultipartFile file,HttpServletRequest request) throws IOException {
		LayUiResult layUiResult = new LayUiResult();
		// 参数验证
		
		String nailconfigId = entity.getNailConfigId();
		if (StringUtils.isEmpty(nailconfigId)) {
			layUiResult.failure("图片类型不能为空");
			return layUiResult;
		}
		String mobile = entity.getMobile();
		if (StringUtils.isEmpty(mobile)) {
			layUiResult.failure("手机号不能为空");
			return layUiResult;
		}
		
		if(null == file || file.isEmpty() ){
			layUiResult.failure("图片不能为空");
			return layUiResult;
		}
		
		
		String username = file.getOriginalFilename();
		logger.info("username = "+username);
		entity.setUsername(username);
		
		// 上传图片
		if(null != file && !file.isEmpty()){
			// 图片验证
			String errorMsg = imageService.checkImage(file);
			if (StringUtils.isNotBlank(errorMsg)) {
				layUiResult.failure(errorMsg);
				return layUiResult;
			}
			// 图片颜色统计
			ConcurrentHashMap<String, Integer> nailColorMap = nailOrderService.uploadImage(request,file,entity,UploadConstant.SAVE_UPLOAD_NAIL_PATH);
	
			// 钉子统计
			nailOrderService.nailCount(nailColorMap,entity);
		
		}
		
		
		// 保存
		boolean result = nailOrderService.saveNailOrder(entity);
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
	public LayUiResult list(NailOrderVo entity, HttpServletRequest request) {

		// 获取参数
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer limit = Integer.valueOf(request.getParameter("limit"));
		LayUiResult result = new LayUiResult(page, limit);
		result = nailOrderService.findByPage(entity, result);
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
	public LayUiResult delete(Order entity, HttpServletRequest request) {

		LayUiResult result = new LayUiResult();
		// 获取参数
		Integer id = entity.getId();
		if (null == id) {
			result.failure("参数[id]不能为空!");
			return result;
		}

		Integer delResult = nailOrderService.delete(id);
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

		boolean deleteResult = nailOrderService.deleteByBatch(array);
		if (deleteResult) {
			result.success();
			return result;
		}
		result.failure();
		return result;
	}
	
}
