package com.sikiedu.betobe.controller;

import com.sikiedu.betobe.domain.Category;
import com.sikiedu.betobe.service.CategoryService;
import com.sikiedu.betobe.utils.BaseDataUtils;
import com.sikiedu.betobe.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhu
 * @date 2021/5/7 18:03:02
 * @description
 */
@Controller
public class CategoryController {

	@Autowired
	private BaseDataUtils baseDataUtils;

	@Autowired
	private CategoryService categoryService;

	@RequestMapping("/categories")
	public String categories(Model model, String categoryId, Integer currentPage) {
		// 获得推荐视频
		baseDataUtils.getRecommendVideo(model);
		// 获取右侧栏数据
		baseDataUtils.getNormalRightData(model);

		if (categoryId == null) {
			categoryId = "1";
		}
		Category category = categoryService.findCategoryById(categoryId);

		// 该分类下的视频
		PageBean videoPageBean = categoryService.getVideoPageBean(currentPage, 8, categoryId);

		model.addAttribute("videoPageBean", videoPageBean);
		model.addAttribute("category", category);


		return "categories.html";
	}

}
