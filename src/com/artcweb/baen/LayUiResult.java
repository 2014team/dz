
package com.artcweb.baen;

import java.io.Serializable;

public class LayUiResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private int code;

	private String msg = "";

	private Object data;

	/**
	 * 总的记录数
	 */
	private int count; // 总的记录数

	/**
	 * 页码
	 */
	private int page = 1;// 第几页

	/**
	 * 每页多少条
	 */
	private int limit = 10; // 每页多少条

	private int totalPages = 10; // 总的页数

	public LayUiResult() {
		super();
	}

	public LayUiResult(int page, int limit) {
		super();
		this.page = page;
		this.limit = limit;
	}

	public int getTotalPages() {

		totalPages = totalPages < 1 ? 1 : totalPages;
		totalPages = getCount() % getLimit() == 0 ? (getCount() / getLimit()) : (getCount() / getLimit() + 1);
		return totalPages;
	}

	public void setTotalPages(int totalPages) {

		this.totalPages = totalPages;
	}

	public int getCode() {

		return code;
	}

	public void setCode(int code) {

		this.code = code;
	}

	public String getMsg() {

		return msg;
	}

	public void setMsg(String msg) {

		this.msg = msg;
	}

	public Object getData() {

		return data;
	}

	public void setData(Object data) {

		this.data = data;
	}

	public int getCount() {

		return count;
	}

	public void setCount(int count) {

		this.count = count;
	}

	public int getPage() {

		return page;
	}

	public void setPage(int page) {

		this.page = page;
	}

	public int getLimit() {

		return limit;
	}

	public void setLimit(int limit) {

		this.limit = limit;
	}

	public int getBegin() {

		return (this.getPage() - 1) * this.getLimit();
	}

	/**
	 * @Title: success
	 * @Description: 成功
	 */
	public void success() {

		this.code = 200;
		this.msg = "请求成功";
	}

	public void success(Object data) {

		success();
		this.data = data;
	}

	public void success(String msg) {

		success();
		this.msg = msg;
	}

	/**
	 * @Title: failure
	 * @Description: 失败
	 */
	public void failure() {

		this.code = 500;
		this.msg = "请求失败";
	}

	/**
	 * @Title: failure
	 * @Description: 失败
	 */
	public void failure(String msg) {

		failure();
		this.msg = msg;
	}

}
