package com.artcweb.bean;
 
public class Analys extends NailCount{

	private static final long serialVersionUID = 1L;
	
	// 总钉子数
	private String totalNailNumber;
	// 总重量
	private String totalWeight;
	// 总包数
	private String totalrPieces;
	
	// 订单数量
	private int rgbSize;
	
	public String getTotalNailNumber() {
		return totalNailNumber;
	}
	public void setTotalNailNumber(String totalNailNumber) {
		this.totalNailNumber = totalNailNumber;
	}
	public String getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}
	public String getTotalrPieces() {
		return totalrPieces;
	}
	public void setTotalrPieces(String totalrPieces) {
		this.totalrPieces = totalrPieces;
	}
	public Analys( int sort,String rgb,String newSerialNumber) {
		super();
		this.setSort(sort);
		this.setRgb(rgb);
		this.setIndexId(newSerialNumber);
	}
	public Analys() {
		super();
	}
	public int getRgbSize() {
		return rgbSize;
	}
	public void setRgbSize(int rgbSize) {
		this.rgbSize = rgbSize;
	}
	
	
	

}
