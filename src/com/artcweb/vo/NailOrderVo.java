package com.artcweb.vo;

import com.artcweb.bean.NailOrder;

public class NailOrderVo extends NailOrder{
	private static final long serialVersionUID = 1L;
	
	private String keyword;
	private String secretKey;
	private String searchKey;
	private String searchValue;
	
	// 时间
	private String createDateStr;

	// 查询-开始时间
	private String beginDate;

	// 查询-结束时间
	private String endDate;
	
	private String nailConfigId;
	
	// ids
	private String array;

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

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getNailConfigId() {
		return nailConfigId;
	}

	public void setNailConfigId(String nailConfigId) {
		this.nailConfigId = nailConfigId;
	}

	public String getArray() {
		return array;
	}

	public void setArray(String array) {
		this.array = array;
	}

	
	
	
}
