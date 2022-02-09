package com.sikiedu.betobe.controller;

import com.sikiedu.betobe.domain.Reply;
import com.sikiedu.betobe.domain.User;
import com.sikiedu.betobe.service.CommentService;
import com.sikiedu.betobe.service.ReplyService;
import com.sikiedu.betobe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhu
 * @date 2021/4/28 21:00:12
 * @description
 */
@Controller
public class ReplyController {

	@Autowired
	private HttpSession session;

	@Autowired
	private CommentService commentService;

	@Autowired
	private ReplyService replyService;

	@Autowired
	private UserService userService;

	/**
	 * 取消回复的不赞同
	 * @param id 回复的id
	 * @param userId 查看谁主页就是谁的id
	 * @return
	 */
	@RequestMapping("/deleteReplyDisagree")
	public String deleteReplyDisagree(String id, String userId) {

		// 获取登录人
		User userInSession = (User) session.getAttribute("user");
		// 拿到登录人的持久化对象
		User findUser = userService.findUserById(userInSession.getId().toString());

		// 拿到reply的持久化对象
		Reply reply = replyService.findReplyById(id);

		findUser.getDisagreeReplies().remove(reply);
		reply.getDisagreeUsers().remove(findUser);

		userService.saveUser(findUser);
		replyService.saveReply(reply);

		return "redirect:/findUserCommentsById?id=" + userId;
	}

	/**
	 * 回复的不赞同
	 * @param id 回复的id
	 * @param userId 查看谁主页就是谁的id
	 * @return
	 */
	@RequestMapping("/addReplyDisagree")
	public String addReplyDisagree(String id, String userId) {

		// 获取登录人
		User userInSession = (User) session.getAttribute("user");
		// 拿到登录人的持久化对象
		User findUser = userService.findUserById(userInSession.getId().toString());

		// 拿到reply的持久化对象
		Reply reply = replyService.findReplyById(id);

		findUser.getDisagreeReplies().add(reply);
		reply.getDisagreeUsers().add(findUser);

		userService.saveUser(findUser);
		replyService.saveReply(reply);

		return "redirect:/findUserCommentsById?id=" + userId;
	}

	/**
	 * 取消回复的赞同
	 * @param id 回复的id
	 * @param userId 查看谁主页就是谁的id
	 * @return
	 */
	@RequestMapping("/deleteReplyAgree")
	public String deleteReplyAgree(String id, String userId) {

		// 获取登录人
		User userInSession = (User) session.getAttribute("user");
		// 拿到登录人的持久化对象
		User findUser = userService.findUserById(userInSession.getId().toString());

		// 拿到reply的持久化对象
		Reply reply = replyService.findReplyById(id);

		findUser.getAgreeReplies().remove(reply);
		reply.getAgreeUsers().remove(findUser);

		userService.saveUser(findUser);
		replyService.saveReply(reply);

		return "redirect:/findUserCommentsById?id=" + userId;
	}

	/**
	 * 回复的赞同
	 * @param id 回复的id
	 * @param userId 查看谁主页就是谁的id
	 * @return
	 */
	@RequestMapping("/addReplyAgree")
	public String addReplyAgree(String id, String userId) {

		// 获取登录人
		User userInSession = (User) session.getAttribute("user");
		// 拿到登录人的持久化对象
		User findUser = userService.findUserById(userInSession.getId().toString());

		// 拿到reply的持久化对象
		Reply reply = replyService.findReplyById(id);

		findUser.getAgreeReplies().add(reply);
		reply.getAgreeUsers().add(findUser);

		userService.saveUser(findUser);
		replyService.saveReply(reply);

		return "redirect:/findUserCommentsById?id=" + userId;
	}

	/**
	 * 保存一条回复
	 * @param reply 回复的内容
	 * @param userId 查询谁的主页，就是谁的id
	 * @param replyCOrR 根据类型维护关系
	 * @param replyCOrRId 被回复的评论或者回复的 id
	 * @return
	 */
	@RequestMapping("/saveReply")
	public String saveReply(Reply reply, String userId, String replyCOrR, String replyCOrRId) {

		// 封装时间
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		String createTime = format.format(date);
		reply.setReplyTime(createTime);

		// 回复人
		User user = (User) session.getAttribute("user");
		reply.setUser(user);

		if ("comment".equals(replyCOrR)) {
			// 登录人回复的是哪个评论
			reply.setComment(commentService.findCommentById(replyCOrRId));
		}

		if ("reply".equals(replyCOrR)) {
			// 登录人点击reply后。登录人自己写的回复要保存
			Reply temp = replyService.findReplyById(replyCOrRId);
			Reply saveReply = replyService.saveReply(reply);

			// 获得temp的所有回复，在temp的所有回复中添加我们刚刚保存的回复
			temp.getReplies().add(saveReply);
			reply.setReply(temp);

			replyService.saveReply(temp);
		}

		// 回复内容
		//reply.setReplyContent();


		// 有哪些回复是回复本条回复的
		//reply.setReplies();

		replyService.saveReply(reply);


		return "redirect:/findUserCommentsById?id=" + userId;
	}
}
