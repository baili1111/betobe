package com.sikiedu.betobe.domain;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhu
 * @date 2021/5/1 11:54:54
 * @description
 */


@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String category;

	private String categoryImage;

	//该大分类下的所有博客
	@OneToMany(targetEntity = Blog.class)
	@JoinColumn(name="category_id")
	private Set<Blog> blogs = new HashSet<>();

	//该大分类下的所有小分类
	@OneToMany(targetEntity = SubCategory.class)
	@JoinColumn(name="category_id")
	private Set<SubCategory> subCategories = new HashSet<>();

	// 该分类下所有的视频
	@OneToMany(targetEntity = Video.class)
	@JoinColumn(name = "category_id")
	private Set<Video> videos = new HashSet<>();

	protected Category() {
	}

	public Category(Long id, String category, String categoryImage, Set<Blog> blogs, Set<SubCategory> subCategories, Set<Video> videos) {
		this.id = id;
		this.category = category;
		this.categoryImage = categoryImage;
		this.blogs = blogs;
		this.subCategories = subCategories;
		this.videos = videos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Set<Blog> getBlogs() {
		return blogs;
	}

	public void setBlogs(Set<Blog> blogs) {
		this.blogs = blogs;
	}

	public Set<SubCategory> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(Set<SubCategory> subCategories) {
		this.subCategories = subCategories;
	}

	public Set<Video> getVideos() {
		return videos;
	}

	public void setVideos(Set<Video> videos) {
		this.videos = videos;
	}

	public String getCategoryImage() {
		return categoryImage;
	}

	public void setCategoryImage(String categoryImage) {
		this.categoryImage = categoryImage;
	}
}
