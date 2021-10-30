
package com.artcweb.vo;

import com.artcweb.bean.Secret;

public class SecretVo extends Secret {

	private static final long serialVersionUID = 1L;

	// 秘钥数量
	private Integer secretNumber;

	// 秘钥长度
	private Integer secretDigit;

	//生成秘钥时间
	private String createDateStr;

	//查询-开始时间
	private String beginDate;

	//查询-结束时间
	private String endDate;

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

	public Integer getSecretNumber() {
		return secretNumber;
	}

	public void setSecretNumber(Integer secretNumber) {
		this.secretNumber = secretNumber;
	}

	public Integer getSecretDigit() {
		return secretDigit;
	}

	public void setSecretDigit(Integer secretDigit) {
		this.secretDigit = secretDigit;
	}

}