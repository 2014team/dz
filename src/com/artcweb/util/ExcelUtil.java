package com.artcweb.util;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artcweb.dto.NailOrderDto;
import com.artcweb.enums.ImageSuffixNameEnum;

import jdk.nashorn.internal.runtime.regexp.joni.constants.AnchorType;


public class ExcelUtil {
	private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
	
	public static String getFileName(){
		String result = DateUtil.format(new Date(), DateUtil.DATE_YYYY_MM_DD_HH_MM_SS);
		return result;
	}
	
	public static void exportExcel(HttpServletRequest request, HttpServletResponse response,String[][] columnNames,String[] columnWidth,List<Map<String,Object>> rows,String excelName,NailOrderDto entity) {
		try {					
			SXSSFWorkbook workbook = createSXSSFWorkbook(columnNames,columnWidth,rows,excelName,entity,request);

			OutputStream outputStream = ExportExcelUtil.getOutputStream(excelName, response, false);
			BufferedOutputStream bufferedOutPut = new BufferedOutputStream(outputStream);
            workbook.write(bufferedOutPut);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	* @Title: exportExcel
	* @Description: 导出Excel
	* @author zhuzq
	* @date  2021年3月20日 下午9:49:49
	* @param request
	* @param response
	* @param columnWidth
	* @param columnNames
	* @param dataRows
	*/
	public static void exportExcel(HttpServletRequest request,HttpServletResponse response,String excelName,String [] columnWidth,String[][] columnNames,List<Map<String,Object>> dataRows) {
		try {
			SXSSFWorkbook workbook = createSXSSFWorkbook(columnNames, columnWidth, dataRows, excelName, request);
			OutputStream outputStream = ExportExcelUtil.getOutputStream(excelName, response, false);
			BufferedOutputStream bufferedOutPut = new BufferedOutputStream(outputStream);
	        workbook.write(bufferedOutPut);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	* @Title: createSXSSFWorkbook
	* @Description: 创建Workbook
	* @author zhuzq
	* @date  2021年3月20日 下午9:51:21
	* @param columnNames
	* @param columnWidth
	* @param rows
	* @param excelName
	* @param request
	* @return
	*/
	private static SXSSFWorkbook createSXSSFWorkbook(String[][] columnNames, String[] columnWidth, List<Map<String,Object>> rows,String excelName,HttpServletRequest request){
		SXSSFWorkbook workbook = new SXSSFWorkbook(); // 创建工作薄，相当于一个文件
		Sheet sheet = workbook.createSheet(); // 创建一个表
		//sheet.setDefaultColumnWidth((short) 3); // 设置默认列宽
		//sheet.setColumnWidth(0, 18 * 256); // 设置单位列列宽
		sheet.setMargin(XSSFSheet.TopMargin, 0.64); // 页边距（上）
		sheet.setMargin(XSSFSheet.BottomMargin, 0.64); // 页边距（下）
		sheet.setMargin(XSSFSheet.LeftMargin, 0.64); // 页边距（左）
		sheet.setMargin(XSSFSheet.RightMargin, 0.64); // 页边距（右）
 
		PrintSetup ps = sheet.getPrintSetup();
		ps.setPaperSize(PrintSetup.A4_PAPERSIZE); // 设置纸张大小
		ps.setLandscape(true); // 打印方向，true：横向，false：纵向(默认)
 
		// 标题样式
		CellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 水平居中
		titleStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		// 标题字体
		Font titleFont = workbook.createFont();
		titleFont.setFontHeightInPoints((short) 12); // 字体大小
		titleFont.setFontName("微软雅黑");
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		titleStyle.setFont(titleFont);
		titleStyle.setWrapText(true);
		
		CellStyle titleStyle_2 = workbook.createCellStyle();
		titleStyle_2.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 水平居中
		titleStyle_2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		titleStyle_2.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
		titleStyle_2.setBorderLeft(XSSFCellStyle.BORDER_THIN); // 左边框
		titleStyle_2.setBorderTop(XSSFCellStyle.BORDER_THIN); // 上边框
		titleStyle_2.setBorderRight(XSSFCellStyle.BORDER_THIN); // 右边框
		titleStyle_2.setWrapText(true); // 设置多行显示
		// 表头字体
		Font titleFont_2 = workbook.createFont();
		titleFont_2.setFontHeightInPoints((short) 11);
		titleFont_2.setFontName("微软雅黑");
		titleFont_2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		titleStyle_2.setFont(titleFont_2);
		
		// 填报单位的样式
		CellStyle titleStyle_u = workbook.createCellStyle();
		titleStyle_u.setAlignment(XSSFCellStyle.ALIGN_LEFT); // 水平居左
		titleStyle_u.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		// 标题字体
		Font titleFont_u = workbook.createFont();
		titleFont_u.setUnderline(XSSFFont.U_SINGLE);
		titleFont_u.setFontHeightInPoints((short) 11);
		titleFont_u.setFontName("微软雅黑");
		titleStyle_u.setFont(titleFont_u);
 
		// 普通文本加粗样式
		CellStyle headerStyle_b = workbook.createCellStyle();
		headerStyle_b.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 水平居中
		headerStyle_b.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		headerStyle_b.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
		headerStyle_b.setBorderLeft(XSSFCellStyle.BORDER_THIN); // 左边框
		headerStyle_b.setBorderTop(XSSFCellStyle.BORDER_THIN); // 上边框
		headerStyle_b.setBorderRight(XSSFCellStyle.BORDER_THIN); // 右边框
		headerStyle_b.setWrapText(true); // 设置多行显示
		//这两句话是表示将表头单元格格式设置为文本型，在后面只要调用-----.setDataFormat(format.getFormat("@"))的方法就可以将数据设置为文本型。
		DataFormat format = workbook.createDataFormat();    
		headerStyle_b.setDataFormat(format.getFormat("@"));
		// 表头字体
		Font headerFont_b = workbook.createFont();
		headerFont_b.setFontHeightInPoints((short) 9);
		headerFont_b.setFontName("微软雅黑");
		headerFont_b.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		headerStyle_b.setFont(headerFont_b);
		headerStyle_b.setWrapText(true);//强行换行

		
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 水平居中
		headerStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		headerStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
		headerStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN); // 左边框
		headerStyle.setBorderTop(XSSFCellStyle.BORDER_THIN); // 上边框
		headerStyle.setBorderRight(XSSFCellStyle.BORDER_THIN); // 右边框
		headerStyle.setWrapText(true); // 设置多行显示
		// 表头字体
		Font headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 9);
		headerFont.setFontName("微软雅黑");
		headerStyle.setFont(headerFont);
 
		// 数据样式
		CellStyle dataStyle_image = workbook.createCellStyle();
		dataStyle_image.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 水平居中
		dataStyle_image.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		//dataStyle_image.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
		dataStyle_image.setBorderLeft(XSSFCellStyle.BORDER_THIN); // 左边框
		dataStyle_image.setBorderTop(XSSFCellStyle.BORDER_THIN); // 上边框
//		dataStyle_p.setBorderRight(XSSFCellStyle.BORDER_THIN); // 右边框
		//dataStyle_image.setDataFormat(format.getFormat("@"));      //将数据单元格格式设置为文本类型  
		// 数据字体
		Font dataFont_image = workbook.createFont();
		//dataFont_image.setFontHeightInPoints((short) 9);
		dataFont_image.setFontName("微软雅黑");
		dataStyle_image.setFont(dataFont_image);
		
		

		CellStyle dataStyle = workbook.createCellStyle();
		dataStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 水平居中
		dataStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		dataStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
		dataStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN); // 左边框
		dataStyle.setBorderTop(XSSFCellStyle.BORDER_THIN); // 上边框
		dataStyle.setBorderRight(XSSFCellStyle.BORDER_THIN); // 右边框
		dataStyle.setDataFormat(format.getFormat("@"));      //将数据单元格格式设置为文本类型  
		// 数据字体
		Font dataFont = workbook.createFont();
		dataFont.setFontHeightInPoints((short) 9);
		dataFont.setFontName("微软雅黑");
		dataStyle.setFont(dataFont);
		dataStyle.setWrapText(true);
		
		// 尾部样式
		CellStyle footStyle = workbook.createCellStyle();
		footStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT); // 水平居中
		footStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		// 尾部字体
		Font footFont = workbook.createFont();
		footFont.setFontHeightInPoints((short) 11);
		footFont.setFontName("微软雅黑");
		footStyle.setFont(footFont);
 
		CellStyle commonStyle = workbook.createCellStyle();
		commonStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
		commonStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN); // 左边框
		commonStyle.setBorderTop(XSSFCellStyle.BORDER_THIN); // 上边框
		commonStyle.setBorderRight(XSSFCellStyle.BORDER_THIN); // 右边框
 
