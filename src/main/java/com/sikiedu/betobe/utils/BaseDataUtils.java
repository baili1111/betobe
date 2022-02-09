package com.sikiedu.betobe.utils;

import com.sikiedu.betobe.domain.*;
import com.sikiedu.betobe.recommend.RecommendVideo;
import com.sikiedu.betobe.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author zhu
 * @date 2021/4/30 18:49:30
 * @description
 */

public class BaseDataUtils {

	@Autowired
	private UserService userService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private VideoService videoService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private TagService tagService;

	@Autowired
	private RecommendVideo recommendVideo;

	@Autowired
	private HttpSession session;

	/**
	 * 根据登录用户曾经点赞和点踩来推荐视频
	 * @param model
	 */
	public void getRecommendVideo(Model model) {
		User userInSession = (User) session.getAttribute("user");
		List<Video> recommendVideoList = recommendVideo.getRecommendVideoByContent(userInSession.getId().toString(), 6);
		model.addAttribute("recommendVideoList", recommendVideoList);
	}

	/**
	 * 获取博客和Video页面右侧栏中的数据
	 * @param model
	 */
	public void getBlogVideoRightData(Model model){

		// 四个视频浏览数最多
		List<Video> popularVideoList = videoService.findPopularVideoList(4);
		// category
		List<Category> categoryList = categoryService.findAllCategory();
		// 四个视频最新视频
		List<Video> newestVideoList = videoService.findNewestVideoList(4);

		// tag
		List<Tag> tagList = tagService.findAllTag();

		model.addAttribute("popularVideoList", popularVideoList);
		model.addAttribute("categoryBlogVideoRightList", categoryList);
		model.addAttribute("tagBlogVideoRightList", tagList);
		model.addAttribute("newestVideoList", newestVideoList);

	}

	/**
	 * 获取普通页面中右侧栏的数据
	 * @param model
	 */
	public void getNormalRightData(Model model) {

		// category
		List<Category> categoryList = categoryService.findAllCategory();

		// 四个视频最新视频
		List<Video> newestVideoList = videoService.findNewestVideoList(4);


		// 四个视频浏览数最多
		List<Video> popularVideoList = videoService.findPopularVideoList(4);

		// tag
		List<Tag> tagList = tagService.findAllTag();

		model.addAttribute("popularVideoList", popularVideoList);
		model.addAttribute("categoryNormalRightList", categoryList);
		model.addAttribute("tagNormalRightList", tagList);
		model.addAttribute("newestVideoList", newestVideoList);

	}

	/**
	 * 获取网页底部的数据
	 * @param model
	 */
	public void getFooterData(ServletRequest model){

		// 3个视频
		List<Video> videoList = videoService.findNormalVideo(3);

		// tag
		List<Tag> tagList = tagService.findAllTag();

		model.setAttribute("videoFooterList", videoList);
		model.setAttribute("tagFooterList", tagList);
	}

	/**
	 * 提供页面头部的数据
	 * @param request
	 */
	public void getHeaderData(ServletRequest request) {
		List<Category> categoryHeaderList = categoryService.findAllCategory();
		request.setAttribute("categoryHeaderList", categoryHeaderList);
	}

	/**
	 * 提供页面需要的数据
	 * @param model
	 * @param id
	 */
	public void getData(Model model, String id) {
		User user = userService.findUserById(id);
		// 查找所有评论这个用户的评论
		List<Comment> commentList = commentService.findCommentListByCommentUserId(id);
		model.addAttribute("commentList", commentList);
		// 查找当前用户被几个人所关注
		List<User> followersList = userService.findFollowersListById(id);
		model.addAttribute("followersList", followersList);

		// 用户发布视频的个数
		int userSubmissionVideoNum = videoService.getUserSubmissionVideoNum(id);
		// 用户喜欢视频的个数
		int userFavoriteVideoNum = videoService.getUserFavoriteVideoNum(id);

		model.addAttribute("user", user);
		model.addAttribute("followersNum", followersList.size());
		model.addAttribute("userSubmissionVideoNum", userSubmissionVideoNum);
		model.addAttribute("userFavoriteVideoNum", userFavoriteVideoNum);
	}


}
