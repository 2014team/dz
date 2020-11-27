package com.artcweb.baen;


import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

/**
 * 导出数据模板
 *
 */
@ExcelTarget(value = "slaughterDataModel")
public class NailOrderExport {
	
	@Excel(name = "买家名称", height = 40, width = 30)
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
//	@Excel(name = "兑换课程", height = 40, width = 30)
//	private String courseName;
//	
//	@Excel(name = "活动名称", height = 40, width = 30)
//	private String codeBatch;
//	
//	@Excel(name = "是否使用", height = 40, width = 30)
//	private String isUseName;
//	
//	@Excel(name = "有效期", height = 40, width = 30)
//	private String codeEndDate;
//	
//	@Excel(name = "二维码", type = 2, width = 20, height = 40, imageType = 2)
//	private byte[] qrCode;
//
//	
//	public String getIsUseName() {
//		return isUseName;
//	}
//
//	public void setIsUseName(String isUseName) {
//		this.isUseName = isUseName;
//	}
//	
//	public String getCourseCode() {
//		return courseCode;
//	}
//
//	public void setCourseCode(String courseCode) {
//		this.courseCode = courseCode;
//	}
//
//	public String getCourseName() {
//		return courseName;
//	}
//
//	public void setCourseName(String courseName) {
//		this.courseName = courseName;
//	}
//
//	public String getCodeEndDate() {
//		return codeEndDate;
//	}
//
//	public void setCodeEndDate(String codeEndDate) {
//		this.codeEndDate = codeEndDate;
//	}
//
//	public byte[] getQrCode() {
//		return qrCode;
//	}
//
//	public void setQrCode(byte[] qrCode) {
//		this.qrCode = qrCode;
//	}
//
//
//	public String getCodeBatch() {
//		return codeBatch;
//	}
//
//	public void setCodeBatch(String codeBatch) {
//		this.codeBatch = codeBatch;
//	}
}
