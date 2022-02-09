package com.sikiedu.betobe.controller;

import com.sikiedu.betobe.domain.User;
import com.sikiedu.betobe.domain.VideoBlogComment;
import com.sikiedu.betobe.service.UserService;
import com.sikiedu.betobe.service.VideoBlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author zhu
 * @date 2021/5/9 19:39:56
 * @description
 */
@Controller
public class VideoBlogCommentController {

	@Autowired
	private VideoBlogCommentService videoBlogCommentService;

	@Autowired
	private HttpSession session;

	@Autowired
	private UserService userService;

	/**
	 * 登录的人需要踩哪个视频评论
	 * @param videoBlogCommentId
	 * @return
	 */
	@RequestMapping("/addDisagreeVideoBlogComment")
	@ResponseBody
	public String addDisagreeVideoBlogComment(String videoBlogCommentId) {
		String json = null;
		User userInSession = (User) session.getAttribute("user");
		User user = userService.findUserById(userInSession.getId().toString());
		VideoBlogComment videoBlogComment = videoBlogCommentService.findVideoCommentById(videoBlogCommentId);

		// 判断是否点击过
		boolean containsUser = videoBlogComment.getDisagreeVideoBlogCommentUsers().contains(user);
		boolean containsVideoBlogComment = user.getDisagreeVideoBlogComment().contains(videoBlogComment);
		if (containsUser && containsVideoBlogComment) {

			videoBlogComment.getDisagreeVideoBlogCommentUsers().remove(user);
			user.getDisagreeVideoBlogComment().remove(videoBlogComment);

			videoBlogCommentService.saveVideoBlogComment(videoBlogComment);
			userService.saveUser(user);

			int num = videoBlogComment.getDisagreeVideoBlogCommentUsers().size();
			json = "{\"success\":"+false+",\"num\":"+num+"}";
			return json;
		}

		videoBlogComment.getDisagreeVideoBlogCommentUsers().add(user);
		user.getDisagreeVideoBlogComment().add(videoBlogComment);

		videoBlogCommentService.saveVideoBlogComment(videoBlogComment);
		userService.saveUser(user);

		int num = videoBlogComment.getDisagreeVideoBlogCommentUsers().size();
		json = "{\"success\":"+true+",\"num\":"+num+"}";
		return json;
	}

	/**
	 * 登录的人需要赞哪个视频评论
	 * @param videoBlogCommentId
	 * @return
	 */
	@RequestMapping("/addAgreeVideoBlogComment")
	@ResponseBody
	public String addAgreeVideoBlogComment(String videoBlogCommentId) {
		String json = null;
		User userInSession = (User) session.getAttribute("user");
		// 获取登录的人和视频下的评论
		User user = userService.findUserById(userInSession.getId().toString());
		VideoBlogComment videoBlogComment = videoBlogCommentService.findVideoCommentById(videoBlogCommentId);

		// 判断是否点击过
		boolean containsUser = videoBlogComment.getAgreeVideoBlogCommentUsers().contains(user);
		boolean containsVideoBlogComment = user.getAgreeVideoBlogComment().contains(videoBlogComment);
		if (containsUser && containsVideoBlogComment) {

			// 移除关系
			videoBlogComment.getAgreeVideoBlogCommentUsers().remove(user);
			user.getAgreeVideoBlogComment().remove(videoBlogComment);

			videoBlogCommentService.saveVideoBlogComment(videoBlogComment);
			userService.saveUser(user);

			int num = videoBlogComment.getAgreeVideoBlogCommentUsers().size();
			json = "{\"success\":"+false+",\"num\":"+num+"}";
			return json;
		}

		// 添加关系
		videoBlogComment.getAgreeVideoBlogCommentUsers().add(user);
		user.getAgreeVideoBlogComment().add(videoBlogComment);

		videoBlogCommentService.saveVideoBlogComment(videoBlogComment);
		userService.saveUser(user);

		int num = videoBlogComment.getAgreeVideoBlogCommentUsers().size();
		json = "{\"success\":"+true+",\"num\":"+num+"}";
		return json;
	}

