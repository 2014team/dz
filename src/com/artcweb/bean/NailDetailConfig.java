
package com.artcweb.bean;

/**
 * @ClassName: NailConfig
 * @Description: 详细配置
 * @author zhuzq
 * @date 2020年11月25日 下午10:03:45
 */
public class NailDetailConfig extends BaseBean {

	private static final long serialVersionUID = 1L;

	// RGB值
	private String rgb;
	private String rgbName;

	// 新编号
	private String newSerialNumber;

	// 旧编号
	private String oldSerialNumber;

	// （小钉）每包克数
	private String nailSmallWeight;
	
	// （迷你）每包克数
	private String nailMiniWeight;

	// （大钉）每包克数
	private String nailBigWeight;
	
	private int sort;
	

	public String getRgbName() {
		return rgbName;
	}

	public void setRgbName(String rgbName) {
		this.rgbName = rgbName;
	}

	public String getRgb() {
		return rgb;
	}

	public void setRgb(String rgb) {
		this.rgb = rgb;
	}

	public String getNewSerialNumber() {
		return newSerialNumber;
	}

	public void setNewSerialNumber(String newSerialNumber) {
		this.newSerialNumber = newSerialNumber;
	}

	public String getOldSerialNumber() {
		return oldSerialNumber;
	}

	public void setOldSerialNumber(String oldSerialNumber) {
		this.oldSerialNumber = oldSerialNumber;
	}

	public String getNailSmallWeight() {
		return nailSmallWeight;
	}

	public void setNailSmallWeight(String nailSmallWeight) {
		this.nailSmallWeight = nailSmallWeight;
	}

	public String getNailBigWeight() {
		return nailBigWeight;
	}

	public void setNailBigWeight(String nailBigWeight) {
		this.nailBigWeight = nailBigWeight;
	}


	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	
	public String getNailMiniWeight() {
		return nailMiniWeight;
	}

	
	public void setNailMiniWeight(String nailMiniWeight) {
		this.nailMiniWeight = nailMiniWeight;
	}
	
	
}
