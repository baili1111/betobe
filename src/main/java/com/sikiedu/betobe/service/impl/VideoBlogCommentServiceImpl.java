package com.sikiedu.betobe.service.impl;

import com.sikiedu.betobe.domain.VideoBlogComment;
import com.sikiedu.betobe.repository.VideoBlogCommentRepository;
import com.sikiedu.betobe.service.VideoBlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhu
 * @date 2021/5/9 19:41:43
 * @description
 */
@Service
public class VideoBlogCommentServiceImpl implements VideoBlogCommentService {


	@Autowired
	private VideoBlogCommentRepository videoBlogCommentRepository;

	@Override
	public VideoBlogComment saveVideoBlogComment(VideoBlogComment videoBlogComment) {
		VideoBlogComment save = videoBlogCommentRepository.save(videoBlogComment);
		return save;
	}

	@Override
	public List<VideoBlogComment> findVideoCommentByVideoId(String idString) {
		return videoBlogCommentRepository.findVideoCommentByVideoId(idString);
	}

	@Override
	public List<VideoBlogComment> findVideoReplyByCommentId(String commentId) {
		return videoBlogCommentRepository.findVideoReplyByCommentId(commentId);
	}

	@Override
	public List<VideoBlogComment> findVideoReplyReplyByReplyId(String replyId, int num) {
		StringBuilder stringBuilder = new StringBuilder(replyId);

		for (int i = 1; i <= num ; i++) {
			stringBuilder.append("_");
		}
		return videoBlogCommentRepository.findVideoReplyReplyByReplyId(new String(stringBuilder));
	}

	@Override
	public VideoBlogComment findVideoCommentById(String videoReplyReplyId) {
		return videoBlogCommentRepository.findById(videoReplyReplyId).get();
	}
}
