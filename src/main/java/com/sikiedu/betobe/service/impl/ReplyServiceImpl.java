package com.sikiedu.betobe.service.impl;

import com.sikiedu.betobe.domain.Reply;
import com.sikiedu.betobe.repository.ReplyRepository;
import com.sikiedu.betobe.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhu
 * @date 2021/4/28 20:48:19
 * @description
 */
@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyRepository replyRepository;

	@Override
	public Reply findReplyById(String replyCOrRId) {
		return replyRepository.findById(new Long(replyCOrRId)).get();
	}

	@Override
	public Reply saveReply(Reply temp) {
		return replyRepository.save(temp);
	}

}
