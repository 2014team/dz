
package com.artcweb.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

public class FileUtil {

	/**
	 * 删除单个文件
	 * 
	 * @param sPath 被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath,HttpServletRequest request) {
		sPath = request.getSession().getServletContext().getRealPath("/") + sPath;
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}
	

}
