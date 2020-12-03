package com.artcweb.bean;


import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

/**
 * 导出数据模板
 *
 */
@ExcelTarget(value = "slaughterDataModel")
public class NailOrderExport {
	 
	@Excel(name = "图片", needMerge=true,mergeVertical=true,mergeRely={1}, imageType=1,type=2)
	private  String imageUrl;
	
	@Excel(name = "编号")
	private String indexId;
	// 钉子数量
	@Excel(name = "数量")
	private String nailNumber;

	// 公斤数（每公斤颗数）
	private String kilogramNumber;

	// 需要重量
	@Excel(name = "重量")
	private String requreWeight;

	// 需要包数、件数
	@Excel(name = "包数")
	private String requrePieces;

	// rgb值
	//@Excel(name = "颜色")
	private String rgb;

	public String getIndexId() {
		return indexId;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	public String getNailNumber() {
		return nailNumber;
	}

	public void setNailNumber(String nailNumber) {
		this.nailNumber = nailNumber;
	}

	public String getKilogramNumber() {
		return kilogramNumber;
	}

	public void setKilogramNumber(String kilogramNumber) {
		this.kilogramNumber = kilogramNumber;
	}

	public String getRequreWeight() {
		return requreWeight;
	}

	public void setRequreWeight(String requreWeight) {
		this.requreWeight = requreWeight;
	}

	public String getRequrePieces() {
		return requrePieces;
	}

	public void setRequrePieces(String requrePieces) {
		this.requrePieces = requrePieces;
	}

	public String getRgb() {
		return rgb;
	}

	public void setRgb(String rgb) {
		this.rgb = rgb;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
