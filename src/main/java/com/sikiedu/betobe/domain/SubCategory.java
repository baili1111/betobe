package com.sikiedu.betobe.domain;



import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhu
 * @date 2021/5/1 12:47:15
 * @description
 */
@Entity
public class SubCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String subCategory;

	//该小分类下的所有博客
	@OneToMany(targetEntity = Blog.class)
	@JoinColumn(name="sub_category_id")
	private Set<Blog> blogs = new HashSet<>();

	@OneToMany(targetEntity = Video.class)
	@JoinColumn(name = "sub_category_id")
	private Set<Video> videos = new HashSet<>();

	//这个小分类属于哪一个大分类
	@ManyToOne(targetEntity = Category.class)
	@JoinColumn(name="category_id")
	private Category category;

	//小分类与标签，多对多
	@ManyToMany
	@JoinTable(
			name="sub_category_tag",
			joinColumns = @JoinColumn(name="sub_category_id"),
			inverseJoinColumns = @JoinColumn(name="tag_id")
	)
	private Set<Tag> tags = new HashSet<>();



	protected SubCategory() {
	}

	public SubCategory(Long id, String subCategory, Set<Blog> blogs, Set<Video> videos, Category category, Set<Tag> tags) {
		this.id = id;
		this.subCategory = subCategory;
		this.blogs = blogs;
		this.videos = videos;
		this.category = category;
		this.tags = tags;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
}
