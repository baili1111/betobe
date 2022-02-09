package com.sikiedu.betobe.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhu
 * @date 2021/4/8 21:11:12
 * @description 使用账号密码登录时，登录失败会让重新登录，并且在上面放置错误信息
 */
@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		//super.onAuthenticationFailure(request, response, exception);

		// 在request域中放置错误信息
		request.setAttribute("error", "Error in username or password");
		// 转发到loginBetobe
		request.getRequestDispatcher("/loginBetobe").forward(request, response);
	}
}
