package com.sikiedu.betobe.service;

import com.sikiedu.betobe.domain.SubscribeUser;

import java.util.List;

/**
 * @author zhu
 * @date 2021/5/23 22:27:40
 * @description
 */
public interface SubscribeService {

	/**
	 * 通过email 查找是否已经存在
	 * @param email
	 * @return
	 */
	boolean findSubscribeByEmail(String email);

	/**
	 * 保存 subscribeUser
	 * @param subscribeUser
	 */
	void saveSubscribe(SubscribeUser subscribeUser);

	/**
	 * 查找全部的user
	 * @return
	 */
	List<SubscribeUser> findAllSubscribeUsers();
}
