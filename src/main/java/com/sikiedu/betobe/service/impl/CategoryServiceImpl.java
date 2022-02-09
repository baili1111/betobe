package com.sikiedu.betobe.service.impl;

import com.sikiedu.betobe.domain.Category;
import com.sikiedu.betobe.domain.Video;
import com.sikiedu.betobe.repository.CategoryRepository;
import com.sikiedu.betobe.repository.VideoRepository;
import com.sikiedu.betobe.service.CategoryService;
import com.sikiedu.betobe.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhu
 * @date 2021/5/1 14:50:34
 * @description
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private VideoRepository videoRepository;

	@Override
	public List<Category> findAllCategory() {
		return (List<Category>) categoryRepository.findAll();
	}

	@Override
	public Category findCategoryById(String categoryId) {
		return categoryRepository.findById(new Long(categoryId)).get();
	}

	@Override
	public PageBean getVideoPageBean(Integer currentPage, int pageSize, String categoryId) {

		// 准备参数 该categoryId下的所有视频
		int totalCount = videoRepository.getVideoTotalCountByCategoryId(categoryId);

		// 封装PageBean
		PageBean pageBean = new PageBean(currentPage, totalCount, pageSize);

		// 封装PageBean里面的list
		List<Video> list = videoRepository.getVideoPageListByCategoryId(pageBean.getStart(), pageBean.getPageSize(), categoryId);
		pageBean.setList(list);

		return pageBean;
	}
}
