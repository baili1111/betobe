package com.sikiedu.betobe.service.impl;

import com.sikiedu.betobe.domain.Comment;
import com.sikiedu.betobe.repository.CommentRepository;
import com.sikiedu.betobe.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhu
 * @date 2021/4/28 12:57:57
 * @description
 */
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public void saveComment(Comment comment) {
		commentRepository.save(comment);
	}

	@Override
	public List<Comment> findCommentListByCommentUserId(String id) {
		return commentRepository.findCommentListByCommentUserId(id);
	}

	@Override
	public Comment findCommentById(String replyCOrRId) {
		return commentRepository.findById(new Long(replyCOrRId)).get();
	}
}
