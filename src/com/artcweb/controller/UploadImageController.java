package com.artcweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.artcweb.baen.JsonResult;
import com.artcweb.constant.UploadConstant;
import com.artcweb.service.ImageService;


@Controller
public class UploadImageController {
	
	@Autowired
	private ImageService imageService;
	
	
	@ResponseBody
	@RequestMapping("/admin/center/upload/image")
	public JsonResult upload(MultipartFile file, HttpServletRequest request, HttpServletResponse response){
		JsonResult jsonResult = new JsonResult();
		
		String uploadPath = null;
		if(null != file){
			
			// 图片验证
			String errorMsg = imageService.checkImage(file);
			if(StringUtils.isNotBlank(errorMsg)){
				jsonResult.failure(errorMsg);
				return jsonResult;
			}
			// 上传图片
			uploadPath = imageService.uploadImage(request, file, UploadConstant.SAVE_UPLOAD_PATH);
		}
		jsonResult.success(uploadPath);
		return jsonResult;
	}
	
}
