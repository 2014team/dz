package com.artcweb.util;
 
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExportExcelUtil  {
	
	private static Logger logger = LoggerFactory.getLogger(ExportExcelUtil.class);
	
 
	public ExportExcelUtil() {
		super();
	}
	
	public static OutputStream getOutputStream(String fileName, HttpServletResponse response,boolean openFlag) {
        try {
        	if(StringUtils.isEmpty(fileName)){
        		fileName = String.valueOf(System.currentTimeMillis());
        	}
			fileName = URLEncoder.encode(fileName, "UTF-8");
			if (openFlag) {
				response.setContentType("application/vnd.ms-excel");
				response.setCharacterEncoding("utf8");
				response.setContentType("octets/stream");
				response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");
				response.setHeader("Pragma", "public");
				response.setHeader("Cache-Control", "no-store");
				response.addHeader("Cache-Control", "max-age=0");
			} else {
				response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName+".xlsx"));
				response.setContentType("application/vnd.ms-excel;charset=utf8");
			}
            return response.getOutputStream();
        } catch (IOException e) {
        	e.printStackTrace();
            logger.error("导出excel表格失败!");
        }
        return null;
    }
    
    
    public static void writeExcel(HttpServletResponse response, String fileName, Workbook workbook) {
        // 判断数据
        if (workbook == null) {
        	logger.error("错误!");
        }
        // 重置响应对象
        response.reset();
        try {
            OutputStream outputStream = getOutputStream(fileName, response,false);
            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(outputStream);
            workbook.write(bufferedOutPut);
            bufferedOutPut.flush();
            bufferedOutPut.close();
            outputStream.close();
        } catch (IOException e) {
        	logger.error(e.getMessage());
        }
    }
    
	
	
	
	

 
 
	
	
}