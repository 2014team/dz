package com.artcweb.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.NailCount;
import com.artcweb.bean.NailSecret;
import com.artcweb.bean.NailWhile;
import com.artcweb.constant.NailOrderComeFromConstant;
import com.artcweb.constant.UploadConstant;
import com.artcweb.dto.NailOrderDto;
import com.artcweb.enums.StatusEnum;
import com.artcweb.enums.ThirdFlagEnum;
import com.artcweb.service.ImageService;
import com.artcweb.service.NailOrderService;
import com.artcweb.service.NailSecretService;
import com.artcweb.service.NailWhileService;
import com.artcweb.util.ImageUtil;
import com.artcweb.util.UploadUtil;
import com.artcweb.vo.NailOrderVo;

@Controller
@RequestMapping("/api/nail")
public class ApiNailOrderController {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(ApiNailOrderController.class);
	
	@Autowired
	private NailOrderService nailOrderService;
	
	@Autowired
	private NailSecretService nailSecretService;
	
	@Autowired
	private NailWhileService nailWhileService;
	
	
	@Autowired
	private ImageService imageService;
	
	/**
	* @Title: indexSearch
	* @Description: 首页搜索
	* @param request
	* @param file
	* @param entity
	* @param result
	* @return
	*/
	@ResponseBody
	@RequestMapping("/search")
	public LayUiResult indexSearch(HttpServletRequest request,MultipartFile file,NailOrderVo entity, LayUiResult result){
		
//		String s = imageService.uploadImage(request, file, UploadConstant.SAVE_UPLOAD_NAIL_PATH, 1000, 1000,ImagePrefixNameEnum.NAIL,ImageSuffixNameEnum.GIF);
//		System.out.println(s);
		
		// 验证
		String keyword = entity.getKeyword();
		if(StringUtils.isEmpty(keyword)){
			result.failure("参数[keyword]不能为空");
			return result;
		}
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("keyword", keyword);
		List<NailOrderDto> nailOrderDtoList = nailOrderService.apiSelect(paramMap);
		result.success(nailOrderDtoList);
		return result;
		
	}
	
