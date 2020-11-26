package com.artcweb.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.ws.security.util.UUIDGenerator;
import org.springframework.web.multipart.MultipartFile;

public class UploadUtil {

	/**
	 * 获取文件扩展名
	 * 
	 * @return string
	 */
	public static String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}
	
	public static String getFileExt1(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".")+1);
	}
	
	public static String getFileName(MultipartFile file) {
		String result  = "";
		if(null == file || file.isEmpty()){
			return result;
		}
		result = file.getOriginalFilename();
		if(result.indexOf(".") != -1){
			result = result.substring(0,result.lastIndexOf("."));
		}
		
		return result;
	}

	/**
	 * 文件类型判断
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean checkFileType(String fileName, String[] allowFiles) {
		for (String ext : allowFiles) {
			if (fileName.toLowerCase().endsWith(ext)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 创建文件夹 不存在创建，存在则忽略
	 * 
	 * @param path
	 */
	public static void mkdirs(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	
	/**
	 * 
	* @Title: getNewFileName
	* @Description: 新文件名称
	* @param fileExt
	* @return
	 */
	public static String getNewFileName(String fileExt){
		return UUIDGenerator.getUUID() + fileExt;
	}
	
	/**
     * 获取图片宽度
     * @param file  图片文件
     * @return 宽度
     */
    public static int getImgWidth(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int ret = -1;
        try {
            is = new FileInputStream(file);
            src = javax.imageio.ImageIO.read(is);
            ret = src.getWidth(null); // 得到源图宽
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }	
}
