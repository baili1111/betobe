package com.sikiedu.betobe.utils;

import java.util.List;

/**
 * @author zhu
 * @date 2021/5/7 21:44:59
 * @description
 */
public class PageBean {

	// 总页数
	private Integer totalPage;
	// 总条数
	private Integer totalCount;
	// 起始页
	private Integer currentPage;
	// 页面大小
	private Integer pageSize;
	// list
	private List list;

	public PageBean(Integer currentPage, Integer totalCount, Integer pageSize) {
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		this.pageSize = pageSize;

		if (this.currentPage == null){
			this.currentPage = 1;
		}
		if (this.pageSize == null){
			this.pageSize = 12;
		}

		// 计算totalPage
		this.totalPage = (int)Math.ceil(1.0 * this.totalCount / this.pageSize);

		if (this.currentPage > this.totalPage) {
			this.currentPage = this.totalPage;
		}
		if (this.currentPage < 1) {
			this.currentPage = 1;
		}

	}

	public Integer getStart(){
		return (this.currentPage - 1) * this.pageSize;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
}
