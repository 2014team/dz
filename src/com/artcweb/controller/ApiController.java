package com.artcweb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.artcweb.bean.LayUiResult;
import com.artcweb.bean.Order;
import com.artcweb.bean.PicPackage;
import com.artcweb.bean.User;
import com.artcweb.constant.ComeFromConstant;
import com.artcweb.dto.PicPackageDto;
import com.artcweb.service.OrderService;
import com.artcweb.service.PicPackageService;
import com.artcweb.service.UserService;

@Controller
public class ApiController {
	
	@Autowired
	private PicPackageService picPackageService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	
	
	@ResponseBody
	@RequestMapping("/api/templete/list")
	public LayUiResult getTempleteList(LayUiResult result){
		PicPackage entity = new  PicPackage();
		entity.setComeFrom(ComeFromConstant.TEMPLATE);
		// 获取参数
		Integer page = result.getPage();
		Integer limit = result.getLimit();
		result = new LayUiResult(page, limit);
		result = picPackageService.findByPage(entity, result);
		
		List<PicPackageDto> resultList  = new ArrayList<PicPackageDto>();
		if(null != result){
			List<PicPackage> list = ( List<PicPackage> ) result.getData();
			if(null != list && list.size() > 0){
				for (PicPackage picPackage : list) {
					PicPackageDto picPackageDto = new PicPackageDto();
					picPackageDto.setPackageId(picPackage.getPackageId());
					picPackageDto.setPackageName(picPackage.getPackageName());
					resultList.add(picPackageDto);
				}
			}
		}
		result.success(resultList);
		return result;
	}

	
	/**
	* @Title: saveCustom
	* @Description: API自定义保存
	* @param entity
	* @param file
	* @param stepFile
	* @param request
	* @return
	*/
	@ResponseBody
	@RequestMapping(value = "/api/user/custom/add/order")
	public LayUiResult saveCustom(Order entity, MultipartFile file,  MultipartFile stepFile,HttpServletRequest request) {

		LayUiResult result = new LayUiResult();
		
		orderService.stepFileDeal(entity,stepFile);
		
		//买家名称
		String userName = entity.getUserName();
		if(StringUtils.isBlank(userName)){
			result.failure("userName不能为空");
			return result;
		}
		//手机号码
		String mobile = entity.getMobile();
		if(null == mobile){
			result.failure("mobile不能为空");
			return result;
		}
		//步骤
		if(null == stepFile || stepFile.isEmpty()){
			result.failure("步骤附件不能为空");
			return result;
		}
		
		Integer pins = entity.getPins();
		if(null == pins){
			pins = 150;
		}

		String packageName = orderService.getPackageName(userName);
		if(StringUtils.isBlank(packageName)){
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("userName", userName);
			User user = userService.getByMap(paramMap);
			if(null != user){
				packageName = user.getUserName() +"-1";
				userService.delete(user.getId());
			}
		}
		
		
		entity.setPackageName(packageName);
		entity.setPins(pins);
		entity.setSort(1);
		
		entity.setComeFrom(ComeFromConstant.CUSTOM_MAKE);
		result = orderService.saveApiNewTemplate(entity, file, request);
		return result;
	}
	
	/**
	* @Title: saveTemplete
	* @Description: API模板保存
	* @param entity
	* @param file
	* @param stepFile
	* @param request
	* @return
	*/
	@ResponseBody
	@RequestMapping(value = "/api/user/templete/add/order")
	public LayUiResult saveTemplete(Order entity,HttpServletRequest request) {
		
		LayUiResult result = new LayUiResult();
		
		//买家名称
		String userName = entity.getUserName();
		if(StringUtils.isBlank(userName)){
			result.failure("userName不能为空");
			return result;
		}
		//手机号码
		String mobile = entity.getMobile();
		if(null == mobile){
			result.failure("mobile不能为空");
			return result;
		}
		//步骤
		Integer packageId = entity.getPackageId();
		if(null == packageId){
			result.failure("packageId不能为空");
			return result;
		}
		
		entity.setSort(1);
		entity.setTemplate(ComeFromConstant.TEMPLATE);
		result = orderService.saveApiChooseTemplate(entity,request);
		return result;
	}
}
