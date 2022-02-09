package com.sikiedu.betobe.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhu
 * @date 2021/4/26 15:10:00
 * @description
 */
// 回复，回复的是谁（评论？回复）
@Entity
public class Reply {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String replyContent;

	private String replyTime;

	// 谁发的回复
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id")
	private User user;

	// 这条回复是回复哪一个评论的

	@ManyToOne(targetEntity = Comment.class)
	@JoinColumn(name = "comment_id")
	private Comment comment;

	// 这条回复是回复哪一个回复的
	@ManyToOne(targetEntity = Reply.class)
	@JoinColumn(name = "reply_id")
	private Reply reply;

	// 有哪些回复是回复本条回复的
	@ManyToMany
	@JoinTable(
			name = "reply_reply",
			joinColumns = @JoinColumn(name = "reply_id"),
			inverseJoinColumns = @JoinColumn(name = "reply_reply_id")
	)
	private Set<Reply> replies = new HashSet<>();

	@ManyToMany(mappedBy = "disagreeReplies")
	private Set<User> disagreeUsers = new HashSet<>();

	@ManyToMany(mappedBy = "agreeReplies")
	private Set<User> agreeUsers = new HashSet<>();


	protected Reply() {
	}

	public Reply(Long id, String replyContent, String replyTime, User user, Comment comment, Reply reply, Set<Reply> replies, Set<User> disagreeUsers, Set<User> agreeUsers) {
		this.id = id;
		this.replyContent = replyContent;
		this.replyTime = replyTime;
		this.user = user;
		this.comment = comment;
		this.reply = reply;
		this.replies = replies;
		this.disagreeUsers = disagreeUsers;
		this.agreeUsers = agreeUsers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public Reply getReply() {
		return reply;
	}

	public void setReply(Reply reply) {
		this.reply = reply;
	}

	public Set<Reply> getReplies() {
		return replies;
	}

	public void setReplies(Set<Reply> replies) {
		this.replies = replies;
	}

	public Set<User> getDisagreeUsers() {
		return disagreeUsers;
	}

	public void setDisagreeUsers(Set<User> disagreeUsers) {
		this.disagreeUsers = disagreeUsers;
	}

	public Set<User> getAgreeUsers() {
		return agreeUsers;
	}

	public void setAgreeUsers(Set<User> agreeUsers) {
		this.agreeUsers = agreeUsers;
	}
}
