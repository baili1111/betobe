package com.sikiedu.betobe.service.impl;

import com.sikiedu.betobe.domain.SubCategory;
import com.sikiedu.betobe.domain.Video;
import com.sikiedu.betobe.repository.SubCategoryRepository;
import com.sikiedu.betobe.repository.VideoRepository;
import com.sikiedu.betobe.service.SubCategoryService;
import com.sikiedu.betobe.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhu
 * @date 2021/5/2 15:45:34
 * @description
 */
@Service
public class subCategoryServiceImpl implements SubCategoryService {

	@Autowired
	private SubCategoryRepository subCategoryRepository;

	@Autowired
	private VideoRepository videoRepository;

	@Override
	public SubCategory findSubCategoryById(String subCategoryId) {
		return subCategoryRepository.findById(new Long(subCategoryId)).get();
	}

	@Override
	public PageBean getVideoPageBean(Integer currentPage, int pageSize, String subCategoryId) {

		// 准备参数 该subCategoryId下的所有视频的个数
		int totalCount = videoRepository.getVideoTotalCountBySubCategoryId(subCategoryId);

		// 封装PageBean
		PageBean pageBean = new PageBean(currentPage, totalCount, pageSize);

		// 封装PageBean里面的list
		List<Video> list = videoRepository.getVideoPageListBySubCategoryId(pageBean.getStart(), pageBean.getPageSize(), subCategoryId);
		pageBean.setList(list);

		return pageBean;
	}
}
