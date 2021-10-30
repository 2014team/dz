package com.artcweb.vo;

import org.apache.commons.lang.StringUtils;

import com.artcweb.bean.NailOrder;
import com.artcweb.enums.NailTypeEnum;

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
	
	private String checkoutFlagName;
	
	private String nailTypeIdName;
	
	private String searchCondition;
	
	
	private String nailImageSizeId;
	
	private Integer frameId;
	
	

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

	public String getCheckoutFlagName() {
		if(super.checkoutFlag == 1){
			return "是";
		}else if(super.checkoutFlag == 0){
			return "否";
		}else{
			return "";
		}
		
	}

	public void setCheckoutFlagName(String checkoutFlagName) {
		this.checkoutFlagName = checkoutFlagName;
	}

	public String getNailTypeIdName() {
		String nailTypeIdName = null;
		if(StringUtils.isNotEmpty(nailConfigId)){
			nailTypeIdName =NailTypeEnum.getDisplayNameByValue(Integer.valueOf(nailConfigId));
		}
		return nailTypeIdName;
	}

	public void setNailTypeIdName(String nailTypeIdName) {
		this.nailTypeIdName = nailTypeIdName;
	}

	public String getSearchCondition() {
		String result = "";
		if(StringUtils.isNotEmpty(array)){
			String[] idArr = array.split(",");
			result = "勾选 "+idArr.length+" 条数据";
		}
		
		if(StringUtils.isNotEmpty(beginDate) && StringUtils.isNotEmpty(endDate) ){
			if(StringUtils.isNotEmpty(result)){
				result = result + " + ";
			}
			result = result + "日期选择："+beginDate+" ~ " +endDate;
		}
		if(StringUtils.isNotEmpty(getNailTypeIdName())){
			if(StringUtils.isNotEmpty(result)){
				result = result + " + ";
			}
			result = result + "图钉类型："+getNailTypeIdName();
		}
		if(StringUtils.isNotEmpty(getCheckoutFlagName())){
			if(StringUtils.isNotEmpty(result)){
				result = result + " + ";
			}
			result = result + "出库："+getCheckoutFlagName();
		}
		
		result = "刷选条件："+result;
		return result;
		
	}

	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	public String getNailImageSizeId() {
		return nailImageSizeId;
	}

	public void setNailImageSizeId(String nailImageSizeId) {
		this.nailImageSizeId = nailImageSizeId;
	}

	public Integer getFrameId() {
		return frameId;
	}

	public void setFrameId(Integer frameId) {
		this.frameId = frameId;
	}

		
	
	
}
