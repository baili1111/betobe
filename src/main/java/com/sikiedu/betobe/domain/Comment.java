package com.sikiedu.betobe.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhu
 * @date 2021/4/26 15:06:58
 * @description
 */
@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// 时间
	private String commentTime;
	// 内容
	private String commentContent;

	// 这条评论的所有回复
	@OneToMany(targetEntity = Reply.class)
	@JoinColumn(name = "comment_id")
	private Set<Reply> replies = new HashSet<>();

	// 这条评论是评论谁的
	@OneToOne
	@JoinColumn(name = "comment_user_id")
	private User commentUser;

	// 这条评论是谁发的
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id")
	private User user;

	// 点赞
	@ManyToMany(mappedBy = "agreeComments")
	private Set<User> agreeUsers = new HashSet<>();

	// 点踩
	@ManyToMany(mappedBy = "disagreeComments")
	private Set<User> disagreeUsers = new HashSet<>();

	protected Comment() {
	}

	public Comment(Long id, String commentTime, String commentContent, Set<Reply> replies, User commentUser, User user, Set<User> agreeUsers, Set<User> disagreeUsers) {
		this.id = id;
		this.commentTime = commentTime;
		this.commentContent = commentContent;
		this.replies = replies;
		this.commentUser = commentUser;
		this.user = user;
		this.agreeUsers = agreeUsers;
		this.disagreeUsers = disagreeUsers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Set<Reply> getReplies() {
		return replies;
	}

	public void setReplies(Set<Reply> replies) {
		this.replies = replies;
	}

	public User getCommentUser() {
		return commentUser;
	}

	public void setCommentUser(User commentUser) {
		this.commentUser = commentUser;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<User> getAgreeUsers() {
		return agreeUsers;
	}

	public void setAgreeUsers(Set<User> agreeUsers) {
		this.agreeUsers = agreeUsers;
	}

	public Set<User> getDisagreeUsers() {
		return disagreeUsers;
	}

	public void setDisagreeUsers(Set<User> disagreeUsers) {
		this.disagreeUsers = disagreeUsers;
	}
}
