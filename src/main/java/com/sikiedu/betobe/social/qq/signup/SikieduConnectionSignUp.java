package com.sikiedu.betobe.social.qq.signup;

import com.sikiedu.betobe.domain.User;
import com.sikiedu.betobe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

/**
 * @author zhu
 * @date 2021/4/11 13:59:07
 * @description
 */
@Component
public class SikieduConnectionSignUp implements ConnectionSignUp {

	@Autowired
	private UserService userService;

	// 根据社交用户的信息，创建一个user，并且返回他的唯一标识
	// 创建用户
	@Override
	public String execute(Connection<?> connection) {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime = format.format(date);

		// 创建用户
		User user = new User(null, connection.getDisplayName(), "123456", null,
				null, null, connection.getDisplayName(), null, null,
				null, null, null, "/images/user/bg/profile-bg.png",
				"/images/user/head/" + new Random().nextInt(15) + ".jpg", createTime,
				new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(),
				new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(),
				new HashSet<>(), new HashSet<>());

		// 保存用户
		userService.saveUser(user);

		// 返回用户的唯一标识
		return user.getUsername();
	}
}
