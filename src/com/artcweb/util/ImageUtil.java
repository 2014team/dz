package com.artcweb.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.artcweb.constant.SiteConstant;
import com.artcweb.constant.UploadConstant;

public class ImageUtil {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(ImageUtil.class);

	/**
	 * @Title: checkImage
	 * @Description: 检查图片
	 * @param files
	 * @return
	 */
	public static String checkImage(MultipartFile file) {
		if (null != file) {
			if (!file.getContentType().contains("image") && !file.getContentType().contains("application/octet-stream")) {
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
	
	/**
	* @Title: getUploadPath
	* @Description: 获取上传路径
	* @param request
	* @param file
	* @param uploadPath
	* @return
	 */
	public static String getUploadPath(HttpServletRequest request,BufferedImage image,MultipartFile file,String uploadPath) {
		// 新文件名称
		String newFileName = file.getOriginalFilename();//UploadUtil.getNewFileName(ext);

		String year = String.valueOf(DataUtil.getYear(new Date()));
		String month = String.valueOf(DataUtil.getMonth(new Date()));
		String folder = year + "/" + month + "/";
		uploadPath = uploadPath + folder;
		
		
		String realPath = request.getSession().getServletContext().getRealPath(uploadPath);
		// 创建目录
		UploadUtil.mkdirs(realPath);
		
		String filePathAndName = null;
		if (realPath.endsWith(File.separator)) {
			filePathAndName = realPath + newFileName;
		} else {
			filePathAndName = realPath + File.separator + newFileName;
		}
		
		try {
			// 获取文件后缀名称
			String ext = UploadUtil.getFileExt1(file.getOriginalFilename());
			if(StringUtils.isNotEmpty(uploadPath) && StringUtils.isNotEmpty(ext)){
				boolean writeResult = ImageIO.write(image, ext, new File(filePathAndName));
				logger.info("writeResult="+writeResult);
			}else{
				logger.error("获取图片上传路径为空！！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uploadPath+newFileName;
				
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
