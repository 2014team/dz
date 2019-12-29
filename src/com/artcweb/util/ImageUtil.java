package com.artcweb.util;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.artcweb.constant.SiteConstant;
import com.artcweb.constant.UploadConstant;

public class ImageUtil {

	/**
	 * @Title: checkImage
	 * @Description: 检查图片
	 * @param files
	 * @return
	 */
	public static String checkImage(MultipartFile file) {
		if (null != file) {
			if (!file.getContentType().contains("image")) {
				return "请上传图片!";
			}
		}
		return null;
	}

	/**
	 * @Title: checkImageType
	 * @Description: 检查图片类型
	 * @param file
	 * @return
	 */
	public static String checkImageType(MultipartFile file) {
		if (null != file) {
			if (!UploadUtil.checkFileType(file.getOriginalFilename(), UploadConstant.ALLOW_IMAGEFILES)) {
				return "请上传图片类型[" + Arrays.asList(UploadConstant.ALLOW_IMAGEFILES).toString() + "]";
			}
		}
		return null;
	}

	/**
	 * @Title: checkImageSize
	 * @Description: 图片大小限制
	 * @param file
	 * @return
	 */
	public static String checkImageSize(MultipartFile file) {
		if (null != file) {
			if (file.getSize() > UploadConstant.LIMIT_IMAGE_SIZE) {
				return "图片大小不能超过6M";
			}
		}
		return null;
	}
	
	
	public static String imageUrlDeal(String image) {
		if (StringUtils.isBlank(image)) {
			return "";
		}
		return SiteConstant.HTTP_DOMAIN + image;
		
	}


}
