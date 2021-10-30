
package com.artcweb.bean;

public class NailOrder extends BaseBean {

	private static final long serialVersionUID = 1L;

	// 买家名称
	private String username;

	// 手机号码
	private String mobile;

	// 执行步骤
	private String step;

	// 当前步骤
	private String currentStep;

	// 图片路径
	private String imageUrl;
	// 效果图片路径
	private String resultImageUrl;

	// 来源 0:后台1:H5
	private String comefrom;

	// 图钉类型Id
	private String nailConfigId;

	// 画框Id
	private String nailPictureFrameId;

	// 宽
	private String width;

	// 高
	private String height;

	// 钉子统计详情
	private String nailCountDetail;

	// 图纸名称
	private String imageName;

	// H5传值1:订单已经生成
	private String thirdFlag;

	// 款式Id
	private String nailDrawingStockId;
	// 出库标识1:出库
	protected int checkoutFlag;
	
	// 画框尺寸ID
	protected Integer nailPictureFrameStockId;
	
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

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(String currentStep) {
		this.currentStep = currentStep;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getComefrom() {
		return comefrom;
	}

	public void setComefrom(String comefrom) {
		this.comefrom = comefrom;
	}

	public String getNailConfigId() {
		return nailConfigId;
	}

	public void setNailConfigId(String nailConfigId) {
		this.nailConfigId = nailConfigId;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getNailCountDetail() {
		return nailCountDetail;
	}

	public void setNailCountDetail(String nailCountDetail) {
		this.nailCountDetail = nailCountDetail;
	}

	public String getNailPictureFrameId() {
		return nailPictureFrameId;
	}

	public void setNailPictureFrameId(String nailPictureFrameId) {
		this.nailPictureFrameId = nailPictureFrameId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getThirdFlag() {
		return thirdFlag;
	}

	public void setThirdFlag(String thirdFlag) {
		this.thirdFlag = thirdFlag;
	}

	public String getResultImageUrl() {
		return resultImageUrl;
	}

	public void setResultImageUrl(String resultImageUrl) {
		this.resultImageUrl = resultImageUrl;
	}

	public String getNailDrawingStockId() {
		return nailDrawingStockId;
	}

	public void setNailDrawingStockId(String nailDrawingStockId) {
		this.nailDrawingStockId = nailDrawingStockId;
	}

	public int getCheckoutFlag() {
		return checkoutFlag;
	}

	public void setCheckoutFlag(int checkoutFlag) {
		this.checkoutFlag = checkoutFlag;
	}

	public Integer getNailPictureFrameStockId() {
		return nailPictureFrameStockId;
	}

	public void setNailPictureFrameStockId(Integer nailPictureFrameStockId) {
		this.nailPictureFrameStockId = nailPictureFrameStockId;
	}



	
	
	
}
