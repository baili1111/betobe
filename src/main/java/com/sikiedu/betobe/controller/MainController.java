package com.sikiedu.betobe.controller;

import com.sikiedu.betobe.domain.Category;
import com.sikiedu.betobe.domain.Video;
import com.sikiedu.betobe.recommend.RecommendVideo;
import com.sikiedu.betobe.service.CategoryService;
import com.sikiedu.betobe.service.VideoService;
import com.sikiedu.betobe.utils.BaseDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author zhu
 * @date 2021/4/2 13:55:35
 * @description 主控制层，跳转到各个页面
 */
@Controller
public class MainController {

	//@RequestMapping("/session/invalid")
	//public String sessionInvalid() {
	//	System.out.println("Session失效");
	//	return "redirect:/loginBetobe";
	//}

	@Autowired
	private BaseDataUtils baseDataUtils;

	@Autowired
	private RecommendVideo recommendVideo;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private VideoService videoService;

	@RequestMapping("")
	public String toIndex() {
		return "redirect:/index";
	}

	@RequestMapping("/index")
	public String index(Model model) {

		// 分类
		List<Category> categoryList = categoryService.findAllCategory();

		// 最新视频
		List<Video> newestVideoList = videoService.findNewestVideoList(8);

		// 最受欢迎视频
		List<Video> popularVideoList = videoService.findPopularVideoList(8);

		model.addAttribute("categoryList", categoryList);
		model.addAttribute("newestVideoList", newestVideoList);
		model.addAttribute("popularVideoList", popularVideoList);

		// 推荐视频
		baseDataUtils.getRecommendVideo(model);
		return "index.html";
	}

	@RequestMapping("/profileBindingSuccess")
	public String profileBinding() {
		return "profile-binding-success.html";
	}


	@RequestMapping("aboutUs")
	public String aboutUs(Model model) {

		baseDataUtils.getNormalRightData(model);

		return "about-us.html";
	}

	@RequestMapping("allVideo")
	public String allVideo() {
		return "all-video.html";
	}

	@RequestMapping("archives")
	public String archives() {
		return "archives.html";
	}

	@RequestMapping("blog")
	public String blog() {
		return "blog.html";
	}

	@RequestMapping("blogSinglePost")
	public String blogSinglePost() {
		return "blog-single-post.html";
	}



	@RequestMapping("contactUs")
	public String contactUs() {
		return "contact-us.html";
	}

	@RequestMapping("loginBetobe")
	public String login() {
		return "login.html";
	}

	@RequestMapping("loginForgotPass")
	public String loginForgotPass() {
		return "login-forgot-pass.html";
	}

	@RequestMapping("loginRegister")
	public String loginRegister() {
		return "login-register.html";
	}

	@RequestMapping("profileAboutMe")
	public String profileAboutMe() {
		return "profile-about-me.html";
	}

	@RequestMapping("profileComments")
	public String profileComments() {
		return "profile-comments.html";
	}

	@RequestMapping("proFileFavorite")
	public String proFileFavorite() {
		return "profile-favorite.html";
	}

	@RequestMapping("profileFollowers")
	public String profileFollowers() {
		return "profile-followers.html";
	}


	@RequestMapping("profileSettings")
	public String profileSettings() {
		return "profile-settings.html";
	}


	@RequestMapping("searchResults")
	public String searchResults() {
		return "search-results.html";
	}



	@RequestMapping("/profileVideo")
	public String profilePageV2() {

		return "profile-video.html";
	}

	@RequestMapping("termsCondition")
	public String termsCondition() {
		return "terms-condition.html";
	}

}
