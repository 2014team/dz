package com.artcweb.vo;

import com.artcweb.baen.NailOrder;

public class NailOrderVo extends NailOrder{
	private static final long serialVersionUID = 1L;
	
	private String keyword;
	private String secretKey;
	private String searchKey;
	private String searchValue;

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

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	
	
	
}
