package com.artcweb.util;

import java.io.File;

import org.apache.ws.security.util.UUIDGenerator;

public class UploadUtil {

	/**
	 * 获取文件扩展名
	 * 
	 * @return string
	 */
	public static String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
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
}
