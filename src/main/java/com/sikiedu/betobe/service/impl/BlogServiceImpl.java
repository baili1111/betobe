package com.sikiedu.betobe.service.impl;

import com.sikiedu.betobe.domain.Blog;
import com.sikiedu.betobe.repository.BlogRepository;
import com.sikiedu.betobe.service.BlogService;
import com.sikiedu.betobe.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhu
 * @date 2021/5/2 22:08:39
 * @description
 */
@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogRepository blogRepository;

	@Override
	public void saveBlog(Blog blog) {
		blogRepository.save(blog);
	}

	@Override
	public Blog findBlogById(String id) {
		return blogRepository.findById(new Long(id)).get();
	}

	@Override
	public List<Blog> findAllBlog() {
		return (List<Blog>) blogRepository.findAll();
	}

	@Override
	public PageBean findAllBlogPageBean(Integer currentPage, int pageSize) {

		int totalCount = blogRepository.findAllBlogCount();
		PageBean pageBean = new PageBean(currentPage, totalCount, pageSize);
		List<Blog> list = blogRepository.getAllBlogPageList(pageBean.getStart(), pageBean.getPageSize());
		pageBean.setList(list);

		return pageBean;
	}
}
