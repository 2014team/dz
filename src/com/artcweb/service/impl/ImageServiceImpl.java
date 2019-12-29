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
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.artcweb.service.ImageService;
import com.artcweb.util.DataUtil;
import com.artcweb.util.ImageUtil;
import com.artcweb.util.UploadUtil;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class ImageServiceImpl implements ImageService {

	private static Logger logger = Logger.getLogger(ImageServiceImpl.class);

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

		String year = String.valueOf(DataUtil.getYear(new Date()));
		String month = String.valueOf(DataUtil.getMonth(new Date()));
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

		String year = String.valueOf(DataUtil.getYear(new Date()));
		String month = String.valueOf(DataUtil.getMonth(new Date()));
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
		thumbnail(filePathAndName, scaleWidth, scaleHeight);
		return uploadPath + newFileName;
	}

	private void thumbnail(String filePathAndName, Integer scaleWidth, Integer scaleHeight) {
		try {
			Thumbnails.of(filePathAndName).size(scaleWidth, scaleHeight).toFile(filePathAndName);
		} catch (IOException e) {
			logger.error("-----读取图片发生异常:{}-----" + e.getMessage());
			logger.info("-----尝试cmyk转化-----");
			File cmykJPEGFile = new File(filePathAndName);
			try {
				BufferedImage image = ImageIO.read(cmykJPEGFile);
				ImageOutputStream output = ImageIO.createImageOutputStream(cmykJPEGFile);
				if (!ImageIO.write(image, "jpg", output)) {
					logger.info("-----cmyk转化异常:{}-----");
				}
				Thumbnails.of(image).scale(0.4f).toFile(filePathAndName);
				logger.info("-----cmyk转化成功-----");
			} catch (IOException e1) {
				logger.info("-----cmyk转化异常:-----" + e1.getMessage());
			}
		}
	}

	public void thumbnail(String filePathAndName, double size) {
		try {
			Thumbnails.of(filePathAndName).scale(size).toFile(filePathAndName);
		} catch (IOException e) {
			logger.error("-----读取图片发生异常:{}-----" + e.getMessage());
			logger.info("-----尝试cmyk转化-----");
			File cmykJPEGFile = new File(filePathAndName);
			try {
				BufferedImage image = ImageIO.read(cmykJPEGFile);
				ImageOutputStream output = ImageIO.createImageOutputStream(cmykJPEGFile);
				if (!ImageIO.write(image, "jpg", output)) {
					logger.info("-----cmyk转化异常:{}-----");
				}
				Thumbnails.of(image).scale(0.4f).toFile(filePathAndName);
				logger.info("-----cmyk转化成功-----");
			} catch (IOException e1) {
				logger.info("-----cmyk转化异常:-----" + e1.getMessage());
			}
		}
	}

}
