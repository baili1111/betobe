package com.sikiedu.betobe.service.impl;

import com.sikiedu.betobe.domain.SubscribeUser;
import com.sikiedu.betobe.repository.SubscribeRepository;
import com.sikiedu.betobe.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhu
 * @date 2021/5/23 22:27:58
 * @description
 */
@Service
public class SubscribeServiceImpl implements SubscribeService {

	@Autowired
	private SubscribeRepository subscribeRepository;

	@Override
	public boolean findSubscribeByEmail(String email) {
		SubscribeUser subscribe = subscribeRepository.findSubscribeByEmail(email);
		return subscribe == null;
	}

	@Override
	public void saveSubscribe(SubscribeUser subscribeUser) {
		subscribeRepository.save(subscribeUser);
	}

	@Override
	public List<SubscribeUser> findAllSubscribeUsers() {
		return (List<SubscribeUser>) subscribeRepository.findAll();
	}
}
