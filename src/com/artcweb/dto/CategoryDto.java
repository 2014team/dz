package com.artcweb.dto;

import java.util.List;

import com.artcweb.bean.Category;

public class CategoryDto extends Category{


	private static final long serialVersionUID = 1L;
	
	private List<PicPackageDto> picPackageList;

	
	public List<PicPackageDto> getPicPackageList() {
		return picPackageList;
	}

	
	public void setPicPackageList(List<PicPackageDto> picPackageList) {
		this.picPackageList = picPackageList;
	}
	
	

	
	
	
	

}
