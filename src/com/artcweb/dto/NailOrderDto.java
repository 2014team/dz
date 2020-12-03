package com.artcweb.dto;

import com.artcweb.bean.NailOrder;

public class NailOrderDto extends NailOrder {
	private static final long serialVersionUID = 1L;

	private String nailType;

	private String colorName;

	private String status;
	
	private String toDay;

	public String getNailType() {
		return nailType;
	}

	public void setNailType(String nailType) {
		this.nailType = nailType;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getToDay() {
		return toDay;
	}

	public void setToDay(String toDay) {
		this.toDay = toDay;
	}
	
	

}
