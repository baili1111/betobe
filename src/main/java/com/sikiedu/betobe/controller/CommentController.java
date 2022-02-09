package com.sikiedu.betobe.controller;

import com.sikiedu.betobe.domain.Comment;
import com.sikiedu.betobe.domain.Reply;
import com.sikiedu.betobe.domain.User;
import com.sikiedu.betobe.service.CommentService;
import com.sikiedu.betobe.service.ReplyService;
import com.sikiedu.betobe.service.UserService;
import com.sikiedu.betobe.utils.BaseDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author zhu
 * @date 2021/4/28 12:38:45
 * @description
 */
@Controller
public class CommentController {

	@Autowired
	private UserService userService;

	@Autowired
	private HttpSession session;

	@Autowired
	private CommentService commentService;

	@Autowired
	private ReplyService replyService;

	@Autowired
	private BaseDataUtils baseDataUtils;

	/**
	 * 评论的取消不赞同
	 * @param id 评论的id
	 * @param userId 查看谁主页就是谁的id
	 * @return
	 */
	@RequestMapping("/deleteCommentDisagree")
	public String deleteCommentDisagree(String id, String userId) {

		User userInSession = (User) session.getAttribute("user");
		User findUser = userService.findUserById(userInSession.getId().toString());
		Comment comment = commentService.findCommentById(id);

		findUser.getDisagreeComments().remove(comment);
		comment.getDisagreeUsers().remove(findUser);

		userService.saveUser(findUser);
		commentService.saveComment(comment);

		return  "redirect:/findUserCommentsById?id=" + userId;
	}

	/**
	 * 评论的不赞同
	 * @param id 评论的id
	 * @param userId 查看谁主页就是谁的id
	 * @return
	 */
	@RequestMapping("/addCommentDisagree")
	public String addCommentDisagree(String id, String userId) {

		User userInSession = (User) session.getAttribute("user");
		User findUser = userService.findUserById(userInSession.getId().toString());
		Comment comment = commentService.findCommentById(id);

		findUser.getDisagreeComments().add(comment);
		comment.getDisagreeUsers().add(findUser);

		userService.saveUser(findUser);
		commentService.saveComment(comment);

		return  "redirect:/findUserCommentsById?id=" + userId;
	}
	/**
	 * 取消评论的赞同
	 * @param id 评论的id
	 * @param userId 查看谁主页就是谁的id
	 * @return
	 */
	@RequestMapping("/deleteCommentAgree")
	public String deleteCommentAgree(String id, String userId) {

		User userInSession = (User) session.getAttribute("user");

		// 拿到用户的持久化对象
		User findUser = userService.findUserById(userInSession.getId().toString());
		Comment comment = commentService.findCommentById(id);

		// 维护关系
		findUser.getAgreeComments().remove(comment);
		comment.getAgreeUsers().remove(findUser);

		userService.saveUser(findUser);
		commentService.saveComment(comment);

		return "redirect:/findUserCommentsById?id=" + userId;
	}

	/**
	 * 评论的赞同
	 * @param id 这个评论的id
	 * @param userId 查看谁主页就是谁的id
	 * @return
	 */
	@RequestMapping("/addCommentAgree")
	public String addCommentAgree(String id, String userId) {

		User userInSession = (User) session.getAttribute("user");

		// 拿到用户的持久化对象
		User findUser = userService.findUserById(userInSession.getId().toString());
		Comment comment = commentService.findCommentById(id);

		// 维护关系
		findUser.getAgreeComments().add(comment);
		comment.getAgreeUsers().add(findUser);

		userService.saveUser(findUser);
		commentService.saveComment(comment);

		return "redirect:/findUserCommentsById?id=" + userId;
	}

	/**
	 * 给页面数据
	 * @param id 查询谁的主页，就是谁的id
	 * @param model
	 * @param replyCOrR 表示回复的是评论还是回复（类型）
	 * @param replyCOrRId 表示回复的那一条（评论或者回复）的id（具体到哪一条）
	 * @return
	 */
	@RequestMapping("/findUserCommentsById")
	public String findUserCommentsById(String id, Model model, String replyCOrR, String replyCOrRId) {
		User user = userService.findUserById(id);



		// 回复的是那一条回复，回复的是那一条评论
		Object replyCOrRObject = null;
		if (replyCOrR == null){
			//replyCOrR = "comment";
			model.addAttribute("commentReply", "comment");
		}
		if (replyCOrRId != null) {
			// 回复的是那一条回复，回复的是那一条评论
			// 根据这条评论或者回复，获取发送这条消息的人
			if ("comment".equals(replyCOrR)) {
				replyCOrRObject = commentService.findCommentById(replyCOrRId);

				model.addAttribute("replyCOrRObject", (Comment)replyCOrRObject);
			} else if ("reply".equals(replyCOrR)) {
				replyCOrRObject = replyService.findReplyById(replyCOrRId);
				model.addAttribute("replyCOrRObject", (Reply)replyCOrRObject);
			}
			model.addAttribute("commentReply", "reply");
		}
		model.addAttribute("replyCOrR", replyCOrR);
		model.addAttribute("replyCOrRId", replyCOrRId);

		baseDataUtils.getData(model, id);

		model.addAttribute("page", "Comments");
		return "profile-comments.html";
	}

	/**
	 * 保存一条评论
	 * @param comment
	 * @param userId 被评论的人
	 * @return
	 */
	@RequestMapping("/saveComment")
	public String saveComment(Comment comment, String userId) {

		// 封装时间
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		String createTime = format.format(date);
		comment.setCommentTime(createTime);

		// 评论哪一个User的
		User commentUser = userService.findUserById(userId);
		comment.setCommentUser(commentUser);

		// 哪一个User发表的评论（登录的人发表的评论）
		User userInSession = (User) session.getAttribute("user");
		comment.setUser(userInSession);

		// 保存comment
		commentService.saveComment(comment);

		return "redirect:/findUserCommentsById?id=" + userId;
	}


}
