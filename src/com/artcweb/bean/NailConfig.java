
package com.artcweb.bean;

/**
 * @ClassName: NailConfig
 * @Description: 数量配置
 * @author zhuzq
 * @date 2020年11月25日 下午10:03:45
 */
public class NailConfig extends BaseBean {

	private static final long serialVersionUID = 1L;

	// 钉子类型
	private String nailType;
	// 钉子数量  （每公斤颗数）
	private String nailNumber;

	public String getNailType() {
		return nailType;
	}

	public void setNailType(String nailType) {
		this.nailType = nailType;
	}

	public String getNailNumber() {
		return nailNumber;
	}

	public void setNailNumber(String nailNumber) {
		this.nailNumber = nailNumber;
	}

}
