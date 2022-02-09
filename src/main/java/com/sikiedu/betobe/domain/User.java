package com.sikiedu.betobe.domain;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhu
 * @date 2021/4/5 14:48:50
 * @description
 */

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 用户名
	private String username;
	private String password;
	private String email;

	private String firstName;
	private String lastName;
	// 显示名称
	private String displayName;

	// 个人首页
	private String webUrl;
	// 电话
	private String phone;

	// 长文本 -> longtext
	@Lob
	// 个人描述
	private String description;

	// social Link
	private String qqLink;
	private String weixinLink;

	// 封面图片
	private String coverImage;
	// 头像
	private String headImage;
	// 创建时间
	private String createTime;


	// 该用户关注了哪些用户
	// 在保存的时候，会级联保存所有的对象
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(
			name = "user_follow",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "follow_id")
	)
	private Set<User> follows = new HashSet<>();

	// 用户，同意了哪些评论
	@ManyToMany
	@JoinTable(
			name = "agree_user_comment",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "comment_id")
	)
	private Set<Comment> agreeComments = new HashSet<>();

	// 用户，不同意哪些评论
	@ManyToMany
	@JoinTable(
			name = "disagree_user_comment",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "comment_id")
	)
	private Set<Comment> disagreeComments = new HashSet<>();



	// 用户发的所有评论
	@OneToMany(targetEntity = Comment.class)
	@JoinColumn(name = "user_id")
	private Set<Comment> comments = new HashSet<>();


	// 用户， 同意了哪些回复
	@ManyToMany
	@JoinTable(
			name = "agree_user_reply",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "reply_id")
	)
	private Set<Reply> agreeReplies = new HashSet<>();

	// 用户， 不同意了哪些回复
	@ManyToMany
	@JoinTable(
			name = "disagree_user_reply",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "reply_id")
	)
	private Set<Reply> disagreeReplies = new HashSet<>();

	// 用户发的博客
	@OneToMany(targetEntity = Blog.class)
	@JoinColumn(name = "user_id")
	private Set<Blog> blogs = new HashSet<>();

	// 用户发的视频
	@OneToMany(targetEntity = Video.class)
	@JoinColumn(name = "user_id")
	private Set<Video> videos = new HashSet<>();

	// 用户点赞的视频
	@ManyToMany
	@JoinTable(
			name = "agree_user_video",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "video_id")
	)
	private Set<Video> agreeVideos = new HashSet<>();

	// 用户踩的视频
	@ManyToMany
	@JoinTable(
			name = "disagree_user_video",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "video_id")
	)
	private Set<Video> disagreeVideos = new HashSet<>();

	// 用户最喜欢的视频
	@ManyToMany
	@JoinTable(
			name = "user_favorite_video",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "video_id")
	)
	private Set<Video> favoriteVideos = new HashSet<>();

	// 用户发的视频或者博客的评论
	@OneToMany(targetEntity = VideoBlogComment.class)
	@JoinColumn(name = "user_id")
	private Set<VideoBlogComment> videoBlogComments = new HashSet<>();

	// 用户赞视频评论或者回复
	@ManyToMany
	@JoinTable(
			name = "agree_user_video_blog_comment",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "video_blog_id")
	)
	private Set<VideoBlogComment> agreeVideoBlogComment = new HashSet<>();

	// 用户踩视频评论或者回复
	@ManyToMany
	@JoinTable(
			name = "disagree_user_video_blog_comment",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "video_blog_id")
	)
	private Set<VideoBlogComment> disagreeVideoBlogComment = new HashSet<>();

	protected User() {
	}

	public User(Long id, String username, String password, String email, String firstName, String lastName, String displayName, String webUrl, String phone, String description, String qqLink, String weixinLink, String coverImage, String headImage, String createTime, Set<User> follows, Set<Comment> agreeComments, Set<Comment> disagreeComments, Set<Comment> comments, Set<Reply> agreeReplies, Set<Reply> disagreeReplies, Set<Blog> blogs, Set<Video> videos, Set<Video> agreeVideos, Set<Video> disagreeVideos, Set<Video> favoriteVideos, Set<VideoBlogComment> videoBlogComments, Set<VideoBlogComment> agreeVideoBlogComment, Set<VideoBlogComment> disagreeVideoBlogComment) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.displayName = displayName;
		this.webUrl = webUrl;
		this.phone = phone;
		this.description = description;
		this.qqLink = qqLink;
		this.weixinLink = weixinLink;
		this.coverImage = coverImage;
		this.headImage = headImage;
		this.createTime = createTime;
		this.follows = follows;
		this.agreeComments = agreeComments;
		this.disagreeComments = disagreeComments;
		this.comments = comments;
		this.agreeReplies = agreeReplies;
		this.disagreeReplies = disagreeReplies;
		this.blogs = blogs;
		this.videos = videos;
		this.agreeVideos = agreeVideos;
		this.disagreeVideos = disagreeVideos;
		this.favoriteVideos = favoriteVideos;
		this.videoBlogComments = videoBlogComments;
		this.agreeVideoBlogComment = agreeVideoBlogComment;
		this.disagreeVideoBlogComment = disagreeVideoBlogComment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQqLink() {
		return qqLink;
	}

	public void setQqLink(String qqLink) {
		this.qqLink = qqLink;
	}

	public String getWeixinLink() {
		return weixinLink;
	}

	public void setWeixinLink(String weixinLink) {
		this.weixinLink = weixinLink;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Set<User> getFollows() {
		return follows;
	}

	public void setFollows(Set<User> follows) {
		this.follows = follows;
	}

	public Set<Comment> getAgreeComments() {
		return agreeComments;
	}

	public void setAgreeComments(Set<Comment> agreeComments) {
		this.agreeComments = agreeComments;
	}

	public Set<Comment> getDisagreeComments() {
		return disagreeComments;
	}

	public void setDisagreeComments(Set<Comment> disagreeComments) {
		this.disagreeComments = disagreeComments;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<Reply> getAgreeReplies() {
		return agreeReplies;
	}

	public void setAgreeReplies(Set<Reply> agreeReplies) {
		this.agreeReplies = agreeReplies;
	}

	public Set<Reply> getDisagreeReplies() {
		return disagreeReplies;
	}

	public void setDisagreeReplies(Set<Reply> disagreeReplies) {
		this.disagreeReplies = disagreeReplies;
	}

	public Set<Blog> getBlogs() {
		return blogs;
	}

	public void setBlogs(Set<Blog> blogs) {
		this.blogs = blogs;
	}

	public Set<Video> getVideos() {
		return videos;
	}

	public void setVideos(Set<Video> videos) {
		this.videos = videos;
	}

	public Set<Video> getAgreeVideos() {
		return agreeVideos;
	}

	public void setAgreeVideos(Set<Video> agreeVideos) {
		this.agreeVideos = agreeVideos;
	}

	public Set<Video> getDisagreeVideos() {
		return disagreeVideos;
	}

	public void setDisagreeVideos(Set<Video> disagreeVideos) {
		this.disagreeVideos = disagreeVideos;
	}

	public Set<Video> getFavoriteVideos() {
		return favoriteVideos;
	}

	public void setFavoriteVideos(Set<Video> favoriteVideos) {
		this.favoriteVideos = favoriteVideos;
	}

	public Set<VideoBlogComment> getVideoBlogComments() {
		return videoBlogComments;
	}

	public void setVideoBlogComments(Set<VideoBlogComment> videoBlogComments) {
		this.videoBlogComments = videoBlogComments;
	}

	public Set<VideoBlogComment> getAgreeVideoBlogComment() {
		return agreeVideoBlogComment;
	}

	public void setAgreeVideoBlogComment(Set<VideoBlogComment> agreeVideoBlogComment) {
		this.agreeVideoBlogComment = agreeVideoBlogComment;
	}

	public Set<VideoBlogComment> getDisagreeVideoBlogComment() {
		return disagreeVideoBlogComment;
	}

	public void setDisagreeVideoBlogComment(Set<VideoBlogComment> disagreeVideoBlogComment) {
		this.disagreeVideoBlogComment = disagreeVideoBlogComment;
	}
}
