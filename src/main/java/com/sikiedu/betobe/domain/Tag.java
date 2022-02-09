package com.sikiedu.betobe.domain;



import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhu
 * @date 2021/5/1 11:55:14
 * @description
 */

@Entity
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String tag;

	//该标签下的所有博客
	@ManyToMany(mappedBy = "blogTags")
	private Set<Blog> tagBlogs = new HashSet<>();

	//该标签下的所有小分类
	@ManyToMany(mappedBy = "tags")
	private Set<SubCategory> subCategories = new HashSet<>();

	// 该标签下的所有博客
	@ManyToMany(mappedBy = "videoTags")
	private Set<Video> videos = new HashSet<>();


	protected Tag() {
	}

	public Tag(Long id, String tag, Set<Blog> tagBlogs, Set<SubCategory> subCategories, Set<Video> videos) {
		this.id = id;
		this.tag = tag;
		this.tagBlogs = tagBlogs;
		this.subCategories = subCategories;
		this.videos = videos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Set<Blog> getTagBlogs() {
		return tagBlogs;
	}

	public void setTagBlogs(Set<Blog> tagBlogs) {
		this.tagBlogs = tagBlogs;
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
}