		// 表格标题行
		Row row0 = sheet.createRow(0);
		row0.setHeight((short)(3 * 256));
		Cell cell0_0 = row0.createCell(0); // 创建单元格，参数说明的是第几个单元格
		cell0_0.setCellStyle(titleStyle);
		cell0_0.setCellValue(excelName); // 设置单元格 和里面的内容		
		
		if(columnWidth.length>0){
			Integer clWidth;
			for(int i =0;i<columnWidth.length;i++){
				if(columnWidth[i]!=null &&!"".equals(columnWidth[i])){
					clWidth = Integer.valueOf(columnWidth[i]);
					sheet.setColumnWidth(i, clWidth*256);
				}
			}
		}
		
		
		Row row = null;
		Cell cell = null;
		for(int i = 1 ; i<=columnNames.length ; i++){
			row = sheet.createRow(i);
			row.setHeight((short)(2 * 256));
			for(int j = 0 ;j < columnNames[i-1].length;j++){
				cell = row.createCell(j);
				cell.setCellValue(columnNames[i-1][j]);
				cell.setCellStyle(headerStyle);
				
			}
		}
		
		sheet.getRow(columnNames.length).setZeroHeight(true);
		// 合并单元格
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnNames[0].length-1)); // 合并大标题行
		String[] names = columnNames[columnNames.length-1];
		// 数据填充,标题占一行，columnNames占columnNames.length行，之后才到数据行
		Object obj = null;
		
		for (int i = 0; i < rows.size(); i++) {
			Row dataRow = sheet.createRow(columnNames.length+1+ i);
			Map<String,Object> project = rows.get(i);
			for (int j = 0; j <names.length; j++) {
				Cell dataCell = dataRow.createCell(j);
				dataCell.setCellStyle(dataStyle);
				obj = project.get(names[j]);
				dataCell.setCellValue(obj==null?"":obj.toString());
			}
			
			
		}
		return workbook;

	}
	
	
	
	
	/**
	* @Title: createSXSSFWorkbook
	* @Description: 清单
	* @author zhuzq
	* @date  2021年3月20日 下午5:55:02
	* @param columnNames
	* @param columnWidth
	* @param rows
	* @param excelName
	* @param entity
	* @param request
	* @return
	*/
	private static SXSSFWorkbook createSXSSFWorkbook(String[][] columnNames, String[] columnWidth, List<Map<String,Object>> rows,String excelName,NailOrderDto entity,HttpServletRequest request){
		SXSSFWorkbook workbook = new SXSSFWorkbook(); // 创建工作薄，相当于一个文件
 
		Sheet sheet = workbook.createSheet(); // 创建一个表
		//sheet.setDefaultColumnWidth((short) 3); // 设置默认列宽
		//sheet.setColumnWidth(0, 18 * 256); // 设置单位列列宽
 
		sheet.setMargin(XSSFSheet.TopMargin, 0.64); // 页边距（上）
		sheet.setMargin(XSSFSheet.BottomMargin, 0.64); // 页边距（下）
		sheet.setMargin(XSSFSheet.LeftMargin, 0.64); // 页边距（左）
		sheet.setMargin(XSSFSheet.RightMargin, 0.64); // 页边距（右）
 
		PrintSetup ps = sheet.getPrintSetup();
		ps.setPaperSize(PrintSetup.A4_PAPERSIZE); // 设置纸张大小
		ps.setLandscape(true); // 打印方向，true：横向，false：纵向(默认)
 
		// 标题样式
		CellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 水平居中
		titleStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		// 标题字体
		Font titleFont = workbook.createFont();
		titleFont.setFontHeightInPoints((short) 12); // 字体大小
		titleFont.setFontName("微软雅黑");
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		titleStyle.setFont(titleFont);
		titleStyle.setWrapText(true);
		
		CellStyle titleStyle_2 = workbook.createCellStyle();
		titleStyle_2.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 水平居中
		titleStyle_2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		titleStyle_2.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
		titleStyle_2.setBorderLeft(XSSFCellStyle.BORDER_THIN); // 左边框
		titleStyle_2.setBorderTop(XSSFCellStyle.BORDER_THIN); // 上边框
		titleStyle_2.setBorderRight(XSSFCellStyle.BORDER_THIN); // 右边框
		titleStyle_2.setWrapText(true); // 设置多行显示
		// 表头字体
		Font titleFont_2 = workbook.createFont();
		titleFont_2.setFontHeightInPoints((short) 11);
		titleFont_2.setFontName("微软雅黑");
		titleFont_2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		titleStyle_2.setFont(titleFont_2);
		
		// 填报单位的样式
		CellStyle titleStyle_u = workbook.createCellStyle();
		titleStyle_u.setAlignment(XSSFCellStyle.ALIGN_LEFT); // 水平居左
		titleStyle_u.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		// 标题字体
		Font titleFont_u = workbook.createFont();
		titleFont_u.setUnderline(XSSFFont.U_SINGLE);
		titleFont_u.setFontHeightInPoints((short) 11);
		titleFont_u.setFontName("微软雅黑");
		titleStyle_u.setFont(titleFont_u);
 
		// 普通文本加粗样式
		CellStyle headerStyle_b = workbook.createCellStyle();
		headerStyle_b.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 水平居中
		headerStyle_b.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		headerStyle_b.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
		headerStyle_b.setBorderLeft(XSSFCellStyle.BORDER_THIN); // 左边框
		headerStyle_b.setBorderTop(XSSFCellStyle.BORDER_THIN); // 上边框
		headerStyle_b.setBorderRight(XSSFCellStyle.BORDER_THIN); // 右边框
		headerStyle_b.setWrapText(true); // 设置多行显示
		//这两句话是表示将表头单元格格式设置为文本型，在后面只要调用-----.setDataFormat(format.getFormat("@"))的方法就可以将数据设置为文本型。
		DataFormat format = workbook.createDataFormat();    
		headerStyle_b.setDataFormat(format.getFormat("@"));
		// 表头字体
		Font headerFont_b = workbook.createFont();
		headerFont_b.setFontHeightInPoints((short) 9);
		headerFont_b.setFontName("微软雅黑");
		headerFont_b.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		headerStyle_b.setFont(headerFont_b);
		headerStyle_b.setWrapText(true);//强行换行

		
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 水平居中
		headerStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		headerStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
		headerStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN); // 左边框
		headerStyle.setBorderTop(XSSFCellStyle.BORDER_THIN); // 上边框
		headerStyle.setBorderRight(XSSFCellStyle.BORDER_THIN); // 右边框
		headerStyle.setWrapText(true); // 设置多行显示
		// 表头字体
		Font headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 9);
		headerFont.setFontName("微软雅黑");
		headerStyle.setFont(headerFont);
 
		// 数据样式
		CellStyle dataStyle_image = workbook.createCellStyle();
		dataStyle_image.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 水平居中
		dataStyle_image.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		//dataStyle_image.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
		dataStyle_image.setBorderLeft(XSSFCellStyle.BORDER_THIN); // 左边框
		dataStyle_image.setBorderTop(XSSFCellStyle.BORDER_THIN); // 上边框
