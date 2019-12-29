
package com.artcweb.baen;

public class AdminCategory extends BaseBean {

	private static final long serialVersionUID = 1L;

	/**
	 * 分类ID
	 */
	private Integer categoryId;

	/**
	 * 分类名
	 */
	private String categoryName;

	public Integer getCategoryId() {

		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {

		this.categoryId = categoryId;
	}

	public String getCategoryName() {

		return categoryName;
	}

	public void setCategoryName(String categoryName) {

		this.categoryName = categoryName;
	}

}
