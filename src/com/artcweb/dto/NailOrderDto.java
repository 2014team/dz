package com.artcweb.dto;

import com.artcweb.baen.NailOrder;

public class NailOrderDto extends NailOrder{
	private static final long serialVersionUID = 1L;
	
	private String nailType;

	
	public String getNailType() {
		return nailType;
	}

	
	public void setNailType(String nailType) {
		this.nailType = nailType;
	}
	
	


}