	/**
	* @Title: get
	* @Description: 查找订单详情
	* @param entity
	* @param result
	* @return
	*/
	@ResponseBody
	@RequestMapping("/order/get")
	public LayUiResult get(NailOrderVo entity, LayUiResult result){
		
		Integer orderId = entity.getId();
		if(null == orderId || orderId < 1){
			result.failure("参数[id]不能为空");
			return result;
		}
		
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("id", orderId);
		NailOrderDto nailOrderDto = nailOrderService.apiGet(paramMap);
		if(null == nailOrderDto){
			result.failure("订单不存在");
			return result;
		}
		
		
		//检查是否白名单
		Map<String,Object> whileParamMap = new HashMap<String, Object>();
		whileParamMap.put("mobile",nailOrderDto.getMobile());
		List<NailWhile> whileList = nailWhileService.select(whileParamMap);
		if(null != whileList && whileList.size() > 0){
			nailOrderDto.setStatus(StatusEnum.OK.getDisplayName());
			result.success(nailOrderDto);
			return result;
		}
		
		
		
		///判断是否输入过秘钥
		String status = nailOrderDto.getStatus();
		if(null == status || !status.equals(StatusEnum.OK.getDisplayName())){
			//秘钥验证
			String secretKey = entity.getSecretKey();
			if(StringUtils.isEmpty(secretKey)){
				result.failure("请输入秘钥");
				return result;
			}
			
			//验证秘钥是否系统生成
			paramMap.clear();
			paramMap.put("secretKey", secretKey);
			NailSecret secret = nailSecretService.getByMap(paramMap);
			if(null == secret){
				result.failure("秘钥不是系统生成，请输入正确秘钥");
				return result;
			}
			
			
			//验证秘钥是否已经被使用
			if(null != secret.getStatus() && secret.getStatus().equals(1)){
				result.failure("秘钥已经被使用，请联系工作人员");
				return result;
			}
			
			//更新秘钥
			secret.setStatus(1);
			secret.setOrderId(orderId);
			Integer updateResult = nailSecretService.update(secret);
			if(null != updateResult && updateResult > 0){
				nailOrderDto.setStatus(StatusEnum.OK.getDisplayName());
				result.success(nailOrderDto);
			}else{
				result.failure("更新秘钥状态失败");
			}
		}else if(null != status && status.equals(StatusEnum.OK.getDisplayName())){
			//证明已经验证通过
			result.success(nailOrderDto);
			return result;
		}
		return result;
		
	}
	
	
	/**
	 * @Title: updateCurrentStep
	 * @Description: 更新当前状态
	 * @param entity
	 * @param result
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/order/update/current/step", method = { RequestMethod.POST,
					RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public LayUiResult updateCurrentStep(NailOrderVo entity, LayUiResult result) {

		Integer id = entity.getId();
		if (null == id) {
			result.failure("参数[id]不能为空!");
			return result;
		}

		String currentStep = entity.getCurrentStep();
		if (StringUtils.isBlank(currentStep)) {
			result.failure("参数[currentStep]不能为空!");
			return result;
		}

		// 获取订单
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("currentStep", currentStep);
		Integer order = nailOrderService.apiUpdateCurrentStep(paramMap);
		if (null != order && order > 0) {
			result.success();
			return result;
		}
		result.failure();
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/order/save")
	public LayUiResult save(NailOrderVo entity, MultipartFile sourceFile,MultipartFile resultFile,HttpServletRequest request,HttpServletResponse response) throws IOException {
		LayUiResult layUiResult = new LayUiResult();
		
		
		if(null == sourceFile || sourceFile.isEmpty() ){
			layUiResult.failure("[sourceFile]图片不能为空");
			return layUiResult;
		}
		if(null == resultFile || resultFile.isEmpty() ){
			layUiResult.failure("[resultFile]图片不能为空");
			return layUiResult;
		}
		
		// 图片验证
		String errorMsg = imageService.checkImage(sourceFile);
		if (StringUtils.isNotBlank(errorMsg)) {
			layUiResult.failure(errorMsg);
			logger.error("图片验证失败");
			return layUiResult;
		}
		
		errorMsg= imageService.checkImage(resultFile);
		if (StringUtils.isNotBlank(errorMsg)) {
			layUiResult.failure(errorMsg);
			logger.error("图片验证失败");
			return layUiResult;
		}
		
		// 图片尺寸验证
		String checkNialImageSise = nailOrderService.checkNialImageSise(sourceFile);
		if (StringUtils.isNotBlank(checkNialImageSise)) {
			layUiResult.failure(checkNialImageSise);
			logger.error("图片不是尺寸配置列表范围,不符合要求，请尺寸配置");
			return layUiResult;
		}

		// 来源设置
		entity.setComefrom(String.valueOf(NailOrderComeFromConstant.H5));
		entity.setThirdFlag(ThirdFlagEnum.OFF.getDisplayName());
		entity.setCurrentStep("");
		
		
		// 设置图片名称
		String fileName = UploadUtil.getFileName(sourceFile);
		
		if(StringUtils.isEmpty(fileName)){
			layUiResult.failure("文件名称不能为空");
			logger.error("文件名称不能为空");
			return layUiResult;
		}
		
		// 图片名称
		entity.setImageName(fileName);
		
		//设置买家名称
		entity.setUsername(fileName);
		
		
		
		BufferedImage image = null;
		InputStream input = null;
		try {
			input = resultFile.getInputStream();
			image = ImageIO.read(input);
			String resultImageUrl = ImageUtil.getUploadPath(request, image,resultFile, UploadConstant.SAVE_UPLOAD_NAIL_PATH,true);
			entity.setResultImageUrl(resultImageUrl);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("处理resultFile失败");
		}finally{
			if(null != input){
				input.close();
			}
		}
		
		int len = fileName.length();
//		if(len > 11){
//			entity.setMobile(fileName.substring(0,11));
//		}
		
		// 名称唯一性验证（只验证后台数据）
		Map<String,Object> paramMap  = new  HashMap<String, Object>();
		//paramMap.put("comefrom", NailOrderComeFromConstant.BACKSTAGE);
		paramMap.put("imageName", fileName);
		boolean checkExist = nailOrderService.checkExist(paramMap,null);
		if(checkExist){// 没有引用删除
			layUiResult.failure("图纸名称系统已存在");
			logger.error("图纸名称系统已存在");
			return layUiResult;
		}
		
		// 上传图片
		if(null != sourceFile && !sourceFile.isEmpty()){
			
			// 图片颜色统计
			ConcurrentHashMap<String, Integer> nailColorMap = nailOrderService.uploadImage(request,sourceFile,entity,UploadConstant.SAVE_UPLOAD_NAIL_PATH);
	
			// 钉子颜色列表统计
			ConcurrentHashMap<Integer, NailCount> nailCountMap = nailOrderService.nailCount(nailColorMap,entity);
			
			// 钉子与重量总数统计
			nailOrderService.nailTotalCount(nailCountMap,entity);
		
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

}
