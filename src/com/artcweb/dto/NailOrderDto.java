package com.artcweb.dto;

import com.artcweb.baen.NailOrder;

public class NailOrderDto extends NailOrder{
	private static final long serialVersionUID = 1L;
	
	private String nailType;

	private String colorName;
	
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
	
	


}
