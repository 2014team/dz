package com.artcweb.vo;

import com.artcweb.bean.Order;

public class OrderVo extends Order{

	//秘钥
	private String secretKey;
	
	private static final long serialVersionUID = 1L;

	
	public String getSecretKey() {
		return secretKey;
	}

	
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	

}
