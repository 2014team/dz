package com.artcweb.util;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.artcweb.constant.SiteConstant;
import com.artcweb.constant.UploadConstant;
import com.artcweb.service.impl.ImageServiceImpl;

public class ImageUtil {
	private static Logger logger = Logger.getLogger(ImageUtil.class);

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

	

	public static String checkImgWidth(MultipartFile file) {
		//2、图片内容宽高属性判断
        BufferedImage bufferedImage = null;
        try {
        	
            bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null || bufferedImage.getWidth() > 500 || bufferedImage.getHeight() > 500) {  
                return "请上传图片长宽500 x 500图片";
            }  
        } catch (Exception e) {
            logger.error("======图片解析错误！====="+e.getMessage());;
        }  
        return null;
	}

}
