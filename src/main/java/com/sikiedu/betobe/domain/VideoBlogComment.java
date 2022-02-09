package com.sikiedu.betobe.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zhu
 * @date 2021/5/9 19:13:29
 * @description
 */
@Entity
public class VideoBlogComment {

	@Id
	private String id;

	private String commentTime;
	private String commentContent;


	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id")
	private User user;

	// 不映射到数据库中
	// 该评论下的所有的回复
	@Transient
	private List<VideoBlogComment> replyCommentList = new ArrayList<>();

	// 该回复是回复了那一条回复
	@Transient
	private VideoBlogComment replyWhatReply;

	// 用户赞评论
	@ManyToMany(mappedBy = "agreeVideoBlogComment")
	private Set<User> agreeVideoBlogCommentUsers;

	// 用户踩评论
	@ManyToMany(mappedBy = "disagreeVideoBlogComment")
	private Set<User> disagreeVideoBlogCommentUsers;

	protected VideoBlogComment() { }

	public VideoBlogComment(String id, String commentTime, String commentContent, User user, List<VideoBlogComment> replyCommentList, VideoBlogComment replyWhatReply, Set<User> agreeVideoBlogCommentUsers, Set<User> disagreeVideoBlogCommentUsers) {
		this.id = id;
		this.commentTime = commentTime;
		this.commentContent = commentContent;
		this.user = user;
		this.replyCommentList = replyCommentList;
		this.replyWhatReply = replyWhatReply;
		this.agreeVideoBlogCommentUsers = agreeVideoBlogCommentUsers;
		this.disagreeVideoBlogCommentUsers = disagreeVideoBlogCommentUsers;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<VideoBlogComment> getReplyCommentList() {
		return replyCommentList;
	}

	public void setReplyCommentList(List<VideoBlogComment> replyCommentList) {
		this.replyCommentList = replyCommentList;
	}

	public VideoBlogComment getReplyWhatReply() {
		return replyWhatReply;
	}

	public void setReplyWhatReply(VideoBlogComment replyWhatReply) {
		this.replyWhatReply = replyWhatReply;
	}

	public Set<User> getAgreeVideoBlogCommentUsers() {
		return agreeVideoBlogCommentUsers;
	}

	public void setAgreeVideoBlogCommentUsers(Set<User> agreeVideoBlogCommentUsers) {
		this.agreeVideoBlogCommentUsers = agreeVideoBlogCommentUsers;
	}

	public Set<User> getDisagreeVideoBlogCommentUsers() {
		return disagreeVideoBlogCommentUsers;
	}

	public void setDisagreeVideoBlogCommentUsers(Set<User> disagreeVideoBlogCommentUsers) {
		this.disagreeVideoBlogCommentUsers = disagreeVideoBlogCommentUsers;
	}
}
