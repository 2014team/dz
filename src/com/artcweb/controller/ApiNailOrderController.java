package com.artcweb.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.NailCount;
import com.artcweb.bean.NailDetailConfig;
import com.artcweb.bean.NailH5Strjson;
import com.artcweb.bean.Secret;
import com.artcweb.bean.While;
import com.artcweb.constant.NailOrderComeFromConstant;
import com.artcweb.constant.UploadConstant;
import com.artcweb.dto.NailConfigDto;
import com.artcweb.dto.NailOrderDto;
import com.artcweb.enums.SiteEnum;
import com.artcweb.enums.StatusEnum;
import com.artcweb.enums.ThirdFlagEnum;
import com.artcweb.service.NailConfigService;
import com.artcweb.service.NailDetailConfigService;
import com.artcweb.service.NailH5StrjsonService;
import com.artcweb.service.NailOrderService;
import com.artcweb.service.SecretService;
import com.artcweb.service.WhileService;
import com.artcweb.util.GsonUtil;
import com.artcweb.util.ImageUtil;
import com.artcweb.vo.NailH5StrjsonVo;
import com.artcweb.vo.NailOrderVo;

@Controller
@RequestMapping("/api/nail")
public class ApiNailOrderController {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(ApiNailOrderController.class);
	
	@Autowired
	private NailOrderService nailOrderService;
	@Autowired
	private NailDetailConfigService nailDetailConfigService;
	
	@Autowired
	private SecretService secretService;
	
	@Autowired
	private WhileService whileService;
	@Autowired
	private NailConfigService nailConfigService;
	@Autowired
	private NailH5StrjsonService nailH5StrjsonService;
	
	
	
