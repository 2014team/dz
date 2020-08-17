package com.artcweb.dto;

import com.artcweb.baen.Secret;

public class SecretDto extends Secret{
	
	private static final long serialVersionUID = 1L;
	private Integer packageId;
	private String packageName;
	private String mobile;
	
	public Integer getPackageId() {
		return packageId;
	}
	
	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}
	
	public String getPackageName() {
		return packageName;
	}
	
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	

}
