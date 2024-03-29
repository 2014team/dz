
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
import com.artcweb.bean.NailDetailConfig;
import com.artcweb.dto.NailDetailConfigDto;
import com.artcweb.service.NailDetailConfigService;
import com.artcweb.vo.NailDetailConfigVo;


@Controller
@RequestMapping("/admin/center/naildetailconfig")
public class NailDetailConfigController {

	@Autowired
	private NailDetailConfigService naildetailconfigService;
	
	

	/**
	 * @Title: toList
	 * @Description: 到列表UI
	 * @return
	 */
	@RequestMapping(value = "/list/ui")
	public String toList() {

		return "/naildetailconfig/list";
	}

	/**
	 * @Title: toAdd
	 * @Description: 到新增UI
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String toAdd(HttpServletRequest request) {

		return "/naildetailconfig/edit";
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
		NailDetailConfig entity = naildetailconfigService.get(id);
		request.setAttribute("entity", entity);
		return "/naildetailconfig/edit";
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
	public LayUiResult save(NailDetailConfigVo entity, HttpServletRequest request) {
		LayUiResult layUiResult = new LayUiResult();

		// 参数验证
		String rgb = entity.getRgb();
		if (StringUtils.isEmpty(rgb)) {
			layUiResult.failure("rgb不能为空");
			return layUiResult;
		}
		String newSerialNumber = entity.getNewSerialNumber();
		if (StringUtils.isEmpty(newSerialNumber)) {
			layUiResult.failure("新编号不能为空");
			return layUiResult;
		}
		String oldSerialNumber = entity.getOldSerialNumber();
		if (StringUtils.isEmpty(oldSerialNumber)) {
			layUiResult.failure("旧编号不能为空");
			return layUiResult;
		}
		String nailSmallWeight = entity.getNailSmallWeight();
		if (StringUtils.isEmpty(nailSmallWeight)) {
			layUiResult.failure("（小钉）每包克数 不能为空");
			return layUiResult;
		}
		String nailMiniWeight = entity.getNailMiniWeight();
		if (StringUtils.isEmpty(nailMiniWeight)) {
			layUiResult.failure("（迷你）每包克数 不能为空");
			return layUiResult;
		}
		String nailBigWeight = entity.getNailBigWeight();
		if (StringUtils.isEmpty(nailBigWeight)) {
			layUiResult.failure("（大钉）每包克数 不能为空");
			return layUiResult;
		}
	
		int sort = entity.getSort();
		Integer id = entity.getId();
		if(id == null ){
			// 新增
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("sort", sort);
			
			NailDetailConfigDto nailDetailConfigDto = naildetailconfigService.selectByMap(paramMap);
			if(null != nailDetailConfigDto){
				layUiResult.failure("排序号已经存在请更换");
				return layUiResult;
			}
		
		}else{
			// 修改
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("sort", sort);
			NailDetailConfigDto nailDetailConfigDto = naildetailconfigService.selectByMap(paramMap);
			if(null != nailDetailConfigDto){
				Integer sourceId = nailDetailConfigDto.getId();
				if(!id.equals(sourceId)){
					layUiResult.failure("排序号已经存在请更换");
					return layUiResult;
				}
			}
		}
		
		
		Integer result = naildetailconfigService.saveOrUpdate(entity);
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
	public LayUiResult list(NailDetailConfigVo entity, HttpServletRequest request) {

		// 获取参数
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer limit = Integer.valueOf(request.getParameter("limit"));
		LayUiResult result = new LayUiResult(page, limit);
		result = naildetailconfigService.findByPage(entity, result);
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
	public LayUiResult delete(NailDetailConfig entity, HttpServletRequest request) {

		LayUiResult result = new LayUiResult();
		// 获取参数
		Integer id = entity.getId();
		if (null == id) {
			result.failure("参数[id]不能为空!");
			return result;
		}

		Integer delResult = naildetailconfigService.delete(id);
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

		boolean deleteResult = naildetailconfigService.deleteByBatch(array);
		if (deleteResult) {
			result.success();
			return result;
		}
		result.failure();
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/get", method = { RequestMethod.POST,
					RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public LayUiResult get(Integer id) {
		
		LayUiResult result = new LayUiResult();
		if (null == id || id < 1) {
			result.failure("id不能为空");
			return result;
		}
		NailDetailConfig entity = naildetailconfigService.get(id);
		result.success(entity);
		return result;
	}
	
}
