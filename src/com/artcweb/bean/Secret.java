
package com.artcweb.bean;

/**
 * @ClassName: Secret
 * @Description:秘钥
 */

public class Secret extends BaseBean {

	private static final long serialVersionUID = 1L;

	// 秘钥
	private String secretKey;

	// 订单Id
	private Integer orderId;

	// 状态 0：未使用 1：已使用
	private Integer status;
	
	// 使用项目名称
	private String siteName;

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	

}
