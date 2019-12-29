
package com.artcweb.baen;

public class AdminRight extends BaseBean {

	private static final long serialVersionUID = 1L;

	/**
	 * 权限规则
	 */
	private String rightRule;

	/**
	 * 权限名称
	 */
	private String rightName;

	/**
	 * 所属分类ID
	 */
	private Integer rightCategoryId;
	
	/**
	 * 所属分类名称
	 */
	private Integer rightCategoryName;

	public String getRightRule() {

		return rightRule;
	}

	public void setRightRule(String rightRule) {

		this.rightRule = rightRule;
	}

	public String getRightName() {

		return rightName;
	}

	public void setRightName(String rightName) {

		this.rightName = rightName;
	}

	public Integer getRightCategoryId() {

		return rightCategoryId;
	}

	public void setRightCategoryId(Integer rightCategoryId) {

		this.rightCategoryId = rightCategoryId;
	}

	
	public Integer getRightCategoryName() {
	
		return rightCategoryName;
	}

	
	public void setRightCategoryName(Integer rightCategoryName) {
	
		this.rightCategoryName = rightCategoryName;
	}
	
}
