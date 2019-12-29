package com.artcweb.baen;

import java.io.Serializable;
import java.util.List;


public class Pager implements Serializable {
	private static final long serialVersionUID = 1L;
	private int pageSize = 2; // 每页多少条
	private int pageNo = 1; // 第几页
	private int totalPages = 10; // 总的页数
	private int totalSize = 0; // 总的记录数
	private int step = 5; // 步长
	private String url = "/"; // 跳转页面url
	private List<?> list;

	public Pager() {
	}

	public Pager(int pageNo, int pageSize) {
		super();
		this.pageSize = pageSize;
		this.pageNo = pageNo;
	}

	public int getBegin() {
		return (this.getPageNo() - 1) * this.getPageSize() + 1;
	}

	public int getEnd() {
		return (this.getPageNo() - 1) * this.getPageSize() + this.getPageSize();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize < 1) {
			this.pageSize = 10;
		} else {
			this.pageSize = pageSize;
		}
	}

	public int getPageNo() {
		if (this.pageNo > this.getTotalPages() && this.getTotalSize() > 0) {
			this.pageNo = this.getTotalPages(); // 如果当前页大于最大页数，则等于最大页数
			// this.pageNo = -1; //修改这里用户手机客户端.
		}
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		if (pageNo < 1) {
			this.pageNo = 1;
		} else {
			this.pageNo = pageNo;
		}
	}

	public int getTotalPages() {
		totalPages = totalPages < 1 ? 1 : totalPages;
		totalPages = getTotalSize() % getPageSize() == 0 ? (getTotalSize() / getPageSize())
				: (getTotalSize() / getPageSize() + 1);
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getStep() {
		if(this.totalPages <= step){
			return this.totalPages;
		}else{
			return step;
		}
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getUrl() {
		return url;
	}
	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
}