	/**
	* @Title: indexSearch
	* @Description: ????????????
	* @param request
	* @param file
	* @param entity
	* @param result
	* @return
	*/
	@ResponseBody
	@RequestMapping("/search")
	public LayUiResult indexSearch(HttpServletRequest request,NailOrderVo entity, LayUiResult result){
		// ??????
		String keyword = entity.getKeyword();
		if(StringUtils.isEmpty(keyword)){
			result.failure("??????[keyword]????????????");
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
	* @Description: ??????????????????
	* @param entity
	* @param result
	* @return
	*/
	@ResponseBody
	@RequestMapping("/order/get")
	public LayUiResult get(NailOrderVo entity, LayUiResult result){
		
		Integer orderId = entity.getId();
		if(null == orderId || orderId < 1){
			result.failure("??????[id]????????????");
			return result;
		}
		
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("id", orderId);
		NailOrderDto nailOrderDto = nailOrderService.apiGet(paramMap);
		if(null == nailOrderDto){
			result.failure("???????????????");
			return result;
		}
		
		
		//?????????????????????
//		Map<String,Object> whileParamMap = new HashMap<String, Object>();
//		whileParamMap.put("mobile",nailOrderDto.getMobile());
//		List<While> whileList = whileService.select(whileParamMap);
//		if(null != whileList && whileList.size() > 0){
//			nailOrderDto.setStatus(StatusEnum.OK.getDisplayName());
//			result.success(nailOrderDto);
//			return result;
//		}
		
		
		
		///???????????????????????????
//		String status = nailOrderDto.getStatus();
//		if(null == status || !status.equals(StatusEnum.OK.getDisplayName())){
//			//????????????
//			String secretKey = entity.getSecretKey();
//			if(StringUtils.isEmpty(secretKey)){
//				result.failure("???????????????");
//				return result;
//			}
//			
//			//??????????????????????????????
//			paramMap.clear();
//			paramMap.put("secretKey", secretKey);
//			Secret secret = secretService.getByMap(paramMap);
//			if(null == secret){
//				result.failure("????????????????????????????????????????????????");
//				return result;
//			}
//			
//			
//			//?????????????????????????????????
//			if(null != secret.getStatus() && secret.getStatus().equals(1)){
//				result.failure("?????????????????????????????????????????????");
//				return result;
//			}
//			
//			//????????????
//			secret.setStatus(1);
//			secret.setOrderId(orderId);
//			secret.setSiteName(String.valueOf(SiteEnum.NAIL.getValue()));
//			Integer updateResult = secretService.update(secret);
//			if(null != updateResult && updateResult > 0){
//				nailOrderDto.setStatus(StatusEnum.OK.getDisplayName());
//				result.success(nailOrderDto);
//			}else{
//				result.failure("????????????????????????");
//			}
//		}else if(null != status && status.equals(StatusEnum.OK.getDisplayName())){
//			//????????????????????????
//			result.success(nailOrderDto);
//			return result;
//		}
		
		nailOrderDto.setStatus(StatusEnum.OK.getDisplayName());
		result.success(nailOrderDto);
		return result;
		
	}
	
	
	/**
	 * @Title: updateCurrentStep
	 * @Description: ??????????????????
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
			result.failure("??????[id]????????????!");
			return result;
		}

		String currentStep = entity.getCurrentStep();
		if (StringUtils.isBlank(currentStep)) {
			result.failure("??????[currentStep]????????????!");
			return result;
		}

		// ????????????
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
	@RequestMapping(value = "/config/color/list", method = { RequestMethod.POST,
					RequestMethod.GET }, produces = "application/json; charset=UTF-8")
	public LayUiResult colorList(NailOrderVo entity, LayUiResult result) {
		Map<String,Object> paramMap = null;
		List<NailDetailConfig> list = nailDetailConfigService.select(paramMap);
		TreeMap<String, String> map = new TreeMap<String, String>();
		if(null != list && list.size() > 0){
			for (NailDetailConfig n : list) {
				map.put(n.getRgb(), n.getNewSerialNumber());
			}
		}
	      result.success(map);
		
		return result;
	}
	
	
	


	
	/**
	* @Title: save
	* @Description: H5????????????
	* @param strJson
	* @param request
	* @return
	* @throws IOException
	*/
	@ResponseBody
	@RequestMapping(value = "/order/save")
	public LayUiResult save(String strJson,HttpServletRequest request) throws IOException {
		logger.info("??????strJson="+strJson);
		
		LayUiResult layUiResult = new LayUiResult();
		if(StringUtils.isEmpty(strJson)){
			layUiResult.failure("??????[strJson]????????????");
			return layUiResult;
		}
		
		NailH5Strjson h5Strjson = new NailH5Strjson();
		h5Strjson.setStrJson(strJson);
		Integer save = nailH5StrjsonService.save(h5Strjson);
		
		logger.info("save id="+h5Strjson.getId());
		
		NailOrderVo entity = new NailOrderVo();
		try {
			NailH5StrjsonVo nailH5StrjsonVo =( NailH5StrjsonVo ) GsonUtil.jsonToBean(strJson, NailH5StrjsonVo.class);
				
			if(null != nailH5StrjsonVo){
				String output_url = nailH5StrjsonVo.getOutput_url();
				if(StringUtils.isNotEmpty(output_url)){
					output_url =output_url+"?x-oss-process=image/auto-orient,1/resize,m_lfit,h_600/quality,q_100";
				}
				String resultImageUrl =ImageUtil.downloadImageFromURL(output_url, request, UploadConstant.SAVE_UPLOAD_NAIL_PATH);
				// h5??????????????????
				if(StringUtils.isNotEmpty(resultImageUrl)){
					entity.setResultImageUrl(resultImageUrl);
				}
				
				String scale_url = nailH5StrjsonVo.getScale_url();
				String imageUrl =ImageUtil.downloadImageFromURL(scale_url, request, UploadConstant.SAVE_UPLOAD_NAIL_PATH);
				// h5??????????????????
				if(StringUtils.isNotEmpty(imageUrl)){
					entity.setImageUrl(imageUrl);
				}
				
				String fileName = nailH5StrjsonVo.getOrder_sn();
				
				// ????????????
				entity.setImageName(fileName);
				
				//??????????????????
				entity.setUsername(fileName);
				
				//??????????????????
				if(StringUtils.isNotEmpty(fileName) && fileName.length() > 11){
					entity.setMobile(fileName.substring(0, 11));
					
						String  nail= fileName.substring(fileName.length()-2, fileName.length());
						Map <String,Object> paramMap = new HashMap<String, Object>();
						paramMap.put("nailType", nail);
						NailConfigDto nailConfigDto = nailConfigService.selectByMap(paramMap);
						if(null != nailConfigDto){
							entity.setNailConfigId(String.valueOf(nailConfigDto.getId()));
					}
				}
				
				
			}
		
		
		}
		catch (Exception e) {
			logger.error("json??????????????????");
			e.printStackTrace();
			layUiResult.failure();
			return layUiResult;
		}
		

		
		// ??????????????????
		String resultImageUrl = entity.getResultImageUrl();
		if(StringUtils.isEmpty(resultImageUrl)){
			layUiResult.failure("output_url????????????????????????");
			logger.error("output_url????????????????????????");
			return layUiResult;
		}
		
		
		// ????????????
		String imageUrl = entity.getImageUrl();
		if(StringUtils.isEmpty(imageUrl)){
			layUiResult.failure("scale_url????????????????????????");
			logger.error("scale_url????????????????????????");
			return layUiResult;
		}
		
		
	   // ????????????
		entity.setComefrom(String.valueOf(NailOrderComeFromConstant.H5));
		entity.setThirdFlag(ThirdFlagEnum.OFF.getDisplayName());
		entity.setCurrentStep("");
		
		String realPath = request.getSession().getServletContext().getRealPath("/");
		File imageUrlFile = new File(realPath+entity.getImageUrl());
		
		InputStream inputStream = new FileInputStream(imageUrlFile);
	    MultipartFile multipartFileImageUrlFilelFile = new MockMultipartFile(imageUrlFile.getName(), inputStream);
		
	    
	    // ??????????????????
		String checkNialImageSise = nailOrderService.checkNialImageSise(multipartFileImageUrlFilelFile);
		if (StringUtils.isNotBlank(checkNialImageSise)) {
			layUiResult.failure(checkNialImageSise);
			logger.error("????????????????????????????????????,?????????????????????????????????");
			return layUiResult;
		}
	     
		
		// ????????????????????????????????????????????????
		Map<String,Object> paramMap  = new  HashMap<String, Object>();
		//paramMap.put("comefrom", NailOrderComeFromConstant.BACKSTAGE);
		paramMap.put("imageName", entity.getImageName());
		boolean checkExist = nailOrderService.checkExist(paramMap,null);
		if(checkExist){
			layUiResult.failure("???????????????????????????");
			logger.error("???????????????????????????");
			return layUiResult;
		}
		
		// ????????????
		if(null != multipartFileImageUrlFilelFile && !multipartFileImageUrlFilelFile.isEmpty()){
			
			// ??????????????????
			LinkedHashMap<String, Integer> nailColorMap = nailOrderService.uploadImage(request,multipartFileImageUrlFilelFile,entity,UploadConstant.SAVE_UPLOAD_NAIL_PATH,NailOrderComeFromConstant.H5);
	
			// ????????????????????????
			LinkedHashMap<String, NailCount> nailCountMap = nailOrderService.nailCount(nailColorMap,entity);
			
			// ???????????????????????????
			nailOrderService.nailTotalCount(nailCountMap,entity);
		
		}
		// ??????
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
