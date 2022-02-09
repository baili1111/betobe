package com.sikiedu.betobe.service;

import com.sikiedu.betobe.domain.Reply;

/**
 * @author zhu
 * @date 2021/4/28 20:48:02
 * @description
 */
public interface ReplyService {
	/**
	 * 查找回复
	 * @param replyCOrRId
	 * @return
	 */
	Reply findReplyById(String replyCOrRId);

	/**
	 *
	 * @param temp
	 * @return
	 */
	Reply saveReply(Reply temp);
}
