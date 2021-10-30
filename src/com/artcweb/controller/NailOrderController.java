
package com.artcweb.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.artcweb.bean.Analys;
import com.artcweb.bean.AnalysNailConfig;
import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.NailConfig;
import com.artcweb.bean.NailCount;
import com.artcweb.bean.NailDrawingStock;
import com.artcweb.bean.NailImageSize;
import com.artcweb.bean.NailOrder;
import com.artcweb.bean.NailPictureFrame;
import com.artcweb.bean.NailPictureframeStock;
import com.artcweb.bean.NailTotalCount;
import com.artcweb.constant.NailOrderComeFromConstant;
import com.artcweb.constant.UploadConstant;
import com.artcweb.dto.NailOrderDto;
import com.artcweb.enums.CheckoutFlagEnum;
import com.artcweb.enums.ThirdFlagEnum;
import com.artcweb.service.ImageService;
import com.artcweb.service.NailConfigService;
import com.artcweb.service.NailDrawingStockService;
import com.artcweb.service.NailImageSizeService;
import com.artcweb.service.NailOrderService;
import com.artcweb.service.NailPictureFrameService;
import com.artcweb.service.NailPictureframeStockService;
import com.artcweb.util.DateUtil;
import com.artcweb.util.ExcelUtil;
import com.artcweb.util.FileUtil;
import com.artcweb.util.GsonUtil;
import com.artcweb.util.MapUtil;
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
	@Autowired
	private NailDrawingStockService nailDrawingStockService;
	@Autowired
	private NailImageSizeService nailImageSizeService;
	@Autowired
	private NailPictureframeStockService nailPictureframeStockService;
	
	

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

		// 获取图钉类型
		Map<String ,Object> paramMap = null;
		List<NailConfig> nailconfigList = nailconfigService.select(paramMap);
		request.setAttribute("nailconfigList", nailconfigList);
		
		List<NailPictureFrame> nailPictureFrameList = nailPictureFrameService.select(paramMap);
		request.setAttribute("nailPictureFrameList", nailPictureFrameList);
		
		List<NailPictureframeStock> nailPictureframeStockList = nailPictureframeStockService.select(paramMap);
		request.setAttribute("nailPictureframeStockList", nailPictureframeStockList);
		
		
		//图纸款式
		List<NailDrawingStock> nailDrawingStockServiceList = nailDrawingStockService.select(paramMap);
		request.setAttribute("nailDrawingStockServiceList", nailDrawingStockServiceList);
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
		NailOrderDto entity = nailOrderService.getById(id);
		request.setAttribute("entity", entity);
		
		// 获取图片类型
		Map<String ,Object> paramMap = null;
		List<NailConfig> nailconfigList = nailconfigService.select(paramMap);
		request.setAttribute("nailconfigList", nailconfigList);
		
		List<NailPictureFrame> nailPictureFrameList = nailPictureFrameService.select(paramMap);
		request.setAttribute("nailPictureFrameList", nailPictureFrameList);
		
		List<NailPictureframeStock> nailPictureframeStockList = nailPictureframeStockService.select(paramMap);
		request.setAttribute("nailPictureframeStockList", nailPictureframeStockList);
		
		//图纸款式
		List<NailDrawingStock> nailDrawingStockServiceList = nailDrawingStockService.select(paramMap);
		request.setAttribute("nailDrawingStockServiceList", nailDrawingStockServiceList);
				
		return "/nailorder/edit";
	}
	
	
	
	@RequestMapping(value = "/detail/{id}")
	public String detail(@PathVariable Integer id, HttpServletRequest request) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		NailOrderDto entity = nailOrderService.getNailOrder(paramMap);
		
		
		if(StringUtils.isNotBlank(entity.getNailCountDetail())){
			NailTotalCount nailTotalCount = ( NailTotalCount ) GsonUtil.jsonToBean(entity.getNailCountDetail(),NailTotalCount.class);
			
			LinkedHashMap<String, NailCount> nailCountDetailMap= nailTotalCount.getNailCountDetailMap();
			
			nailCountDetailMap = MapUtil.mapSortForStringKey(nailCountDetailMap);
			
			if(null != nailCountDetailMap && nailCountDetailMap.size() > 0){
				// 4取模处理
				int size = (nailCountDetailMap.size()+1)%4;
				if(size != 0){
					for(int i=0;i<(4-size);i++){
						nailCountDetailMap.put(100+i+"", new NailCount());
					}
					nailTotalCount.setNailCountDetailMap(nailCountDetailMap);
				}
			}
			
			
			
			request.setAttribute("nailTotalCount", nailTotalCount);
		}
		request.setAttribute("entity", entity);
		
		return "/nailorder/detail";
	}
	
	
	/**
	* @Title: analys
	* @Description: 统计分析
	* @author zhuzq
	* @date  2021年3月12日 下午2:54:27
	* @param entity
	* @param request
	* @return
	*/
	@RequestMapping(value = "/choose/analys/{arrayId}")
	public String analys(NailOrderVo entity,@PathVariable("arrayId") String arrayId,Integer checkoutFlagX, HttpServletRequest request) {
		
		// 特殊处理
		if(null != checkoutFlagX){
			entity.setCheckoutFlag(checkoutFlagX);
		}else{
			entity.setCheckoutFlag(-1);
		}
		
		
		entity.setArray(arrayId);
		
		Map<String,Object>  datamap = nailOrderService.analys(entity);
		
		
		// 图纸统计
		Map<String,AnalysNailConfig> nailConfigMap =  null;
		// 图钉统计
		Map<String,Analys>  analysMap = null;
		if(null != datamap && datamap.size() > 0){
			nailConfigMap = (Map<String,AnalysNailConfig>) datamap.get("analysNailConfigMap");
			analysMap = (Map<String, Analys>) datamap.get("analysMap");
		}
		request.setAttribute("analysMap", analysMap);
		request.setAttribute("nailConfigMap", nailConfigMap);
		
		
		// 获取图片类型
		List<NailConfig> nailconfigList = nailconfigService.select(new HashMap<String, Object>());
		request.setAttribute("nailconfigList", nailconfigList);
		
		List<NailPictureframeStock> nailPictureframeStockList = nailPictureframeStockService.select(new HashMap<String, Object>());
		request.setAttribute("nailPictureframeStockList", nailPictureframeStockList);
		
		request.setAttribute("entity", entity);
		
		// 判断大小
		String[] idArr= null;
		if(StringUtils.isNotEmpty(entity.getArray())){
			idArr = entity.getArray().split(",");
			request.setAttribute("arrayIdSize", idArr.length);
		}
		
		return "/nailorder/analys";
	}
	
	
	@RequestMapping(value = "/analys/list")
	public String analyslist(NailOrderVo entity, Integer checkoutFlagX,HttpServletRequest request) {
		
		// 特殊处理
		if(null != checkoutFlagX){
			entity.setCheckoutFlag(checkoutFlagX);
		}else{
			entity.setCheckoutFlag(-1);
		}
		
		
		Map<String,Object>  datamap = nailOrderService.analys(entity);
		
		
		// 图纸统计
		Map<String,AnalysNailConfig> nailConfigMap =  null;
		// 图钉统计
		Map<String,Analys>  analysMap = null;
		if(null != datamap && datamap.size() > 0){
			nailConfigMap = (Map<String,AnalysNailConfig>) datamap.get("analysNailConfigMap");
			analysMap = (Map<String, Analys>) datamap.get("analysMap");
		}
		request.setAttribute("analysMap", analysMap);
		request.setAttribute("nailConfigMap", nailConfigMap);
		
		
		request.setAttribute("entity", entity);
		
		// 获取图片类型
		List<NailConfig> nailconfigList = nailconfigService.select(new HashMap<String, Object>());
		request.setAttribute("nailconfigList", nailconfigList);
		return "/nailorder/analys_list";
	}
	
	
	
	/**
	* @Title: checkout
	* @Description: 出库
	* @author zhuzq
	* @date  2021年3月10日 下午4:37:56
	* @param entity
	* @return
	*/
	@ResponseBody
	@RequestMapping(value = "/checkout")
	public LayUiResult checkout(String array, HttpServletRequest request){
		
		LayUiResult result = new LayUiResult();
		
		if (StringUtils.isBlank(array)) {
			result.failure();
			return result;
		}

		array = array.replace("[", "").replace("]", "");
		
		
		String checkOutCondition = nailOrderService.updateCheckout(array);
		if(StringUtils.isNotEmpty(checkOutCondition)){
			result.failure(checkOutCondition);
			return result;
		}
		// 更新库存
		result.success("出库成功");
		return result;
	}
	
	/**
	* @Title: cancelCheckout
	* @Description: 取消出库
	* @author zhuzq
	* @date  2021年3月11日 下午4:49:00
	* @param array
	* @param request
	* @return
	*/
	@ResponseBody
	@RequestMapping(value = "/cancel/checkout")
	public LayUiResult cancelCheckout(String array, HttpServletRequest request){
		
		LayUiResult result = new LayUiResult();
		
		if (StringUtils.isBlank(array)) {
			result.failure();
			return result;
		}
		
		array = array.replace("[", "").replace("]", "");
		
		
		String checkOutCondition = nailOrderService.updateCancelCheckout(array);
		if(StringUtils.isNotEmpty(checkOutCondition)){
			result.failure(checkOutCondition);
			return result;
		}
		// 更新库存
		result.success("退库成功");
		return result;
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
	public LayUiResult save(NailOrderVo entity, MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws IOException {
		LayUiResult layUiResult = new LayUiResult();
		
		// 参数验证
		String username = entity.getUsername();
		if (StringUtils.isEmpty(username)) {
			layUiResult.failure("买家名称不能为空");
			return layUiResult;
		}
		
		String nailconfigId = entity.getNailConfigId();
		/*if (StringUtils.isEmpty(nailconfigId)) {
			layUiResult.failure("图钉类型不能为空");
			return layUiResult;
		}*/

		String nailPictureFrameId = entity.getNailPictureFrameId();
		/*if (StringUtils.isEmpty(nailPictureFrameId)) {
			layUiResult.failure("画框颜色不能为空");
			return layUiResult;
		}*/
		String mobile = entity.getMobile();
		/*if (StringUtils.isEmpty(mobile)) {
			layUiResult.failure("手机号不能为空");
			return layUiResult;
		
		}*/
		String imageName = entity.getImageName();
		if (StringUtils.isEmpty(imageName)) {
			layUiResult.failure("图纸名称不能为空");
			return layUiResult;
		}
		
		if(null == file || file.isEmpty() ){
			layUiResult.failure("图片不能为空");
			return layUiResult;
		}
		
		// 名称唯一性验证（只验证后台数据）
		Map<String,Object> paramMap  = new  HashMap<String, Object>();
		//paramMap.put("comefrom", NailOrderComeFromConstant.BACKSTAGE);
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
			
			// 图片尺寸验证
			String checkNialImageSise = nailOrderService.checkNialImageSise(file);
			if (StringUtils.isNotBlank(checkNialImageSise)) {
				layUiResult.failure(checkNialImageSise);
				return layUiResult;
			}
			// 图片验证
			String errorMsg = imageService.checkImage(file);
			if (StringUtils.isNotBlank(errorMsg)) {
				layUiResult.failure(errorMsg);
				return layUiResult;
			}
			
//			if(StringUtils.isNotBlank(nailconfigId) && !"0".equals(nailconfigId) && StringUtils.isNotBlank(nailPictureFrameId) && !"0".equals(nailPictureFrameId)){
				//
				
				// 图片颜色统计
				LinkedHashMap<String, Integer> nailColorMap = nailOrderService.uploadImage(request,file,entity,UploadConstant.SAVE_UPLOAD_NAIL_PATH,NailOrderComeFromConstant.BACKSTAGE);
				if(null == nailColorMap){
					layUiResult.failure("图片有32种颜色以外的颜色，操作失败");
					return layUiResult;
				}
				
				// 钉子颜色列表统计
				LinkedHashMap<String, NailCount> nailCountMap = nailOrderService.nailCount(nailColorMap,entity);
				
				// 钉子与重量总数统计
				nailOrderService.nailTotalCount(nailCountMap,entity);
//			}
		
		}
		
		
		// 保存
		Integer result = nailOrderService.saveNailOrder(entity);
		if (null != result && result > 0) {
			layUiResult.success(result);
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
		/*if (StringUtils.isEmpty(nailConfigId)) {
			layUiResult.failure("图钉类型不能为空");
			return layUiResult;
		}*/

		String nailPictureFrameId = entity.getNailPictureFrameId();
		/*if (StringUtils.isEmpty(nailPictureFrameId)) {
			layUiResult.failure("画框颜色不能为空");
			return layUiResult;
		}*/
		String mobile = entity.getMobile();
		/*if (StringUtils.isEmpty(mobile)) {
			layUiResult.failure("手机号不能为空");
			return layUiResult;
		
		}*/
		String imageName = entity.getImageName();
		if (StringUtils.isEmpty(imageName)) {
			layUiResult.failure("图纸名称不能为空");
			return layUiResult;
		}
		
		if((null == file || file.isEmpty()) && StringUtils.isEmpty(imageName) ){
			layUiResult.failure("图片不能为空");
			return layUiResult;
		}
		
		// 名称唯一性验证（只验证后台数据）
		Map<String,Object> paramMap  = new  HashMap<String, Object>();
		//paramMap.put("comefrom", NailOrderComeFromConstant.BACKSTAGE);
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
		
		int checkoutFlag = nailOrder.getCheckoutFlag();
		if(String.valueOf(checkoutFlag).equals(CheckoutFlagEnum.OK.getDisplayName())){
			layUiResult.failure("选择的数据已经出库，修改失败!");
			return layUiResult;
		}
		
		
		
		String nailConfigId_s = nailOrder.getNailConfigId();
		
		
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
		// 款式
		
		String nailDrawingStockId = entity.getNailDrawingStockId();
		
		if(StringUtils.isEmpty(nailDrawingStockId)){
			nailDrawingStockId = "0";
		}
		nailOrder.setNailDrawingStockId(nailDrawingStockId);
		nailOrder.setNailPictureFrameStockId(entity.getNailPictureFrameStockId());
		
		
		String sourceImageUrl = nailOrder.getImageUrl();
		
		
		String thirdFlag = nailOrder.getThirdFlag();
		String comefrom = nailOrder.getComefrom();
		// 判断是否H5数据没有生产订单、修改订单类型也需要重新计算
		if((StringUtils.isNotBlank(thirdFlag) && 
				StringUtils.isNotBlank(comefrom) && 
				String.valueOf(comefrom).equals(NailOrderComeFromConstant.H5) &&
				String.valueOf(thirdFlag).equals(ThirdFlagEnum.OFF.getDisplayName())
			)|| !nailConfigId_s.equals(nailConfigId)
				){
			File f = new File(request.getSession().getServletContext().getRealPath("/")+sourceImageUrl);
			FileInputStream inputStream = new FileInputStream(f);
			file = new MockMultipartFile(f.getName(), f.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
			
			if(String.valueOf(comefrom).equals(NailOrderComeFromConstant.H5)){
				//图片处理
				nailOrder.setThirdFlag(ThirdFlagEnum.OK.getDisplayName());
			}
		}
		
		
		// 上传图片
		if(null != file && !file.isEmpty()){
			

			// 图片尺寸验证
			String checkNialImageSise = nailOrderService.checkNialImageSise(file);
			if (StringUtils.isNotBlank(checkNialImageSise)) {
				layUiResult.failure(checkNialImageSise);
				return layUiResult;
			}
			
			// 图片验证
			String errorMsg = imageService.checkImage(file);
			if (StringUtils.isNotBlank(errorMsg)) {
				layUiResult.failure(errorMsg);
				return layUiResult;
			}
			// 图片颜色统计
			LinkedHashMap<String, Integer> nailColorMap = nailOrderService.uploadImage(request,file,entity,UploadConstant.SAVE_UPLOAD_NAIL_PATH,NailOrderComeFromConstant.BACKSTAGE);
	
			if(null == nailColorMap){
				layUiResult.failure("图片有32种颜色意外的颜色，操作失败");
				return layUiResult;
			}
			
			// 钉子颜色列表统计
			LinkedHashMap<String, NailCount> nailCountMap = nailOrderService.nailCount(nailColorMap,entity);
			
			// 钉子与重量总数统计
			nailOrderService.nailTotalCount(nailCountMap,entity);
			
		
			// 设置高和宽
			nailOrder.setHeight(entity.getHeight());
			nailOrder.setWidth(entity.getWidth());
			// 设置执行步骤
			nailOrder.setStep(entity.getStep());
			// 设置上传图片
			nailOrder.setImageUrl(entity.getImageUrl());
			nailOrder.setResultImageUrl(entity.getResultImageUrl());
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
			layUiResult.success(id);
		}
		else {
			layUiResult.failure();
		}
		return layUiResult;
	}
	
	
	/**
	* @Title: generator
	* @Description: 清单生产
	* @param entity
	* @param file
	* @param request
	* @return
	* @throws IOException
	*/
	@ResponseBody
	@RequestMapping(value = "/generator")
	public LayUiResult generator(NailOrderVo entity, HttpServletRequest request) throws IOException {
		LayUiResult layUiResult = new LayUiResult();
		// 参数验证
		Integer id = entity.getId();
		if (null ==id || id < 1) {
			layUiResult.failure("id不能为空");
			return layUiResult;
		}
		NailOrder nailOrder = nailOrderService.get(id);
		if(null == nailOrder){
			layUiResult.failure("数据不存在");
			return layUiResult;
		}
		
		
		entity.setNailConfigId(nailOrder.getNailConfigId());
		
		if(StringUtils.isEmpty(nailOrder.getNailConfigId())){
			layUiResult.failure("订单 画框信息没有无法生存订单");
			return layUiResult;
		}
		
		
		String sourceImageUrl = nailOrder.getImageUrl();
		
		if(StringUtils.isEmpty(sourceImageUrl)){
			layUiResult.failure("订单 图片不存在无法生存订单");
			return layUiResult;
		}
		
		
		MultipartFile file = null;
		if(StringUtils.isNotBlank(sourceImageUrl)){
			File f = new File(request.getSession().getServletContext().getRealPath("/")+sourceImageUrl);
			FileInputStream inputStream = new FileInputStream(f);
			file = new MockMultipartFile(f.getName(), f.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
		}
		
		
		// 上传图片
		if(null != file && !file.isEmpty()){
			

			// 图片尺寸验证
			String checkNialImageSise = nailOrderService.checkNialImageSise(file);
			if (StringUtils.isNotBlank(checkNialImageSise)) {
				layUiResult.failure(checkNialImageSise);
				return layUiResult;
			}
			
			// 图片验证
			String errorMsg = imageService.checkImage(file);
			if (StringUtils.isNotBlank(errorMsg)) {
				layUiResult.failure(errorMsg);
				return layUiResult;
			}
			// 图片颜色统计
			LinkedHashMap<String, Integer> nailColorMap = nailOrderService.uploadImage(request,file,entity,UploadConstant.SAVE_UPLOAD_NAIL_PATH,NailOrderComeFromConstant.BACKSTAGE);
	
			// 钉子颜色列表统计
			LinkedHashMap<String, NailCount> nailCountMap = nailOrderService.nailCount(nailColorMap,entity);
			
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
			layUiResult.success(id);
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
	public LayUiResult list(NailOrderVo entity, Integer checkoutFlagX ,HttpServletRequest request) {

		
		// 特殊处理
		if(null != checkoutFlagX){
			entity.setCheckoutFlag(checkoutFlagX);
		}else{
			entity.setCheckoutFlag(-1);
		}
		
		entity.setNailPictureFrameStockId(entity.getFrameId());
		
		// 查找类型处理
		String searchKey = entity.getSearchKey();
		String searchValue = entity.getSearchValue();
		if(StringUtils.isNotEmpty(searchKey)){
			if(searchKey.equals("1")){
				entity.setUsername(searchValue);
			}else if(searchKey.equals("2")){
				entity.setImageName(searchValue);
			}else if(searchKey.equals("3")){
				entity.setMobile(searchValue);
				
			}
		}
		
		String nailImageSizeId = entity.getNailImageSizeId();
		if(StringUtils.isNotEmpty(nailImageSizeId)){
			NailImageSize nailImageSize = nailImageSizeService.get(Integer.valueOf(nailImageSizeId));
			if(null != nailImageSize){
				String width = nailImageSize.getWidth();
				String height = nailImageSize.getHeight();
				entity.setHeight(height);
				entity.setWidth(width);
			}
		}
		
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
		
		int checkoutFlag = e.getCheckoutFlag();
		if(String.valueOf(checkoutFlag).equals(CheckoutFlagEnum.OK.getDisplayName())){
			result.failure("选择的数据已经出库，删除失败!");
			return result;
		}
		
		String sourceImageUrl =e.getImageUrl();
		String resultImageUrl =e.getResultImageUrl();

		Integer delResult = nailOrderService.delete(id);
		if (null != delResult && delResult > 0) {
			result.success();
			
			if(StringUtils.isNotEmpty(sourceImageUrl)){
				// 判断是否有其他数据引用图片
				Map<String,Object> tparamMap  = new  HashMap<String, Object>();
				tparamMap.put("imageUrl", sourceImageUrl);
				boolean checkExist = nailOrderService.checkImageExist(tparamMap,String.valueOf(id));
				if(checkExist){// 没有引用删除
					boolean  deleteResult = FileUtil.deleteFile(sourceImageUrl,request);
					logger.info("物理删除图片结果 = "+deleteResult);
					if(deleteResult){
						boolean  resultImageUrlResult = FileUtil.deleteFile(resultImageUrl,request);
						logger.info("物理删除图片结果 = "+resultImageUrlResult);
						
					}
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

		String deleteResult = nailOrderService.deleteByBatch(array,request);
		if (StringUtils.isEmpty(deleteResult)) {
			result.success();
			return result;
		}
		result.failure(deleteResult);
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
		NailOrderDto entity = nailOrderService.getById(id);
		result.success(entity);
		return result;
	}
	
	/**
	* @Title: export
	* @Description: 清单导出
	* @param response
	* @param request
	*/
	@RequestMapping("/export/{id}")
	public void export(@PathVariable String id ,HttpServletRequest request,HttpServletResponse response) {
		if(StringUtils.isEmpty(id)){
			logger.error("id参数错误,不能为空");
			return ;
			
		}
		// 获取清单详情
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		NailOrderDto entity = nailOrderService.getNailOrder(paramMap);
		
		// 导出订单详情Excel
		nailOrderService.exportExcel(request,response,entity);
		
	}
	
	
	/**
	* @Title: exportNail
	* @Description: 导出图钉
	* @author zhuzq
	* @date  2021年3月20日 下午5:44:36
	* @param entity
	* @param checkoutFlagX
	* @param request
	* @return
	*/
	@RequestMapping(value = "/export/nail")
	public void exportNail(NailOrderVo entity, Integer checkoutFlagX,String arrayId,HttpServletRequest request,HttpServletResponse response) {
		// 特殊处理
		if(null != checkoutFlagX){
			entity.setCheckoutFlag(checkoutFlagX);
		}else{
			entity.setCheckoutFlag(-1);
		}
		
		
		String nailConfigIde = entity.getNailConfigId();
		int checkoutFlag = entity.getCheckoutFlag();
		String createDateStr = entity.getCreateDateStr();
		if(StringUtils.isEmpty(createDateStr) && StringUtils.isEmpty(nailConfigIde) && checkoutFlag == -1){
				entity.setArray(arrayId);
		}
		
		Map<String,Object>  datamap = nailOrderService.analys(entity);
		// 图钉统计
		Map<String,Analys>  analysMap = null;
		
		String [] columnWidth ={"12","12","12","12","12","12","12","12","12","12"}; 
		String[][] columnNames =  new String[][] {
			{"编号","数量","重量","包数","数量平均值","重量平均值","包数平均值","数量占比","重量占比","包数占比"}, 
			{"indexId","nailNumber","requreWeight","requrePieces","nailNumberAvg","requreWeightAvg","requrePiecesAvg","nailNumberRatio","requreWeightRatio","requrePiecesRatio"}
			};
		List<Map<String,Object>> dataRows = new LinkedList<Map<String,Object>>();
		
		if(null != datamap && datamap.size() > 0){
			analysMap = (Map<String, Analys>) datamap.get("analysMap");
			if(null != analysMap && analysMap.size() > 0){
				for (Entry<String, Analys> e : analysMap.entrySet()) {
					Analys al = e.getValue();
					 Map<String, Object> map = new HashMap<String, Object>();
					 map.put("indexId", al.getIndexId());
					 map.put("nailNumber", al.getNailNumber());
					 map.put("requreWeight", al.getRequreWeight());
					 map.put("requrePieces", al.getRequrePieces());
					 map.put("nailNumberAvg", al.getNailNumberAvg());
					 map.put("requreWeightAvg", al.getRequreWeightAvg());
					 map.put("requrePiecesAvg", al.getRequrePiecesAvg());
					 map.put("nailNumberRatio", al.getNailNumberRatio());
					 map.put("requreWeightRatio", al.getRequreWeightRatio());
					 map.put("requrePiecesRatio", al.getRequrePiecesRatio());
					 dataRows.add(map);
					
				}
				
			}
			
		}
		String excelName = "图钉统计分析"+ExcelUtil.getFileName();
		ExcelUtil.exportExcel(request, response, excelName, columnWidth, columnNames, dataRows);
		
	}
	
	/**
	* @Title: exportDrawing
	* @Description: 导出图纸
	* @author zhuzq
	* @date  2021年3月20日 下午10:03:54
	* @param entity
	* @param checkoutFlagX
	* @param request
	* @param response
	*/
	@RequestMapping(value = "/export/drawing")
	public void exportDrawing(NailOrderVo entity, Integer checkoutFlagX,String arrayId,HttpServletRequest request,HttpServletResponse response) {
		// 特殊处理
		if(null != checkoutFlagX){
			entity.setCheckoutFlag(checkoutFlagX);
		}else{
			entity.setCheckoutFlag(-1);
		}
		
		String nailConfigIde = entity.getNailConfigId();
		int checkoutFlag = entity.getCheckoutFlag();
		String createDateStr = entity.getCreateDateStr();
		if(StringUtils.isEmpty(createDateStr) && StringUtils.isEmpty(nailConfigIde) && checkoutFlag == -1){
				entity.setArray(arrayId);
		}
		
		
		Map<String,Object>  datamap = nailOrderService.analys(entity);
		Map<String,AnalysNailConfig> nailConfigMap =  null;
		// 图钉统计
		if(null != datamap && datamap.size() > 0){
			nailConfigMap = (Map<String,AnalysNailConfig>) datamap.get("analysNailConfigMap");
		}
				
		String [] columnWidth ={"60","60"}; 
		String[][] columnNames =  new String[][] {
			{"款式","数量"}, 
			{"nailType","total"}
			};
		List<Map<String,Object>> dataRows = new LinkedList<Map<String,Object>>();
		
		if(null != datamap && datamap.size() > 0){
			nailConfigMap = (Map<String,AnalysNailConfig>) datamap.get("analysNailConfigMap");
			if(null != nailConfigMap && nailConfigMap.size() > 0){
				for (Entry<String, AnalysNailConfig> e : nailConfigMap.entrySet()) {
					AnalysNailConfig al = e.getValue();
					 Map<String, Object> map = new HashMap<String, Object>();
					 map.put("nailType", al.getNailType());
					 map.put("total", al.getTotal());
					 dataRows.add(map);
					
				}
				
			}	
				
	}
		String excelName = "图纸统计分析"+ExcelUtil.getFileName();
		ExcelUtil.exportExcel(request, response, excelName, columnWidth, columnNames, dataRows);
	}
	
}
