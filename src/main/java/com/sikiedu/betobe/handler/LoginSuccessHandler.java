package com.sikiedu.betobe.handler;

import com.sikiedu.betobe.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author zhu
 * @date 2021/4/8 21:11:26
 * @description 登录成功跳转到用户的设置界面
 */
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private HttpSession session;

	// 第三方登录没有走这个handler
	// 默认的表单登录走了
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		//super.onAuthenticationSuccess(request, response, authentication);

		// 成功，跳转到首页
		//response.sendRedirect("/index");

		User user = (User) session.getAttribute("user");

		response.sendRedirect("/findUserProfileSettingsById?id="+user.getId());

	}
}
