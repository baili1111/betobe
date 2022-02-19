package com.sikiedu.betobe.controller;

import com.sikiedu.betobe.domain.*;
import com.sikiedu.betobe.dto.BarrageDTO;
import com.sikiedu.betobe.search.engine.SearchUtils;
import com.sikiedu.betobe.service.*;
import com.sikiedu.betobe.utils.PageBean;
import com.sikiedu.betobe.utils.Signature;
import com.sikiedu.betobe.utils.BaseDataUtils;
import com.sikiedu.betobe.utils.VideoUtils;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author zhu
 * @date 2021/5/4 00:08:54
 * @description
 */
@Controller
public class VideoController {

	@Autowired
	private BaseDataUtils baseDataUtils;

	@Autowired
	private HttpSession session;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private SubCategoryService subCategoryService;

	@Autowired
	private VideoService videoService;

	@Autowired
	private TagService tagService;

	@Autowired
	private UserService userService;

	@Autowired
	private VideoBlogCommentService videoBlogCommentService;

	@Autowired
	private SearchUtils searchUtils;

	/**
	 * 删除用户喜爱的视频
	 * @param videoId
	 * @return
	 */
	@RequestMapping("/deleteFavoriteVideoById")
	@ResponseBody
	public String deleteFavoriteVideoById(String videoId) {

		// 拿到视频
		Video video = videoService.findVideoById(videoId);

		// 拿到登录人
		User userInSession = (User) session.getAttribute("user");
		User user = userService.findUserById(userInSession.getId().toString());

		// 操作持久化对象
		video.getFavoriteUsers().remove(user);
		user.getFavoriteVideos().remove(video);

		videoService.saveVideo(video);
		userService.saveUser(user);

		String json = "{\"success\":"+true+"}";
		return json;
	}

	/**
	 * 删除视频
	 * @param id
	 * @param code
	 * @return
	 */
	@RequestMapping("/deleteVideoByIdCode")
	@ResponseBody
	public String deleteVideoByIdCode(String id, Integer code) {

		String json = "{\"success\":"+false+"}";
		Integer codeInSession = (Integer) session.getAttribute("videoCode");
		if (code == codeInSession / 2) {
			// 在数据库中删除
			//Video video = videoService.deleteVideoById(id);

			// 在腾讯云中删除
			//VideoUtils.deleteVideoOnTencent(video.getVideoFileId());
			json = "{\"success\":"+true+"}";
		}
		session.removeAttribute("videoCode");

		return json;
	}

	/**
	 * 删除视频
	 * @param id
	 * @return
	 */
	@DeleteMapping("/deleteVideoByIdDelete")
	@ResponseBody
	public String deleteVideoByIdDelete(String id) {
		// 在数据库中删除
		//Video video = videoService.deleteVideoById(id);

		// 在腾讯云中删除
		//VideoUtils.deleteVideoOnTencent(video.getVideoFileId());

		String json = "{\"success\":"+true+"}";
		return json;
	}

	/**
	 * 删除视频
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteVideoById")
	@ResponseBody
	public String deleteVideoById(String id) {

		//// 在数据库中删除
		//Video video = videoService.deleteVideoById(id);
		//
		//// 在腾讯云中删除
		//VideoUtils.deleteVideoOnTencent(video.getVideoFileId());

		String json = "{\"success\":"+true+"}";
		return json;
	}

	/**
	 * 修改video字段
	 * @param video
	 * @return
	 */
	@RequestMapping("/modifyVideo")
	public String modifyVideo(Video video) {
		// 拿到持久化对象
		Video findVideoById = videoService.findVideoById(video.getId().toString());

		// 操作持久化对象
		findVideoById.setTitle(video.getTitle());
		findVideoById.setContent(video.getContent());
		findVideoById.setKeyword(video.getKeyword());

		videoService.saveVideo(findVideoById);

		return "redirect:/index";
	}

