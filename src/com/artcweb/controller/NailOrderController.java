
package com.artcweb.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.artcweb.baen.NailPictureFrame;
import com.artcweb.constant.NailOrderComeFromConstant;
import com.artcweb.constant.UploadConstant;
import com.artcweb.service.ImageService;
import com.artcweb.service.NailConfigService;
import com.artcweb.service.NailOrderService;
import com.artcweb.service.NailPictureFrameService;
import com.artcweb.util.ExcelUtil;
import com.artcweb.util.FileUtil;
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
	private NailPictureFrameService nailPictureFrameService;
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
		
		List<NailPictureFrame> nailPictureFrameList = nailPictureFrameService.select(paramMap);
		request.setAttribute("nailPictureFrameList", nailPictureFrameList);
		
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
		NailOrder entity = nailOrderService.getById(id);
		request.setAttribute("entity", entity);
		
		// 获取图片类型
		Map<String ,Object> paramMap = null;
		List<NailConfig> nailconfigList = nailconfigService.select(paramMap);
		request.setAttribute("nailconfigList", nailconfigList);
		
		List<NailPictureFrame> nailPictureFrameList = nailPictureFrameService.select(paramMap);
		request.setAttribute("nailPictureFrameList", nailPictureFrameList);
				
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
		String username = entity.getUsername();
		if (StringUtils.isEmpty(username)) {
			layUiResult.failure("买家名称不能为空");
			return layUiResult;
		}
		
		String nailconfigId = entity.getNailConfigId();
		if (StringUtils.isEmpty(nailconfigId)) {
			layUiResult.failure("图钉类型不能为空");
			return layUiResult;
		}

		String nailPictureFrameId = entity.getNailPictureFrameId();
		if (StringUtils.isEmpty(nailPictureFrameId)) {
			layUiResult.failure("画框颜色不能为空");
			return layUiResult;
		}
		String mobile = entity.getMobile();
		if (StringUtils.isEmpty(mobile)) {
			layUiResult.failure("手机号不能为空");
			return layUiResult;
		
		}
		String imageName = entity.getImageName();
		if (StringUtils.isEmpty(imageName)) {
			layUiResult.failure("图纸名称不能为空");
			return layUiResult;
		}
		
		if(null == file || file.isEmpty() ){
			layUiResult.failure("图片不能为空");
			return layUiResult;
		}
		
		// 名称唯一性验证
		Map<String,Object> paramMap  = new  HashMap<String, Object>();
		paramMap.put("imageName", imageName);
		boolean checkExist = nailOrderService.checkExist(paramMap,null);
		if(checkExist){// 没有引用删除
			layUiResult.failure("图纸名称系统已存在");
			return layUiResult;
		}
		
		// 来源设置
		entity.setComefrom(String.valueOf(NailOrderComeFromConstant.BACKSTAGE));
		entity.setCurrentStep("");
		
		
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
	
			// 钉子颜色列表统计
			ConcurrentHashMap<String, NailCount> nailCountMap = nailOrderService.nailCount(nailColorMap,entity);
			
			// 钉子与重量总数统计
			nailOrderService.nailTotalCount(nailCountMap,entity);
		
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
	* @Title: update
	* @Description: 更新
	* @param entity
	* @param file
	* @param request
	* @return
	* @throws IOException
	*/
	@ResponseBody
	@RequestMapping(value = "/update")
	public LayUiResult update(NailOrderVo entity, MultipartFile file,HttpServletRequest request) throws IOException {
		LayUiResult layUiResult = new LayUiResult();
		// 参数验证
		Integer id = entity.getId();
		if (null ==id || id < 1) {
			layUiResult.failure("id不能为空");
			return layUiResult;
		}
		
		// 参数验证
		String username = entity.getUsername();
		if (StringUtils.isEmpty(username)) {
			layUiResult.failure("买家名称不能为空");
			return layUiResult;
		}
		
		String nailConfigId = entity.getNailConfigId();
		if (StringUtils.isEmpty(nailConfigId)) {
			layUiResult.failure("图钉类型不能为空");
			return layUiResult;
		}

		String nailPictureFrameId = entity.getNailPictureFrameId();
		if (StringUtils.isEmpty(nailPictureFrameId)) {
			layUiResult.failure("画框颜色不能为空");
			return layUiResult;
		}
		String mobile = entity.getMobile();
		if (StringUtils.isEmpty(mobile)) {
			layUiResult.failure("手机号不能为空");
			return layUiResult;
		
		}
		String imageName = entity.getImageName();
		if (StringUtils.isEmpty(imageName)) {
			layUiResult.failure("图纸名称不能为空");
			return layUiResult;
		}
		
		if((null == file || file.isEmpty()) && StringUtils.isEmpty(imageName) ){
			layUiResult.failure("图片不能为空");
			return layUiResult;
		}
		
		// 名称唯一性验证
		Map<String,Object> paramMap  = new  HashMap<String, Object>();
		paramMap.put("imageName", imageName);
		boolean checkExist = nailOrderService.checkExist(paramMap,String.valueOf(id));
		if(checkExist){// 没有引用删除
			layUiResult.failure("图纸名称系统已存在");
			return layUiResult;
		}
		
		
		NailOrder nailOrder = nailOrderService.get(id);
		if(null == nailOrder){
			layUiResult.failure("数据不存在");
			return layUiResult;
		}
		
		
		// 买家名称
		nailOrder.setUsername(username);
		// 钉子类型
		nailOrder.setNailConfigId(nailConfigId);
		// 画框颜色
		nailOrder.setNailPictureFrameId(nailPictureFrameId);
		// 手机号码
		nailOrder.setMobile(mobile);
		// 图纸名称
		nailOrder.setImageName(imageName);
		
		
		String sourceImageUrl = nailOrder.getImageUrl();
		
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
	
			// 钉子颜色列表统计
			ConcurrentHashMap<String, NailCount> nailCountMap = nailOrderService.nailCount(nailColorMap,entity);
			
			// 钉子与重量总数统计
			nailOrderService.nailTotalCount(nailCountMap,entity);
			
		
			// 设置高和宽
			nailOrder.setHeight(entity.getHeight());
			nailOrder.setWidth(entity.getWidth());
			// 设置执行步骤
			nailOrder.setStep(entity.getStep());
			// 设置上传图片
			nailOrder.setImageUrl(entity.getImageUrl());
			nailOrder.setNailCountDetail(entity.getNailCountDetail());
		
		}
		
		
		// 保存
		Integer result = nailOrderService.update(nailOrder);
		if (null != result && result > 0) {
			if(StringUtils.isNotBlank(sourceImageUrl)){
				
				// 判断是否有其他数据引用图片
				Map<String,Object> tparamMap  = new  HashMap<String, Object>();
				tparamMap.put("imageUrl", sourceImageUrl);
				boolean tcheckExist = nailOrderService.checkExist(tparamMap,String.valueOf(id));
				if(tcheckExist){// 没有引用删除
					boolean  deleteResult = FileUtil.deleteFile(sourceImageUrl,request);
					logger.info("物理删除图片结果 = "+deleteResult);
				}
			}
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
	public LayUiResult delete(NailOrder entity, HttpServletRequest request) {

		LayUiResult result = new LayUiResult();
		// 获取参数
		Integer id = entity.getId();
		if (null == id) {
			result.failure("参数[id]不能为空!");
			return result;
		}
		
		NailOrder e = nailOrderService.get(id);
		if(null == e){
			result.failure("系统没有查询要删除数据");
			return result;
		}
		String sourceImageUrl =e.getImageUrl();

		Integer delResult = nailOrderService.delete(id);
		if (null != delResult && delResult > 0) {
			result.success();
			
			if(StringUtils.isNotEmpty(sourceImageUrl)){
				// 判断是否有其他数据引用图片
				Map<String,Object> tparamMap  = new  HashMap<String, Object>();
				tparamMap.put("imageUrl", sourceImageUrl);
				boolean checkExist = nailOrderService.checkExist(tparamMap,String.valueOf(id));
				if(checkExist){// 没有引用删除
					boolean  deleteResult = FileUtil.deleteFile(sourceImageUrl,request);
					logger.info("物理删除图片结果 = "+deleteResult);
				}
				
			}
			
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

		boolean deleteResult = nailOrderService.deleteByBatch(array,request);
		if (deleteResult) {
			result.success();
			return result;
		}
		result.failure();
		return result;
	}
	
	
	@RequestMapping("/export")
	public void printing(HttpServletResponse response,HttpServletRequest request) {
		List<NailOrder> nailOrderList = nailOrderService.select(new HashMap<String, Object>());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = sdf.format(new Date());
	    ExcelUtil.writeExcel(response, fileName, ExcelUtil.exportPictureCode(nailOrderList));
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
		NailOrder entity = nailOrderService.get(id);
		result.success(entity);
		return result;
	}
	
	
}
