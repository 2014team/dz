package com.artcweb.bean;

public class Inventory extends BaseBean{
	
	private static final long serialVersionUID = 490963226063812176L;
	/**
	*图钉-rgb
	*/
	private String rgb;
	/**
	 * 图钉-新编号
	 */
	private String newSerialNumber;
	
	/**
	 * 图钉-库存(单位千克)
	 */
	private String stock_1;
	private String stock_2;
	private String stock_3;
	private String stock_4;
	
	
	/**
	 * 图钉-需要出库
	 */
	private String ckeckOutNumber;
	
	/**
	 * 图纸-ID
	 */
	private Integer drawDrawingId;
	/**
	 * 图纸-款式
	 */
	private String style;
	
	/**
	 * 图纸-数量
	 */
	private Integer number;
	
	/**
	 * 图纸-需要出库
	 */
	private String drawCkeckOutNumber;
	
	private String nailConfigId;
	

	public String getRgb() {
		return rgb;
	}

	public void setRgb(String rgb) {
		this.rgb = rgb;
	}

	public String getNewSerialNumber() {
		return newSerialNumber;
	}

	public void setNewSerialNumber(String newSerialNumber) {
		this.newSerialNumber = newSerialNumber;
	}

	
	public String getStock_1() {
		return stock_1;
	}

	public void setStock_1(String stock_1) {
		this.stock_1 = stock_1;
	}

	public String getStock_2() {
		return stock_2;
	}

	public void setStock_2(String stock_2) {
		this.stock_2 = stock_2;
	}

	public String getStock_3() {
		return stock_3;
	}

	public void setStock_3(String stock_3) {
		this.stock_3 = stock_3;
	}

	public String getStock_4() {
		return stock_4;
	}

	public void setStock_4(String stock_4) {
		this.stock_4 = stock_4;
	}

	public String getCkeckOutNumber() {
		return ckeckOutNumber;
	}

	public void setCkeckOutNumber(String ckeckOutNumber) {
		this.ckeckOutNumber = ckeckOutNumber;
	}
	
	
	

	public Integer getDrawDrawingId() {
		return drawDrawingId;
	}

	public void setDrawDrawingId(Integer drawDrawingId) {
		this.drawDrawingId = drawDrawingId;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getDrawCkeckOutNumber() {
		return drawCkeckOutNumber;
	}

	public void setDrawCkeckOutNumber(String drawCkeckOutNumber) {
		this.drawCkeckOutNumber = drawCkeckOutNumber;
	}

	public Inventory(Integer id,String rgb, String newSerialNumber, String stock_1, String stock_2, String stock_3, String stock_4) {
		super();
		this.id = id;
		this.rgb = rgb;
		this.newSerialNumber = newSerialNumber;
		this.stock_1 = stock_1;
		this.stock_2 = stock_2;
		this.stock_3 = stock_3;
		this.stock_4 = stock_4;
	}

	public Inventory(Integer drawDrawingId, String style, Integer number) {
		super();
		this.drawDrawingId = drawDrawingId;
		this.style = style;
		this.number = number;
	}

	public String getNailConfigId() {
		return nailConfigId;
	}

	public void setNailConfigId(String nailConfigId) {
		this.nailConfigId = nailConfigId;
	}
	

}
