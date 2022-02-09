package com.sikiedu.betobe.repository;

import com.sikiedu.betobe.domain.VideoBlogComment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author zhu
 * @date 2021/5/9 19:47:30
 * @description
 */
public interface VideoBlogCommentRepository extends CrudRepository<VideoBlogComment, String> {

	// 根据视频的id，查询所有评论了该视频的评论
	@Query(value = "select * from video_blog_comment where id like %?1% and length(id) < 60", nativeQuery = true)
	List<VideoBlogComment> findVideoCommentByVideoId(String idString);

	// 根据评论的id，查询所有回复了该评论的回复
	@Query(value = "select * from video_blog_comment where id like %?1% and length(id) > 60 and length(id) < 100", nativeQuery = true)
	List<VideoBlogComment> findVideoReplyByCommentId(String commentId);

	// 根据回复的id，查询所有回复了该回复的回复
	@Query(value = "select * from video_blog_comment where id like %?1 and length(id) > 100",nativeQuery = true)
	List<VideoBlogComment> findVideoReplyReplyByReplyId(String replyId);
}
