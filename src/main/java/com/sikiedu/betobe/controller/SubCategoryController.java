package com.sikiedu.betobe.controller;

import com.sikiedu.betobe.domain.SubCategory;
import com.sikiedu.betobe.service.SubCategoryService;
import com.sikiedu.betobe.utils.BaseDataUtils;
import com.sikiedu.betobe.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhu
 * @date 2021/5/9 13:50:05
 * @description
 */
@Controller
public class SubCategoryController {

	@Autowired
	private SubCategoryService subCategoryService;

	@Autowired
	private BaseDataUtils baseDataUtils;

	@RequestMapping("/subCategory")
	public String subCategory(Model model, String subCategoryId, Integer currentPage) {

		// 获得推荐视频
		baseDataUtils.getRecommendVideo(model);
		// 得到页面右侧的数据
		baseDataUtils.getNormalRightData(model);

		if (subCategoryId == null) {
			subCategoryId = "1";
		}
		SubCategory subCategory = subCategoryService.findSubCategoryById(subCategoryId);

		// 获得小分类下视频list
		PageBean videoPageBean = subCategoryService.getVideoPageBean(currentPage, 8, subCategoryId);


		model.addAttribute("subCategory", subCategory);
		model.addAttribute("videoPageBean", videoPageBean);

		return "sub-categories.html";
	}

}
