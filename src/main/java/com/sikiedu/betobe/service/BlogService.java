package com.sikiedu.betobe.service;

import com.sikiedu.betobe.domain.Blog;
import com.sikiedu.betobe.utils.PageBean;

import java.util.List;

/**
 * @author zhu
 * @date 2021/5/2 22:08:28
 * @description
 */
public interface BlogService {
	/**
	 * 保存一条blog
	 * @param blog
	 */
	void saveBlog(Blog blog);

	/**
	 * 根据id 查找blog
	 * @param id
	 * @return
	 */
	Blog findBlogById(String id);

	/**
	 * 查找所有博客
	 * @return
	 */
	List<Blog> findAllBlog();

	/**
	 * 博客分页
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	PageBean findAllBlogPageBean(Integer currentPage, int pageSize);
}