	/**
	 * 去修改video的页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/toModifyVideoById")
	public String toModifyVideoById(String id, Model model) {

		Video video = videoService.findVideoById(id);
		model.addAttribute("video", video);
		User user = (User) session.getAttribute("user");
		baseDataUtils.getData(model, user.getId().toString());

		return "modify-post-video.html";
	}

	@RequestMapping("/showAllNewestVideo")
	public String showAllNewestVideo(Model model, Integer currentPage) {
		// 给页面数据
		baseDataUtils.getRecommendVideo(model);
		baseDataUtils.getNormalRightData(model);

		// 放置pageBean
		PageBean videoPageBean = videoService.getNewestVideoListPageBean(currentPage, 8);

		model.addAttribute("videoPageBean", videoPageBean);

		return "find-all-video-Newest.html";
	}

	@RequestMapping("/showAllPopularVideo")
	public String showAllPopularVideo(Model model, Integer currentPage) {

		// 给页面数据
		baseDataUtils.getRecommendVideo(model);
		baseDataUtils.getNormalRightData(model);

		// 放置pageBean
		PageBean videoPageBean = videoService.getPopularVideoListPageBean(currentPage, 8);

		model.addAttribute("videoPageBean", videoPageBean);

		return "find-all-video-popular.html";
	}

	/**
	 * 用户对视频点踩
	 * @param id
	 * @param videoId
	 * @return
	 */
	@RequestMapping("/addDisagreeVideo")
	@ResponseBody
	public String addDisagreeVideo(String id, String videoId) {

		String json = null;

		User user = userService.findUserById(id);
		Video video = videoService.findVideoById(videoId);

		boolean containsUser = video.getDisagreeUsers().contains(user);
		boolean containsVideo = user.getDisagreeVideos().contains(video);

		// 判断是否点过赞
		if (containsUser && containsVideo){

			video.getDisagreeUsers().remove(user);
			user.getDisagreeVideos().remove(video);

			videoService.saveVideo(video);
			userService.saveUser(user);

			json = "{\"success\":"+false+"}";
			return json;
		}
		video.getDisagreeUsers().add(user);
		user.getDisagreeVideos().add(video);

		videoService.saveVideo(video);
		userService.saveUser(user);

		json = "{\"success\":"+true+"}";
		return json;

	}

	/**
	 * 用户对视频点赞
	 * @param id
	 * @param videoId
	 * @return
	 */
	@RequestMapping("/addAgreeVideo")
	@ResponseBody
	public String addAgreeVideo(String id, String videoId) {

		String json = null;

		User user = userService.findUserById(id);
		Video video = videoService.findVideoById(videoId);

		boolean containsUser = video.getAgreeUsers().contains(user);
		boolean containsVideo = user.getAgreeVideos().contains(video);

		// 判断是否点过赞
		if (containsUser && containsVideo){

			video.getAgreeUsers().remove(user);
			user.getAgreeVideos().remove(video);

			videoService.saveVideo(video);
			userService.saveUser(user);

			json = "{\"success\":"+false+"}";
			return json;
		}
		video.getAgreeUsers().add(user);
		user.getAgreeVideos().add(video);

		videoService.saveVideo(video);
		userService.saveUser(user);

		json = "{\"success\":"+true+"}";
		return json;
	}

	/**
	 * 视频收藏
	 * @param id 登录的用户
	 * @param videoId 登录的用户收藏在看的视频
	 * @return
	 */
	@RequestMapping("/addFavouriteVideo")
	@ResponseBody
	public String addFavouriteVideo(String id, String videoId) {

		String json = null;
		// 当用户收藏后，直接点赞视频
		addAgreeVideo(id, videoId);
		//User userInSession = (User) session.getAttribute("user");
		User user = userService.findUserById(id);
		Video video = videoService.findVideoById(videoId);

		// 判断是否点击过
		boolean containsUser = video.getFavoriteUsers().contains(user);
		boolean containsVideo = user.getFavoriteVideos().contains(video);
		if (containsUser && containsVideo) {

			video.getFavoriteUsers().remove(user);
			user.getFavoriteVideos().remove(video);

			userService.saveUser(user);
			videoService.saveVideo(video);

			json = "{\"success\":"+false+"}";
			return json;
		}

		// 添加关系
		user.getFavoriteVideos().add(video);
		video.getFavoriteUsers().add(user);

		userService.saveUser(user);
		videoService.saveVideo(video);

		json = "{\"success\":"+true+"}";
		return json;
	}

