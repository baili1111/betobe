package com.sikiedu.betobe.service;

import com.sikiedu.betobe.domain.SubCategory;
import com.sikiedu.betobe.utils.PageBean;

/**
 * @author zhu
 * @date 2021/5/2 15:42:44
 * @description
 */
public interface SubCategoryService {


	/**
	 * 通过id 查找SubCategory
	 * @param subCategoryId
	 * @return
	 */
	SubCategory findSubCategoryById(String subCategoryId);

	/**
	 * 获取小分类的分页视频
	 * @param currentPage
	 * @param pageSize
	 * @param subCategoryId
	 * @return
	 */
	PageBean getVideoPageBean(Integer currentPage, int pageSize, String subCategoryId);
}
