
package com.artcweb.baen;

import java.util.List;

public class User extends BaseBean {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * 排序
	 */
	private Integer sort;

	private List<Order> orderList;

	private int orderCount;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

}
