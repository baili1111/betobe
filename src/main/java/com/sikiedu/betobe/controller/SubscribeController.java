package com.sikiedu.betobe.controller;

import com.sikiedu.betobe.domain.SubscribeUser;
import com.sikiedu.betobe.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhu
 * @date 2021/5/23 22:24:06
 * @description
 */
@Controller
public class SubscribeController {

	@Autowired
	private SubscribeService subscribeService;

	/**
	 * 保存订阅该网站的用户
	 * @param subscribeUser
	 * @return
	 */
	@RequestMapping("/subscribe")
	@ResponseBody
	public String subscribe(SubscribeUser subscribeUser) {

		System.out.println("---");

		if (subscribeService.findSubscribeByEmail(subscribeUser.getEmail())) {
			// 保存关注的用户
			subscribeService.saveSubscribe(subscribeUser);
			return "{\"success\":"+true+"}";
		}

		return "{\"success\":"+false+"}";
	}
}
