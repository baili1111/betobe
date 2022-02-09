package com.sikiedu.betobe.service;

import com.sikiedu.betobe.domain.Comment;

import java.util.List;

/**
 * @author zhu
 * @date 2021/4/28 12:57:23
 * @description
 */

public interface CommentService {

	/**
	 * 保存一条评论
	 * @param comment
	 */
	void saveComment(Comment comment);

	/**
	 * 查找所有评论这个用户的评论
	 * @param id comment_user_id
	 * @return
	 */
	List<Comment> findCommentListByCommentUserId(String id);

	/**
	 * 根据 id 查找评论
	 * @param replyCOrRId
	 * @return
	 */
	Comment findCommentById(String replyCOrRId);
}