	/**
	 * 请求一条video
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/findVideoById")
	public String findVideoById(String id, Model model) {
		// 根据id返回video数据
		Video video = videoService.findVideoById(id);
		model.addAttribute("video", video);

		// 封装浏览数据
		video.setViewNumber(video.getViewNumber()+1);
		videoService.saveVideo(video);

		// 根据id返回video的评论数据
		List<VideoBlogComment> videoBlogCommentList = videoBlogCommentService.findVideoCommentByVideoId("|Video" + id);

		// 根据评论的id去查询回复
		for (VideoBlogComment videoBlogCommentReply : videoBlogCommentList) {
			// 拿到评论的id
			String commentId = videoBlogCommentReply.getId();
			// 通过评论的id查询所有回复
			List<VideoBlogComment> videoReplyList = videoBlogCommentService.findVideoReplyByCommentId(commentId);
			videoBlogCommentReply.setReplyCommentList(videoReplyList);

			getVideoReply(videoReplyList, commentId);
		}

		model.addAttribute("videoBlogCommentList", videoBlogCommentList);


		// 弹幕
		List<BarrageDTO> barrageList = new ArrayList<>();
		for (Barrage barrage : video.getBarrages()) {
			barrageList.add(new BarrageDTO(barrage.getId(), barrage.getBarrage(), barrage.getTime()));
		}
		Collections.sort(barrageList, new Comparator<BarrageDTO>() {
			@Override
			public int compare(BarrageDTO o1, BarrageDTO o2) {
				return (int) (Double.parseDouble(o1.getTime()) - Double.parseDouble(o2.getTime()));
			}
		});
		model.addAttribute("barrageList", barrageList);



		// 页面右侧数据
		baseDataUtils.getBlogVideoRightData(model);
		// 页面推荐视频
		baseDataUtils.getRecommendVideo(model);
		return "single-video.html";
	}

	// 递归获取评论的回复，深度优先遍历
	private void getVideoReply(List<VideoBlogComment> videoReplyList, String replyWhatReply) {

		if (videoReplyList.isEmpty()) {
			return;
		}

		// 递归
		for (VideoBlogComment videoBlogCommentReplyReply : videoReplyList) {

			// 该回复是回复哪一个回复的
			VideoBlogComment replyWhatReplyObject = videoBlogCommentService.findVideoCommentById(replyWhatReply);
			videoBlogCommentReplyReply.setReplyWhatReply(replyWhatReplyObject);
			// 回复的是哪一个回复
			String replyId = videoBlogCommentReplyReply.getId();
			String[] split2 = replyId.split("\\|");
			// 通过回复的id，去查找所有回复的回复
			List<VideoBlogComment> videoReplyReplyList = videoBlogCommentService.findVideoReplyReplyByReplyId(split2[0], 43+split2[split2.length-1].length());
			videoBlogCommentReplyReply.setReplyCommentList(videoReplyReplyList);

			getVideoReply(videoReplyReplyList, replyId);
		}
	}

	/**
	 * 保存一条video
	 * @param video
	 * @param categoryId
	 * @param subCategoryId
	 * @param tagsinput
	 * @return
	 */
	@RequestMapping("/saveVideo")
	public String saveVideo(Video video, String categoryId, String subCategoryId, String tagsinput) throws IOException {

		// 封装视频长度
		Integer seconds = VideoUtils.getVideoSeconds(video.getVideoFileId());
		video.setSeconds(seconds);

		Category category = categoryService.findCategoryById(categoryId);
		video.setCategory(category);
		// 封装小分类
		SubCategory subCategory = subCategoryService.findSubCategoryById(subCategoryId);

		// 封装创建时间
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		String createTime = format.format(date);
		video.setCreateTime(createTime);


		tagsinput = tagsinput.toUpperCase();
		// 封装标签
		String[] tags = tagsinput.split(",");
		Set<Tag> videoTags = new HashSet<>();
		for (String tagString : tags) {
			Tag tag = null;
			// 如果标签不存在
			if (tagService.findTagByTag(tagString) == null) {
				// 保存该标签
				tag = new Tag(null, tagString, new HashSet<>(), new HashSet<>(), new HashSet<>());
				tag = tagService.saveTag(tag);
			} else {
				// 如果标签存在
				tag = tagService.findTagByTag(tagString);
			}
			// 维护video与tag 的关系
			videoTags.add(tag);
			// 维护subCategory与tag之间的多对多关系
			subCategory.getTags().add(tag);
			tag.getSubCategories().add(subCategory);
		}

		// 维护video与小标签的关系
		video.setSubCategory(subCategory);

		// 维护video与tag的关系
		video.setVideoTags(videoTags);

		// 封装发布video的用户
		User userInSession = (User) session.getAttribute("user");
		video.setUser(userInSession);

		// 封装浏览数目
		video.setViewNumber(0L);

		// 保存video
		videoService.saveVideo(video);

		searchUtils.getLexicon(video);


		return "redirect:/index";
	}

	/**
	 * 上传视频步骤之一获取签名
	 * @return
	 */
	@RequestMapping("/videoSign")
	@ResponseBody
	public String videoSign() {

		Signature sign = new Signature();
		// 设置 App 的云 API 密钥
		//sign.setSecretId("个人 API 密钥中的 Secret Id");
		//sign.setSecretKey("个人 API 密钥中的 Secret Key");
		sign.setSecretId("");
		sign.setSecretKey("");
		sign.setCurrentTime(System.currentTimeMillis() / 1000);
		sign.setRandom(new Random().nextInt(java.lang.Integer.MAX_VALUE));
		sign.setSignValidDuration(3600 * 24 * 2); // 签名有效期：2天
		String signature = null;
		try {
			signature = sign.getUploadSignature();
			System.out.println("signature : " + signature);
		} catch (Exception e) {
			System.out.print("获取签名失败");
			e.printStackTrace();
		}

		return signature;
	}

	/**
	 * 上传视频界面
	 * @param model
	 * @return
	 */
	@RequestMapping("submitPostVideo")
	public String submitPostVideo(Model model) {
		User user = (User) session.getAttribute("user");
		baseDataUtils.getData(model, user.getId().toString());

		List<Category> categoryList = categoryService.findAllCategory();
		model.addAttribute("categoryList", categoryList);

		return "submit-post-video.html";
	}


	@RequestMapping("/singleVideo")
	public String singleVideoV2() {
		return "single-video.html";
	}



}
