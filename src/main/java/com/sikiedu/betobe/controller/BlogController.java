package com.sikiedu.betobe.controller;

import com.sikiedu.betobe.domain.*;
import com.sikiedu.betobe.service.*;
import com.sikiedu.betobe.utils.BaseDataUtils;
import com.sikiedu.betobe.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhu
 * @date 2021/4/30 19:03:19
 * @description
 */
@Controller
public class BlogController {

	@Autowired
	private BaseDataUtils baseDataUtils;

	@Autowired
	private HttpSession session;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private SubCategoryService subCategoryService;

	@Autowired
	private TagService tagService;

	@Autowired
	private BlogService blogService;

	@Autowired
	private VideoBlogCommentService videoBlogCommentService;

	@RequestMapping("/findAllBlog")
	public String findAllBlog(Model model, Integer currentPage) {

		//List<Blog> blogList = blogService.findAllBlog();
		//model.addAttribute("blogList", blogList);

		PageBean blogPageBean = blogService.findAllBlogPageBean(currentPage, 8);

		// 所有的博客数量

		// 分页查询

		model.addAttribute("blogPageBean", blogPageBean);

		baseDataUtils.getBlogVideoRightData(model);

		return "blog.html";
	}


	/**
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/findBlogById")
	public String findBlogById(String id, Model model) {

		Blog blog = blogService.findBlogById(id);
		model.addAttribute("blog", blog);
		blog.setViewNumber(blog.getViewNumber()+1);
		blogService.saveBlog(blog);

		// 根据id返回Blog的评论数据
		List<VideoBlogComment> videoBlogCommentList = videoBlogCommentService.findVideoCommentByVideoId("|Blog" + id);

		// 根据评论的id去查询回复
		for (VideoBlogComment videoBlogCommentReply : videoBlogCommentList) {
			// 拿到评论的id
			String commentId = videoBlogCommentReply.getId();
			// 通过评论的id查询所有回复
			List<VideoBlogComment> videoReplyList = videoBlogCommentService.findVideoReplyByCommentId(commentId);
			videoBlogCommentReply.setReplyCommentList(videoReplyList);

			getVideoReply(videoReplyList, commentId);
		}

		model.addAttribute("videoBlogCommentList", videoBlogCommentList);

		baseDataUtils.getBlogVideoRightData(model);

		return "blog-single-post.html";
	}

	// 递归获取评论的回复，深度优先遍历
	private void getVideoReply(List<VideoBlogComment> videoReplyList, String replyWhatReply) {

		if (videoReplyList.isEmpty()) {
			return;
		}

		// 递归
		for (VideoBlogComment videoBlogCommentReplyReply : videoReplyList) {

			// 该回复是回复哪一个回复的
			VideoBlogComment replyWhatReplyObject = videoBlogCommentService.findVideoCommentById(replyWhatReply);
			videoBlogCommentReplyReply.setReplyWhatReply(replyWhatReplyObject);
			// 回复的是哪一个回复
			String replyId = videoBlogCommentReplyReply.getId();
			String[] split2 = replyId.split("\\|");
			// 通过回复的id，去查找所有回复的回复
			List<VideoBlogComment> videoReplyReplyList = videoBlogCommentService.findVideoReplyReplyByReplyId(split2[0], 43+split2[split2.length-1].length());
			videoBlogCommentReplyReply.setReplyCommentList(videoReplyReplyList);

			getVideoReply(videoReplyReplyList, replyId);
		}
	}

	/**
	 * 保存博客
	 * @param blog
	 * @param tagsinput 标签
	 * @param categoryId 分类
	 * @param subCategoryId 小分类
	 * @return
	 */
	@RequestMapping("/saveBlog")
	public String saveBlog(Blog blog, String tagsinput, String categoryId, String subCategoryId) {

		System.out.println(blog);

		System.out.println("categoryId=" + categoryId);
		System.out.println("subCategoryId=" + subCategoryId);
		System.out.println("tagsinput=" + tagsinput);

		// 封装分类
		Category category = categoryService.findCategoryById(categoryId);
		blog.setCategory(category);

		// 封装小分类
		SubCategory subCategory = subCategoryService.findSubCategoryById(subCategoryId);

		// 封装创建时间
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		String createTime = format.format(date);
		blog.setCreateTime(createTime);


		tagsinput = tagsinput.toUpperCase();
		// 封装标签
		String[] tags = tagsinput.split(",");
		Set<Tag> blogTags = new HashSet<>();
		for (String tagString : tags) {
			Tag tag = null;
			// 如果标签不存在，在数据里没有重复的标签
			if (tagService.findTagByTag(tagString) == null) {
				// 如果没有重复的，可以添加
				tag = new Tag(null, tagString, new HashSet<>(), new HashSet<>(), new HashSet<>());
				tag = tagService.saveTag(tag);
			} else {
				// 不可添加
				tag = tagService.findTagByTag(tagString);
			}

			blogTags.add(tag);
			// 维护subCategory与tag之间多对多关系
			subCategory.getTags().add(tag);
			tag.getSubCategories().add(subCategory);
		}
		// 维护blog与tag的关系
		blog.setBlogTags(blogTags);
		blog.setSubCategory(subCategory);

		// 封装发布博客的人
		User user = (User) session.getAttribute("user");
		blog.setUser(user);

		// 封装浏览数目
		blog.setViewNumber(0L);

		// 保存
		blogService.saveBlog(blog);


		return "redirect:/index";
	}

	/**
	 * 写博客
	 * @param model
	 * @return
	 */
	@RequestMapping("/submitPostBlog")
	public String submitPostBlog(Model model) {
		User user = (User) session.getAttribute("user");
		baseDataUtils.getData(model, user.getId().toString());

		List<Category> categoryList = categoryService.findAllCategory();
		//System.out.println(categoryList);
		model.addAttribute("categoryList", categoryList);

		return "submit-post-blog.html";
	}

}
