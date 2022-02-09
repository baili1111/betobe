package com.sikiedu.betobe.domain;



import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhu
 * @date 2021/5/5 01:17:17
 * @description
 */

@Entity
public class Video {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	@Lob
	private String content;
	private String keyword;
	private String createTime;
	private Long viewNumber;

	// 视频长度
	private Integer seconds;

	private String videoFileId;
	private String videoUrl;
	private String coverImage;;


	// 视频分类
	@ManyToOne(targetEntity = Category.class)
	@JoinColumn(name = "category_id")
	private Category category;

	// 视频小分类
	@ManyToOne(targetEntity = SubCategory.class)
	@JoinColumn(name = "sub_category_id")
	private SubCategory subCategory;

	// 视频标签
	@ManyToMany
	@JoinTable(
			name = "video_tag",
			joinColumns = @JoinColumn(name = "video_id"),
			inverseJoinColumns = @JoinColumn(name = "tag_id")
	)
	private Set<Tag> videoTags = new HashSet<>();

	// 发布该视频的用户
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id")
	private User user;

	// 喜欢该视频的人
	@ManyToMany(mappedBy = "favoriteVideos")
	private Set<User> favoriteUsers = new HashSet<>();

	// 给视频点赞的人
	@ManyToMany(mappedBy = "agreeVideos")
	private Set<User> agreeUsers = new HashSet<>();

	// 给视频点踩的人
	@ManyToMany(mappedBy = "disagreeVideos")
	private Set<User> disagreeUsers = new HashSet<>();

	@OneToMany(targetEntity = Barrage.class)
	@JoinColumn(name = "video_id")
	private Set<Barrage> barrages = new HashSet<>();

	protected Video() {
	}

	public Video(Long id, String title, String content, String keyword, String createTime, Long viewNumber, Integer seconds, String videoFileId, String videoUrl, String coverImage, Category category, SubCategory subCategory, Set<Tag> videoTags, User user, Set<User> favoriteUsers, Set<User> agreeUsers, Set<User> disagreeUsers, Set<Barrage> barrages) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.keyword = keyword;
		this.createTime = createTime;
		this.viewNumber = viewNumber;
		this.seconds = seconds;
		this.videoFileId = videoFileId;
		this.videoUrl = videoUrl;
		this.coverImage = coverImage;
		this.category = category;
		this.subCategory = subCategory;
		this.videoTags = videoTags;
		this.user = user;
		this.favoriteUsers = favoriteUsers;
		this.agreeUsers = agreeUsers;
		this.disagreeUsers = disagreeUsers;
		this.barrages = barrages;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Long getViewNumber() {
		return viewNumber;
	}

	public void setViewNumber(Long viewNumber) {
		this.viewNumber = viewNumber;
	}

	public Integer getSeconds() {
		return seconds;
	}

	public void setSeconds(Integer seconds) {
		this.seconds = seconds;
	}

	public String getVideoFileId() {
		return videoFileId;
	}

	public void setVideoFileId(String videoFileId) {
		this.videoFileId = videoFileId;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public Set<Tag> getVideoTags() {
		return videoTags;
	}

	public void setVideoTags(Set<Tag> videoTags) {
		this.videoTags = videoTags;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<User> getFavoriteUsers() {
		return favoriteUsers;
	}

	public void setFavoriteUsers(Set<User> favoriteUsers) {
		this.favoriteUsers = favoriteUsers;
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

	public Set<Barrage> getBarrages() {
		return barrages;
	}

	public void setBarrages(Set<Barrage> barrages) {
		this.barrages = barrages;
	}
}
