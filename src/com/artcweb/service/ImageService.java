package com.artcweb.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	/**
	 * @Title: checkImage
	 * @Description: 图片验证
	 * @param file
	 * @return
	 */
	public String checkImage(MultipartFile file);

	/**
	 * @Title: uploadImage
	 * @Description: 上传图片
	 * @param request
	 * @param file
	 * @param uploadPath
	 * @return
	 */
	public String uploadImage(HttpServletRequest request, MultipartFile file, String uploadPath);
	
	
	public String uploadMinImage(HttpServletRequest request, MultipartFile file, String uploadPath);

}
