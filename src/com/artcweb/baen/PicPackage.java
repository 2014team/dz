
package com.artcweb.baen;

public class PicPackage extends BaseBean {

	private static final long serialVersionUID = 1L;

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
	 * 小图片地址
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
	 * 用户数量
	 */
	private Integer useCount;

	/**
	 * 来源 0：定制   1：模板
	 */
	private Integer comeFrom;

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

	public Integer getPins() {
		return pins;
	}

	public void setPins(Integer pins) {
		this.pins = pins;
	}

	public Integer getUseCount() {

		return useCount;
	}

	public void setUseCount(Integer useCount) {

		this.useCount = useCount;
	}

	public String getMinImageUrl() {
		return minImageUrl;
	}

	public void setMinImageUrl(String minImageUrl) {
		this.minImageUrl = minImageUrl;
	}

	public Integer getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(Integer comeFrom) {
		this.comeFrom = comeFrom;
	}

}
