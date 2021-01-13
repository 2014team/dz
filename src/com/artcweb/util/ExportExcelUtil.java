package com.artcweb.util;
 
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.PrintSetup;
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


public class ExportExcelUtil  {
	
	private static Logger logger = LoggerFactory.getLogger(ExportExcelUtil.class);
	
 
	public ExportExcelUtil() {
		super();
	}
	
    private static OutputStream getOutputStream(String fileName, HttpServletResponse response,boolean openFlag) {
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
	
	
	public static void exportExcel(HttpServletRequest request, HttpServletResponse response,String[][] columnNames,String[] columnWidth,List<Map<String,Object>> rows,String excelName,NailOrderDto entity) {
		try {					
			SXSSFWorkbook workbook = createSXSSFWorkbook(columnNames,columnWidth,rows,excelName,entity,request);

			OutputStream outputStream = getOutputStream(excelName, response, false);
			BufferedOutputStream bufferedOutPut = new BufferedOutputStream(outputStream);
            workbook.write(bufferedOutPut);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
 
	
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
		titleFont.setFontName("宋体");
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		titleStyle.setFont(titleFont);
 
		
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
		titleFont_2.setFontName("宋体");
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
		titleFont_u.setFontName("宋体");
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
		headerFont_b.setFontName("宋体");
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
		headerFont.setFontName("宋体");
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
		dataFont_image.setFontName("宋体");
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
		dataFont_p.setFontName("宋体");
		dataStyle_p.setFont(dataFont_p);
		dataStyle_p.setWrapText(true);

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
		dataFont.setFontName("宋体");
		dataStyle.setFont(dataFont);
		dataStyle.setWrapText(true);
 
		// 尾部样式
		CellStyle footStyle = workbook.createCellStyle();
		footStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT); // 水平居中
		footStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		// 尾部字体
		Font footFont = workbook.createFont();
		footFont.setFontHeightInPoints((short) 11);
		footFont.setFontName("宋体");
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
		cell0_0.setCellValue(excelName+"("+DataUtil.format(new Date(), DataUtil.DATE_YYYY_MM_DD_HH_MM_SS)+")"); // 设置单元格 和里面的内容		
		
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
								 anchor = new XSSFClientAnchor(0, 0, 700, 250, (short) 0, i, (short) 1, 12);
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
			Map<String,Object> project = rows.get(i);
			for (int j = 0; j <names.length; j++) {
				Cell dataCell = dataRow.createCell(j);
				dataCell.setCellStyle(dataStyle);
				// 第一和第二列处理
				if(j == 1 || j == 2){
					dataCell.setCellStyle(headerStyle_b);
				}
				
				
				// 最后一行样式处理
				if(i== rows.size()-1){
					dataCell.setCellStyle(headerStyle_b);
				}
				obj = project.get(names[j]);
				dataCell.setCellValue(obj==null?"":obj.toString());
				
				
				//数据处理
				if((i == 9 &&j == 0) || (i == 8 &&j == 0) ){
					dataCell.setCellValue(excelName);
					dataCell.setCellStyle(dataStyle_p);
				}
				
				// 图钉类型
				if((i == 12 &&j == 0) ){
					dataCell.setCellValue(entity.getNailType());
				} 
				// 相框颜色
				if((i == 13 &&j == 0) ){
					dataCell.setCellValue(entity.getColorName());
				}
				// 买家名称
				if((i == 14 &&j == 0) ){
					dataCell.setCellValue(entity.getUsername());
				}
				
				if(i==1 && j==0){
					// 图片样式设置
					cell.setCellStyle(dataStyle_image);
				}
				
			}
		}
		
		return workbook;

	}
}