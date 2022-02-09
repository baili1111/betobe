package com.sikiedu.betobe.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhu
 * @date 2021/4/30 22:10:24
 * @description
 */
@Entity
public class Blog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String coverImage;

	private String title;
	@Lob
	private String content;
	private String keyword;
	private Long viewNumber;
	private String createTime;

	// 该博客属于哪一个分类
	@ManyToOne(targetEntity = Category.class)
	@JoinColumn(name="category_id")
	private Category category;

	// 该博客属于哪一个小分类
	@ManyToOne(targetEntity = SubCategory.class)
	@JoinColumn(name="sub_category_id")
	private SubCategory subCategory;

	// 博客标签，多对多
	@ManyToMany
	@JoinTable(
			name="blog_tag",
			joinColumns = @JoinColumn(name="blog_id"),
			inverseJoinColumns = @JoinColumn(name="tag_id")
	)
	private Set<Tag> blogTags = new HashSet<Tag>();

	// 该博客是哪一个人发的
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name="user_id")
	private User user;

	@Override
	public String toString() {
		return "Blog{" +
				"id=" + id +
				", coverImage='" + coverImage + '\'' +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", keyword='" + keyword + '\'' +
				", viewNumber=" + viewNumber +
				", createTime='" + createTime + '\'' +
				", category=" + category +
				", subCategory=" + subCategory +
				", blogTags=" + blogTags +
				", user=" + user +
				'}';
	}

	protected Blog() {
	}

	public Blog(Long id, String coverImage, String title, String content, String keyword, Long viewNumber, String createTime, Category category, SubCategory subCategory, Set<Tag> blogTags, User user) {
		this.id = id;
		this.coverImage = coverImage;
		this.title = title;
		this.content = content;
		this.keyword = keyword;
		this.viewNumber = viewNumber;
		this.createTime = createTime;
		this.category = category;
		this.subCategory = subCategory;
		this.blogTags = blogTags;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
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

	public Long getViewNumber() {
		return viewNumber;
	}

	public void setViewNumber(Long viewNumber) {
		this.viewNumber = viewNumber;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public Set<Tag> getBlogTags() {
		return blogTags;
	}

	public void setBlogTags(Set<Tag> blogTags) {
		this.blogTags = blogTags;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