//		dataStyle_p.setBorderRight(XSSFCellStyle.BORDER_THIN); // 右边框
		//dataStyle_image.setDataFormat(format.getFormat("@"));      //将数据单元格格式设置为文本类型  
		// 数据字体
		Font dataFont_image = workbook.createFont();
		//dataFont_image.setFontHeightInPoints((short) 9);
		dataFont_image.setFontName("微软雅黑");
		dataStyle_image.setFont(dataFont_image);
		
		
		CellStyle dataStyle_p = workbook.createCellStyle();
		dataStyle_p.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 水平居中
		dataStyle_p.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		//dataStyle_p.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
		dataStyle_p.setBorderLeft(XSSFCellStyle.BORDER_THIN); // 左边框
//		dataStyle_p.setBorderTop(XSSFCellStyle.BORDER_THIN); // 上边框
//		dataStyle_p.setBorderRight(XSSFCellStyle.BORDER_THIN); // 右边框
		dataStyle_p.setDataFormat(format.getFormat("@"));      //将数据单元格格式设置为文本类型  
		// 数据字体
		Font dataFont_p = workbook.createFont();
		dataFont_p.setFontHeightInPoints((short) 9);
		dataFont_p.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		dataFont_p.setFontName("微软雅黑");
		dataStyle_p.setFont(dataFont_p);
		dataStyle_p.setWrapText(true);
		
		
		
		// checkbox 处理
		RichTextString nailType = new HSSFRichTextString(entity.getNailType()+"\u25A1");
		CellStyle dataStyle_ck = workbook.createCellStyle();
		dataStyle_ck.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 水平居中
		dataStyle_ck.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		dataStyle_ck.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
		dataStyle_ck.setBorderLeft(XSSFCellStyle.BORDER_THIN); // 左边框
		dataStyle_ck.setBorderTop(XSSFCellStyle.BORDER_THIN); // 上边框
		dataStyle_ck.setBorderRight(XSSFCellStyle.BORDER_THIN); // 右边框
		dataStyle_ck.setDataFormat(format.getFormat("@"));      //将数据单元格格式设置为文本类型  
		// 数据字体
		Font dataFont_ck = workbook.createFont();
		dataFont_ck.setFontHeightInPoints((short) 20);
		dataFont_ck.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		dataFont_ck.setFontName("Wingdings 2");
		dataStyle_ck.setFont(dataFont_ck);
		dataStyle_ck.setWrapText(true);

		RichTextString colorName = new HSSFRichTextString(entity.getColorName()+"\u25A1");
		
		
		
		CellStyle dataStyle_fzh = workbook.createCellStyle();
		dataStyle_fzh.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 水平居中
		dataStyle_fzh.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		dataStyle_fzh.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
		dataStyle_fzh.setBorderLeft(XSSFCellStyle.BORDER_THIN); // 左边框
		dataStyle_fzh.setBorderTop(XSSFCellStyle.BORDER_THIN); // 上边框
		dataStyle_fzh.setBorderRight(XSSFCellStyle.BORDER_THIN); // 右边框
		dataStyle_fzh.setDataFormat(format.getFormat("@"));      //将数据单元格格式设置为文本类型  
		// 数据字体
		Font dataFont_fzh = workbook.createFont();
		dataFont_fzh.setFontHeightInPoints((short) 20);
		//dataFont_fzh.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		dataFont_fzh.setFontName("Wingdings 2");
		dataStyle_fzh.setFont(dataFont_fzh);
		dataStyle_fzh.setWrapText(true);
		
		RichTextString fenzhuanghe = new HSSFRichTextString("分装盒"+"\u25A1");

		RichTextString peijianbao = new HSSFRichTextString("配件包"+"\u25A1");
		
		
		
		

		CellStyle dataStyle = workbook.createCellStyle();
		dataStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 水平居中
		dataStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		dataStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
		dataStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN); // 左边框
		dataStyle.setBorderTop(XSSFCellStyle.BORDER_THIN); // 上边框
		dataStyle.setBorderRight(XSSFCellStyle.BORDER_THIN); // 右边框
		dataStyle.setDataFormat(format.getFormat("@"));      //将数据单元格格式设置为文本类型  
		// 数据字体
		Font dataFont = workbook.createFont();
		dataFont.setFontHeightInPoints((short) 9);
		dataFont.setFontName("微软雅黑");
		dataStyle.setFont(dataFont);
		dataStyle.setWrapText(true);
		
		CellStyle dataStyle_f = workbook.createCellStyle();
		dataStyle_f.setAlignment(XSSFCellStyle.ALIGN_LEFT); // 水平居中
		dataStyle_f.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		dataStyle_f.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
		dataStyle_f.setBorderLeft(XSSFCellStyle.BORDER_THIN); // 左边框
		dataStyle_f.setBorderTop(XSSFCellStyle.BORDER_THIN); // 上边框
		dataStyle_f.setBorderRight(XSSFCellStyle.BORDER_THIN); // 右边框
		dataStyle_f.setDataFormat(format.getFormat("@"));      //将数据单元格格式设置为文本类型  
		// 数据字体
		Font dataFont_f = workbook.createFont();
		dataFont_f.setFontHeightInPoints((short) 9);
		dataFont_f.setFontName("微软雅黑");
		dataFont_f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		dataStyle_f.setFont(dataFont_f);
		dataStyle_f.setWrapText(true);
 
		// 尾部样式
		CellStyle footStyle = workbook.createCellStyle();
		footStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT); // 水平居中
		footStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		// 尾部字体
		Font footFont = workbook.createFont();
		footFont.setFontHeightInPoints((short) 11);
		footFont.setFontName("微软雅黑");
		footStyle.setFont(footFont);
 
		CellStyle commonStyle = workbook.createCellStyle();
		commonStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
		commonStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN); // 左边框
		commonStyle.setBorderTop(XSSFCellStyle.BORDER_THIN); // 上边框
		commonStyle.setBorderRight(XSSFCellStyle.BORDER_THIN); // 右边框
 
		// 表格标题行
		Row row0 = sheet.createRow(0);
		row0.setHeight((short)(3 * 256));
		Cell cell0_0 = row0.createCell(0); // 创建单元格，参数说明的是第几个单元格
		cell0_0.setCellStyle(titleStyle);
		cell0_0.setCellValue(excelName+"数量清单\n"+DateUtil.format(new Date(), DateUtil.DATE_YYYY_MM_DD_HH_MM_SS)); // 设置单元格 和里面的内容		
		
		if(columnWidth.length>0){
			Integer clWidth;
			for(int i =0;i<columnWidth.length;i++){
				if(columnWidth[i]!=null &&!"".equals(columnWidth[i])){
					clWidth = Integer.valueOf(columnWidth[i]);
					sheet.setColumnWidth(i, clWidth*256);
				}
			}
		}
		
		
		Row row = null;
		Cell cell = null;
		for(int i = 1 ; i<=columnNames.length ; i++){
			row = sheet.createRow(i);
			row.setHeight((short)(2 * 256));
			for(int j = 0 ;j < columnNames[i-1].length;j++){
				cell = row.createCell(j);
				if(i==1 && j==0){
					ByteArrayOutputStream out = null;
					XSSFClientAnchor anchor = null;
					try {
						// 图片处理
						String imageUrl = entity.getImageUrl();
						if(StringUtils.isNotEmpty(imageUrl)){
							imageUrl = request.getSession().getServletContext().getRealPath("/") + imageUrl; 
							BufferedImage  bufferImg = ImageIO.read(new File(imageUrl));
							 
							 out = new ByteArrayOutputStream();
							 String fileExt =  UploadUtil.getFileExt1(imageUrl);
							 
							 
							 
							 ImageIO.write(bufferImg, fileExt, out);
							 byte[] imageByte =  out.toByteArray();
							//调用Drawing对象进行绘画操作
								XSSFDrawing drawingPatriarch = (XSSFDrawing) sheet.createDrawingPatriarch();
								//使用Anchor进行图片位置等方面的调节
								//   参数   说明    
				                 //  dx1  第1个单元格中x轴的偏移量     
				                 //  dy1  第1个单元格中y轴的偏移量     
				                 //  dx2     第2个单元格中x轴的偏移量     
				                 //  dy2  第2个单元格中y轴的偏移量    
				                 //  col1 第1个单元格的列号     
				                 //  row1  第1个单元格的行号     
				                 //  col2 第2个单元格的列号     
				                 //  row2 第2个单元格的行号    
								 anchor = new XSSFClientAnchor(0, 0, 0, 0, (short) 0, i, (short) 1, 12);
								//product.getBarcodeImg()是一个byte[]
								//根据anchor和图片的byte[]来进行创建
								drawingPatriarch.createPicture(anchor, workbook.addPicture(imageByte, XSSFWorkbook.PICTURE_TYPE_JPEG));
						}
					}catch (Exception e) {
						e.printStackTrace();
						logger.error(e.getMessage());
					}finally{
						if(null != out){
							try {
								out.close();
							} catch (IOException e) {
								logger.error(e.getMessage());
								e.printStackTrace();
							}
						}
						anchor = null;
					}
					
				}else{
					cell.setCellValue(columnNames[i-1][j]);
				}
				
				cell.setCellStyle(headerStyle);
				
				if(i==1){
					// 二级标题颜色设置
					cell.setCellStyle(titleStyle_2);
				}
				
				
			}
			
		}
		
		sheet.getRow(columnNames.length).setZeroHeight(true);
		// 合并单元格
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnNames[0].length-1)); // 合并大标题行
		String[] names = columnNames[columnNames.length-1];
		// 数据填充,标题占一行，columnNames占columnNames.length行，之后才到数据行
		Object obj = null;
		
		for (int i = 0; i < rows.size(); i++) {
			Row dataRow = sheet.createRow(columnNames.length+1+ i);
			if(i ==0){
				
				sheet.addMergedRegion(new CellRangeAddress(1, 11, 0, 0)); // 合并大标题行
			}
			// 买家名称
			if(rows.size() >= 13){
				if(i ==12){
					CellRangeAddress cra = new CellRangeAddress(12, 13, 0, 0);
					sheet.addMergedRegion(cra); // 合并大标题行
				}
			}
			
			// 图钉类型
			if(rows.size() >= 16){
				if(i ==14){
					CellRangeAddress cra = new CellRangeAddress(14, 16, 0, 0);
					sheet.addMergedRegion(cra); // 合并大标题行
				}
			}
			// 画框颜色
			if(rows.size() >= 19){
				if(i ==17){
					CellRangeAddress cra = new CellRangeAddress(17, 19, 0, 0);
					sheet.addMergedRegion(cra); // 合并大标题行
				}
			}
			// 分装盒
			if(rows.size() >= 22){
				if(i ==20){
					CellRangeAddress cra = new CellRangeAddress(20, 22, 0, 0);
					sheet.addMergedRegion(cra); // 合并大标题行
				}
			}
			// 配件包
			if(rows.size() >= 25){
				if(i ==23){
					CellRangeAddress cra = new CellRangeAddress(23, 25, 0, 0);
					sheet.addMergedRegion(cra); // 合并大标题行
				}
			}
			
			
			// 备注说明合并
			if(i ==rows.size()-1){//最后一行
				
				int addRow = (rows.size() ) + 2; // 2行标题
				
				CellRangeAddress cra = new CellRangeAddress(26, addRow, 0, 0);
				sheet.addMergedRegion(cra); // 合并大标题行
			}
		
		
			// 设置最后一行行高
			if(i ==rows.size()-1){
				dataRow.setHeight((short)(4 * 256));
			}
		
			Map<String,Object> project = rows.get(i);
			for (int j = 0; j <names.length; j++) {
				Cell dataCell = dataRow.createCell(j);
				dataCell.setCellStyle(dataStyle);
				
			
				
				// 第一和第二列处理
				if(j == 0 || j == 1 || j == 2 || j == 4){
					dataCell.setCellStyle(headerStyle_b);
				}
				
				
				// 最后一行样式处理
				if(i== rows.size()-1){
					dataCell.setCellStyle(headerStyle_b);
				}
				obj = project.get(names[j]);
				dataCell.setCellValue(obj==null?"":obj.toString());
				
				
				//数据处理
				//图纸名称
				if((i == 9 &&j == 0) || (i == 8 &&j == 0) ){
					dataCell.setCellValue(excelName);
					dataCell.setCellStyle(dataStyle_p);
				}
				
				// 图钉类型
				if((i == 11 &&j == 0) ){
					dataCell.setCellValue(nailType);
					dataCell.setCellStyle(dataStyle_ck);
				} 
				// 相框颜色
				if((i == 14 &&j == 0) ){
					dataCell.setCellValue(colorName);
					dataCell.setCellStyle(dataStyle_ck);
				}
				// 买家名称
				/*if((i == 15 &&j == 0) ){
					dataCell.setCellValue(entity.getUsername());
				}*/
				
				// 分装盒
				if((i == 17 &&j == 0) ){
					dataCell.setCellValue(fenzhuanghe);
					dataCell.setCellStyle(dataStyle_ck);
				}
				
				// 配件包
				if((i == 20 &&j == 0) ){
					dataCell.setCellValue(peijianbao);
					dataCell.setCellStyle(dataStyle_ck);
				}
				
				// 说明
				if((i == 23 &&j == 0) ){
					String describe="请亲在第一时间内核对包数； 使用手机号解锁清单上的图纸； 有疑问及时联系客服";
					dataCell.setCellValue(describe);
				}
				
				if(i==1 && j==0){
					// 图片样式设置
					cell.setCellStyle(dataStyle_image);
				}
				
				 // 签名单元格样式
				if(j==names.length-1){
					dataCell.setCellStyle(dataStyle_f);
				}
				
			}
		}
		
		return workbook;

	}
	

}