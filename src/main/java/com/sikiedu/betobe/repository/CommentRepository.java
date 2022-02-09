package com.sikiedu.betobe.repository;

import com.sikiedu.betobe.domain.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author zhu
 * @date 2021/4/28 12:58:46
 * @description
 */
public interface CommentRepository extends CrudRepository<Comment, Long> {

	@Query(value = "select * from comment where comment_user_id = ?1", nativeQuery = true)
	List<Comment> findCommentListByCommentUserId(String id);
}
