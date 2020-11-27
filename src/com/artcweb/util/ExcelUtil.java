package com.artcweb.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;


import org.apache.poi.ss.usermodel.Workbook;

import com.artcweb.baen.NailOrder;
import com.artcweb.baen.NailOrderExport;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * excel工具类
 *
 */
public class ExcelUtil {

    /**
     * 导出excel
     *
     * @param response HttpServletResponse
     * @param fileName 文件名字
     * @param workbook 通过exportPicture()方法获取
     */
    public static void writeExcel(HttpServletResponse response, String fileName, Workbook workbook) {
        // 判断数据
        if (workbook == null) {
            System.out.println("错误!");
        }
        // 重置响应对象
        response.reset();
        try {
            OutputStream outputStream = getOutputStream(fileName, response);
            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(outputStream);
            workbook.write(bufferedOutPut);
            bufferedOutPut.flush();
            bufferedOutPut.close();
            outputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 导出文件时为Writer生成OutputStream
     *
     * @param fileName 文件名字
     * @param response response
     * @return 输出流
     */
    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) {
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "no-store");
            response.addHeader("Cache-Control", "max-age=0");
            return response.getOutputStream();
        } catch (IOException e) {
            System.out.println("导出excel表格失败!");
        }
        return null;
    }

//    /**
//     * 获取Workbook对象
//     *
//     * @param pigList    
//     * @return 文件下载
//     */
//    public static Workbook exportPicture(List<CourseCodeRecordDTO> pigList) {
//        List<CourseCodeRecordModel> list = new ArrayList<>();
//        pigList.forEach(item -> {
//        	CourseCodeRecordModel e = new CourseCodeRecordModel();
//        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        	e.setCourseCode(item.getCourseCode());
//        	e.setCourseName(item.getCourseName());
//        	e.setExchangeDate(sdf.format(item.getExchangeDate()));
//        	String codeEndDate = null;
//        	if(null != item.getCodeEndDate()){
//        		codeEndDate = sdf.format(item.getCodeEndDate());
//        	}
//        	e.setCodeEndDate(codeEndDate);
//        	e.setUsername(item.getUsername());
//        	
//            String s = item.getCourseCode();
//
//            QrcodeGenerator generate = new SimpleQrcodeGenerator().generate(s);
//                BufferedImage image = generate.getImage();
//            byte[] pngs = imageToBytes(image);
//            e.setQrCode(pngs);
//            list.add(e);
//        });
//        return ExcelExportUtil.exportExcel(new ExportParams(), CourseCodeRecordModel.class, list);
//    }
    
    /**
     * 获取Workbook对象
     *
     * @param pigList    
     * @return 文件下载
     */
    public static Workbook exportPictureCode(List<NailOrder> pigList) {
        List<NailOrderExport> list = new ArrayList<>();
        
        for (NailOrder entity : pigList) {
        	NailOrderExport e = new NailOrderExport();
        	e.setUsername(entity.getUsername());
//        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        	e.setCourseCode(item.getCourseCode());
//        	e.setCourseName(item.getCourseName());
//        	String codeEndDate = null;
//        	if(null != item.getCodeEndDate()){
//        		codeEndDate = sdf.format(item.getCodeEndDate());
//        	}
//        	e.setCodeEndDate(codeEndDate);
//        	e.setIsUseName(item.getIsUseName());
//        	e.setCodeBatch(item.getCodeBatch());
//            String s = item.getCourseCode();
//
//            QrcodeGenerator generate = new SimpleQrcodeGenerator().generate(s);
//                BufferedImage image = generate.getImage();
//            byte[] pngs = imageToBytes(image);
//            e.setQrCode(pngs);
            list.add(e);
		}
        
        return ExcelExportUtil.exportExcel(new ExportParams(), NailOrderExport.class, list);
    }

    /**
     * BufferedImage转byte[]
     *
     * @param bImage BufferedImage对象
     * @return byte[]
     */
    private static byte[] imageToBytes(BufferedImage bImage) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, "png", out);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return out.toByteArray();
    }

}