	/**
	 * 添加视频评论里面回复的回复
	 * 回复的回复的回复
	 * @param videoBlogComment
	 * @param videoReplyReplyId
	 * @return
	 */
	@RequestMapping("/addVideoReplyReply")
	@ResponseBody
	public String addVideoReplyReply(VideoBlogComment videoBlogComment, String videoReplyReplyId) {

		String json = null;
		// 封装id
		if (videoReplyReplyId.contains("|ReplyReply")) {
			// 防止无限增长
			// 071911d7-d1b8-44bd-8454-b47de247dbbb
			// ReplyReply26652007-621a-49a7-bc7e-da40ecace195
			// Reply3a246370-f4f4-438b-ae26-164446377ce1
			// Video1
			//System.out.println(videoReplyReplyId);
			String[] split = videoReplyReplyId.split("\\|");
			videoBlogComment.setId(UUID.randomUUID().toString() + "|ReplyReply" + split[0] + "|" + split[2] + "|" + split[3]);


		} else {
			videoBlogComment.setId(UUID.randomUUID().toString() + "|ReplyReply" + videoReplyReplyId);
		}

		// 维护发布评论的用户关系
		User userInSession = (User) session.getAttribute("user");
		videoBlogComment.setUser(userInSession);

		// 封装创建时间
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		String createTime = format.format(date);
		videoBlogComment.setCommentTime(createTime);

		videoBlogCommentService.saveVideoBlogComment(videoBlogComment);

		// 拿到回复videoReplyReplyId
		VideoBlogComment videoBlogReply = videoBlogCommentService.findVideoCommentById(videoReplyReplyId);
		// 该回复是回复的哪一个人
		String replyUserDisplayName = videoBlogReply.getUser().getDisplayName();
		// 该回复是回复的哪一个回复的内容
		String replyContent = videoBlogReply.getCommentContent();


		json = "{\"success\":"+true+",\"replyUserDisplayName\":\""+replyUserDisplayName+"\", \"replyContent\":\""+replyContent+"\", \"videoReplyReplyReplyId\":\""+videoBlogComment.getId()+"\"}";



		return json;

	}

	/**
	 * 视频评论里面回复
	 * @param videoBlogComment
	 * @param videoCommentId
	 * @return
	 */
	@RequestMapping("/addVideoReply")
	@ResponseBody
	public String addVideoReply(VideoBlogComment videoBlogComment, String videoCommentId) {

		String json = null;
		// 封装id
		videoBlogComment.setId(UUID.randomUUID().toString() + "|Reply" + videoCommentId);

		// 维护发布评论的用户关系
		User userInSession = (User) session.getAttribute("user");
		videoBlogComment.setUser(userInSession);

		// 封装创建时间
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		String createTime = format.format(date);
		videoBlogComment.setCommentTime(createTime);

		videoBlogComment = videoBlogCommentService.saveVideoBlogComment(videoBlogComment);

		String videoBlogReplyId = videoBlogComment.getId();


		json = "{\"success\":"+true+",\"videoBlogReplyId\":\""+videoBlogReplyId+"\"}";

		return json;
	}

	@RequestMapping("/addBlogComment")
	@ResponseBody
	public String addBlogComment(VideoBlogComment videoBlogComment, String blogId) {

		String json = null;

		// 封装id
		videoBlogComment.setId(UUID.randomUUID().toString() + "|Blog" + blogId);

		// 维护发布评论的用户关系
		User userInSession = (User) session.getAttribute("user");
		videoBlogComment.setUser(userInSession);

		// 封装创建时间
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		String createTime = format.format(date);
		videoBlogComment.setCommentTime(createTime);

		videoBlogComment = videoBlogCommentService.saveVideoBlogComment(videoBlogComment);

		json = "{\"success\":"+true+",\"videoBlogCommentId\":\""+videoBlogComment.getId()+"\"}";

		return json;
	}


	/**
	 * 保存一条视频评论
	 * @param videoBlogComment
	 * @param videoId 评论的是哪个视频
	 * @return
	 */
	@RequestMapping("/addVideoComment")
	@ResponseBody
	public String addVideoComment(VideoBlogComment videoBlogComment, String videoId) {

		String json = null;

		// 封装id
		videoBlogComment.setId(UUID.randomUUID().toString() + "|Video" + videoId);

		// 维护发布评论的用户关系
		User userInSession = (User) session.getAttribute("user");
		videoBlogComment.setUser(userInSession);

		// 封装创建时间
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		String createTime = format.format(date);
		videoBlogComment.setCommentTime(createTime);

		videoBlogComment = videoBlogCommentService.saveVideoBlogComment(videoBlogComment);

		json = "{\"success\":"+true+",\"videoBlogCommentId\":\""+videoBlogComment.getId()+"\"}";

		return json;
	}

}
