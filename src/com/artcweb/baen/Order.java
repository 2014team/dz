
package com.artcweb.baen;

public class Order extends BaseBean {

	private static final long serialVersionUID = 1L;

	/**
	 * 订单ID
	 */
	private Integer orderId;

	/**
	 * 手机号码
	 */
	private String mobile;
	
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * 当前步骤
	 */
	private String currentStep;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 套餐ID
	 */
	private Integer packageId;

	/**
	 * 套餐名称
	 */
	private String packageName;

	/**
	 * imageUrl图片
	 */
	private String imageUrl;

	/**
	 * 小图片
	 */
	private String minImageUrl;

	/**
	 * 执行步骤
	 */
	private String step;

	/**
	 * 钉子数量
	 */
	private Integer pins;

	/**
	 * 0:新建模板 1：选择模板
	 */
	private int template;

	/**
	 * 0:定制 1：模板
	 */

	private int comeFrom;

	public Integer getOrderId() {

		return orderId;
	}

	public void setOrderId(Integer orderId) {

		this.orderId = orderId;
	}

	public String getMobile() {

		return mobile;
	}

	public void setMobile(String mobile) {

		this.mobile = mobile;
	}

	public String getCurrentStep() {

		return currentStep;
	}

	public void setCurrentStep(String currentStep) {

		this.currentStep = currentStep;
	}

	public Integer getSort() {

		return sort;
	}

	public void setSort(Integer sort) {

		this.sort = sort;
	}

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

	public String getImageUrl() {

		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {

		this.imageUrl = imageUrl;
	}

	public String getStep() {

		return step;
	}

	public void setStep(String step) {

		this.step = step;
	}

	public int getTemplate() {

		return template;
	}

	public void setTemplate(int template) {

		this.template = template;
	}

	public Integer getPins() {
		return pins;
	}

	public void setPins(Integer pins) {
		this.pins = pins;
	}

	public String getMinImageUrl() {
		return minImageUrl;
	}

	public void setMinImageUrl(String minImageUrl) {
		this.minImageUrl = minImageUrl;
	}

	public int getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(int comeFrom) {
		this.comeFrom = comeFrom;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	

}
