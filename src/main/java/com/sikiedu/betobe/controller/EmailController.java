package com.sikiedu.betobe.controller;

import com.sikiedu.betobe.dto.Email;
import com.sikiedu.betobe.utils.SendEmailManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhu
 * @date 2021/5/24 14:20:54
 * @description
 */
@Controller
public class EmailController {

	@RequestMapping("/sendEmail")
	public String sendEmail(Email email) {

		SendEmailManager sendEmailManager = new SendEmailManager(
				"1666578924@qq.com", email.getTitle(),
				"邮箱：" + email.getEmail() + "<br/>姓名:"+ email.getUsername() + "<br/><br/>" + email.getMessage());
		sendEmailManager.start();
		return "contact-us.html";
	}
}
