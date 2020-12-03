
package com.artcweb.bean;

public class Category extends BaseBean {

	private static final long serialVersionUID = 1L;

	/**
	 * 类别名称
	 */
	private String categoryName;

	/**
	 * 排序
	 */
	private Integer sort;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
