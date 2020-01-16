package com.jit.aquaculture.commons.pages;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import com.jit.aquaculture.commons.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;


public class PageVO<T> implements Model {

	private static final long serialVersionUID = -4426958360243585882L;

	private Integer pageNum;

	private Integer pageSize;

	private Long total;

	private Integer pages;

	private List<T> list;

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public PageVO() {
	}

	public PageVO(PageQO pageQO) {
		this.setPageNum(pageQO.getPageNum());
		this.setPageSize(pageQO.getPageSize());
	}

	public PageVO(List<T> poList) {
		BeanUtils.copyProperties(new PageInfo<>(poList), this);
	}

	public static <T> PageVO<T> build(List<T> poList) {
		return new PageVO<>(poList);
	}

	/**
	 * @desc 构建一个分页VO对象
	 *
	 * @param page 数据库查出来的分页数据列表
	 */
	public static <T> PageVO<T> build(Page<T> page) {
		PageVO<T> pageVO = new PageVO<>();
		BeanUtils.copyProperties(page.toPageInfo(), pageVO);
		return pageVO;
	}

	/**
	 * @desc 构建一个分页VO对象
	 * 试用场景为：从数据库取出的PO列表不做任何处理，转化为VO列表返回
	 *
	 * @param page 数据库查出来的分页数据列表
	 * @param voClazz 要转为的VO类
	 */
	public static <T, E> PageVO<T> build(Page<E> page, Class<T> voClazz) {

		PageVO<T> pageVO = new PageVO<>();
		BeanUtils.copyProperties(page, pageVO, "list");

		try {
			List<T> voList = Lists.newArrayList();
			List<E> poList = page.getResult();
			if (!CollectionUtils.isEmpty(poList)) {
				for (E e : poList) {
					T t = voClazz.newInstance();
					BeanUtils.copyProperties(e, t);
					voList.add(t);
				}
			}
			pageVO.setList(voList);
		} catch (IllegalAccessException | InstantiationException e) {
			throw new RuntimeException(e);
		}

		return pageVO;
	}

	/**
	 * @desc 构建一个分页VO对象
	 * 试用场景为：将处理好的VO列表封装返回
	 *
	 * @param poPage 数据库查出来的分页数据
	 * @param voList vo数据列表
	 */
	public static <T, E> PageVO<T> build(Page<E> poPage, List<T> voList) {
		PageVO<T> page = new PageVO<>();
		BeanUtil.copyProperties(poPage, page, "list");
		page.setList(voList == null ? Lists.newArrayList() : voList);
		return page;
	}

	public static int getPages(long total, int pageSize) {
		if (total == 0 || pageSize == 0) {
			return 0;
		}
		return (int) (total % pageSize == 0 ? (total / pageSize) : (total / pageSize + 1));
	}

	public int getPages(){
		return getPages(this.total, this.pageSize);
	}
}
