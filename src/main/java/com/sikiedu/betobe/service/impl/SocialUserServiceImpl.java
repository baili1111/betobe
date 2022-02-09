package com.sikiedu.betobe.service.impl;

import com.sikiedu.betobe.domain.User;
import com.sikiedu.betobe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * @author zhu
 * @date 2021/4/7 22:25:49
 * @description spring security 登录走的Service
 */

@Service
public class SocialUserServiceImpl implements UserDetailsService, SocialUserDetailsService {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private HttpSession session;

	/**
	 * 正常密码登录走的方法
	 * @param username
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		System.out.println("SocialUserServiceImpl----->loadUserByUsername----->" + username);
		// 查找用户是否存在
		User user = userService.findUserByUsername(username);
		if (user == null) {
			// 不存在，UsernameNotFoundException
			throw new UsernameNotFoundException("SocialUserServiceImpl----->loadUserByUsername----->" + username);
		}
		// 在session域放置user对象，为登录人
		session.setAttribute("user", user);
		// 存在，给我们SpringSecurity用户名以及密码，权限
		return new SocialUser(user.getUsername(), passwordEncoder.encode(user.getPassword()),
				true, true, true, true,
				AuthorityUtils.commaSeparatedStringToAuthorityList("USER"));
	}

	/**
	 * 第三方登录走的方法
	 * @param userId
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {

		System.out.println("SocialUserServiceImpl----->loadUserByUserId----->" + userId);

		User user = userService.findUserByUsername(userId);
		if (user == null) {
			// 不存在，UsernameNotFoundException
			throw new UsernameNotFoundException("SocialUserServiceImpl----->loadUserByUserId----->" + userId);
		}
		// 在session域放置user对象，为登录人
		session.setAttribute("user", user);

		// 存在，给我们SpringSecurity用户名以及密码，权限
		return new SocialUser(user.getUsername(), passwordEncoder.encode(user.getPassword()),
				true, true, true, true,
				AuthorityUtils.commaSeparatedStringToAuthorityList("USER"));
	}
}
