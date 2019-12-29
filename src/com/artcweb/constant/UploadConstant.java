package com.artcweb.constant;

public class UploadConstant {
	
	//上传类型
	public static final String[] ALLOW_IMAGEFILES = { ".jpg", ".jpeg", ".png", ".gif" };
	
	//大小限制
	public static final Integer LIMIT_IMAGE_SIZE = 6 * 1024 * 1024;//6M
	
	//保存路径
	public static final String SAVE_UPLOAD_PATH = "/upload/packge/";

}
