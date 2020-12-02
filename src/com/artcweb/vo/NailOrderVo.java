package com.artcweb.vo;

import com.artcweb.baen.NailOrder;

public class NailOrderVo extends NailOrder{
	private static final long serialVersionUID = 1L;
	
	private String keyword;
	private String secretKey;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	

}
