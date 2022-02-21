package com.artcweb.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.artcweb.enums.ImagePrefixNameEnum;
import com.artcweb.enums.ImageSuffixNameEnum;
import com.artcweb.service.ImageService;
import com.artcweb.util.DateUtil;
import com.artcweb.util.ImageUtil;
import com.artcweb.util.UploadUtil;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class ImageServiceImpl implements ImageService {

	private static Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

	/**
	 * @Title: checkImage
	 * @Description: 图片验证
	 * @param file
	 * @return
	 */
	@Override
	public String checkImage(MultipartFile file) {
		// 图片验证
		String errorMsg = ImageUtil.checkImage(file);
		if (StringUtils.isNotBlank(errorMsg)) {
			return errorMsg;
		}
		// 图片格式验证
		errorMsg = ImageUtil.checkImageType(file);
		if (StringUtils.isNotBlank(errorMsg)) {
			return errorMsg;
		}

		// 图片大小验证
		errorMsg = ImageUtil.checkImageSize(file);
		if (StringUtils.isNotBlank(errorMsg)) {
			return errorMsg;
		}
		return null;
	}

	/**
	 * @Title: uploadImage
	 * @Description: 上传图片
	 * @param request
	 * @param file
	 * @param uploadPath
	 * @return
	 */
	@Override
	public String uploadImage(HttpServletRequest request, MultipartFile file, String uploadPath) {
		// 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\uploadPath\\文件夹中
		// String fileName = file.getOriginalFilename();
		// String fileExt[] = fileName.split("\\.");
		// 获取文件后缀名称
		String ext = UploadUtil.getFileExt(file.getOriginalFilename());
		// 新文件名称
		String newFileName = UploadUtil.getNewFileName(ext);

		String year = String.valueOf(DateUtil.getYear(new Date()));
		String month = String.valueOf(DateUtil.getMonth(new Date()));
		String folder = year + "/" + month + "/";
		uploadPath = uploadPath + folder;

		// 存储文件目录
		String realPath = request.getSession().getServletContext().getRealPath(uploadPath);
		String filePathAndName = null;
		if (realPath.endsWith(File.separator)) {
			filePathAndName = realPath + newFileName;
		} else {
			filePathAndName = realPath + File.separator + newFileName;
		}
		logger.info("-----上传的文件:-----" + filePathAndName);
		try {
			// 先把文件保存到本地
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, newFileName));
		} catch (IOException e1) {
			logger.error("-----文件保存到本地发生异常:-----" + e1.getMessage());
		}

		// // 图片处理
		// int big = 1 * 1024 * 1024; // 2M以上就进行0.6压缩
		// if (file.getSize() > big) {
		// thumbnail(filePathAndName, 0.6f);
		// } else {
		// thumbnail(filePathAndName, 0.8f);
		// }
		return uploadPath + newFileName;
	}

	@Override
	public String uploadMinImage(HttpServletRequest request, MultipartFile file, String uploadPath) {
		// 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\uploadPath\\文件夹中
		// String fileName = file.getOriginalFilename();
		// String fileExt[] = fileName.split("\\.");
		// 获取文件后缀名称
		String ext = UploadUtil.getFileExt(file.getOriginalFilename());
		// 新文件名称
		String newFileName = UploadUtil.getNewFileName(ext);
		newFileName = "min_"+newFileName;

		String year = String.valueOf(DateUtil.getYear(new Date()));
		String month = String.valueOf(DateUtil.getMonth(new Date()));
		String folder = year + "/" + month + "/";
		uploadPath = uploadPath + folder;

		// 存储文件目录
		String realPath = request.getSession().getServletContext().getRealPath(uploadPath);
		String filePathAndName = null;
		if (realPath.endsWith(File.separator)) {
			filePathAndName = realPath + newFileName;
		} else {
			filePathAndName = realPath + File.separator + newFileName;
		}
		logger.info("-----上传的文件:-----" + filePathAndName);
		try {
			// 先把文件保存到本地
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, newFileName));
		} catch (IOException e1) {
			logger.error("-----文件保存到本地发生异常:-----" + e1.getMessage());
		}

		// // 图片处理
		Integer scaleWidth = 50;
		Integer scaleHeight = 50;
		ImageUtil.thumbnail(filePathAndName, scaleWidth, scaleHeight,ImageSuffixNameEnum.JPG);
		return uploadPath + newFileName;
	}
	
	@Override
	public String uploadImage(HttpServletRequest request, MultipartFile file, String uploadPath,Integer scaleWidth,Integer scaleHeight,ImagePrefixNameEnum prefix,ImageSuffixNameEnum suffix) {
		// 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\uploadPath\\文件夹中
		// String fileName = file.getOriginalFilename();
		// String fileExt[] = fileName.split("\\.");
		// 获取文件后缀名称
		String ext = UploadUtil.getFileExt(file.getOriginalFilename());
		// 新文件名称
		String newFileName = UploadUtil.getNewFileName(ext);
		newFileName = prefix.getDeclaringClass()+newFileName;
		
		String year = String.valueOf(DateUtil.getYear(new Date()));
		String month = String.valueOf(DateUtil.getMonth(new Date()));
		String folder = year + "/" + month + "/";
		uploadPath = uploadPath + folder;
		
		// 存储文件目录
		String realPath = request.getSession().getServletContext().getRealPath(uploadPath);
		String filePathAndName = null;
		if (realPath.endsWith(File.separator)) {
			filePathAndName = realPath + newFileName;
		} else {
			filePathAndName = realPath + File.separator + newFileName;
		}
		logger.info("-----上传的文件:-----" + filePathAndName);
		try {
			// 先把文件保存到本地
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, newFileName));
		} catch (IOException e1) {
			logger.error("-----文件保存到本地发生异常:-----" + e1.getMessage());
		}
		
		// // 图片处理
		ImageUtil.thumbnail(filePathAndName, scaleWidth, scaleHeight,suffix);
		return uploadPath + newFileName;
	}

	
}
