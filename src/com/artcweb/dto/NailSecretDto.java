package com.artcweb.dto;

import com.artcweb.bean.NailSecret;

public class NailSecretDto extends NailSecret{
	
	private static final long serialVersionUID = 1L;
	private String username;
	private String mobile;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	

}
