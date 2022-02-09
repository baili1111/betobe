package com.sikiedu.betobe.service;

import com.sikiedu.betobe.domain.VideoBlogComment;

import java.util.List;

/**
 * @author zhu
 * @date 2021/5/9 19:41:29
 * @description
 */
public interface VideoBlogCommentService {
	/**
	 * 保存一条视频评论
	 * @param videoBlogComment
	 * @return
	 */
	VideoBlogComment saveVideoBlogComment(VideoBlogComment videoBlogComment);

	/**
	 * 根据视频idString 模糊查询  查询出来的如果 数据库里面的id小于60就是一条评论，大于就是一条回复
	 * @param idString "|Video" + id
	 * @return
	 */
	List<VideoBlogComment> findVideoCommentByVideoId(String idString);

	/**
	 * 根据视频评论的id去获取回复
	 * @param commentId
	 * @return
	 */
	List<VideoBlogComment> findVideoReplyByCommentId(String commentId);

	/**
	 * 根据视频的回复的id去获取回复
	 * @param replyId
	 * @return
	 */
	List<VideoBlogComment> findVideoReplyReplyByReplyId(String replyId, int num);

	/**
	 * 根据 id 查询回复
	 * @param videoReplyReplyId
	 * @return
	 */
	VideoBlogComment findVideoCommentById(String videoReplyReplyId);
}
