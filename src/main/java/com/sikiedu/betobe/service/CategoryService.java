package com.sikiedu.betobe.service;

import com.sikiedu.betobe.domain.Category;
import com.sikiedu.betobe.utils.PageBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhu
 * @date 2021/5/1 14:50:22
 * @description
 */
public interface CategoryService {

	/**
	 * 查找所有的category
	 * @return
	 */
	List<Category> findAllCategory();

	/**
	 * 通过id查找category
	 * @param categoryId
	 * @return
	 */
	Category findCategoryById(String categoryId);

	/**
	 * 按 category 分页查询
	 * @param currentPage 当前页
	 * @param pageSize 页面大小
	 * @param categoryId 查找这个分类下的视频
	 * @return
	 */
	PageBean getVideoPageBean(Integer currentPage, int pageSize, String categoryId);
}
