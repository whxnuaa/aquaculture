package com.jit.aquaculture.commons.pages;


import org.hibernate.validator.constraints.Range;


public class PageQO<T> {

	/**
	 * 按创建时间倒序排序
	 */
	public static final String ORDER_BY_CREATE_TIME_DESC = "create_time desc";

	@Range(min = 1, max = Integer.MAX_VALUE)
	private int pageNum = 1;

	@Range(min = 1, max = Integer.MAX_VALUE)
	private int pageSize = 10;

	private String orderBy;

	private T condition;

	public static String getOrderByCreateTimeDesc() {
		return ORDER_BY_CREATE_TIME_DESC;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public T getCondition() {
		return condition;
	}

	public void setCondition(T condition) {
		this.condition = condition;
	}

	public PageQO() {
	}

	public PageQO(int pageNum, int pageSize) {
		super();
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}

	public int getOffset() {
		return (this.pageNum - 1) * this.pageSize;
	}

}
